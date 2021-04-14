package com.mickleentityltdnigeria.resturantapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.ApplicationDataDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.ApplicationData;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.ApplicationDataEvenHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.PaymentDetails;
import com.mickleentityltdnigeria.resturantapp.utils.module;
import com.mickleentityltdnigeria.resturantapp.utils.paymentResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mickleentityltdnigeria.resturantapp.utils.module.getFoodOrderTotalMerchantValue;
import static com.mickleentityltdnigeria.resturantapp.utils.module.getFoodOrderTotalSubMerchantValue;
import static com.mickleentityltdnigeria.resturantapp.utils.module.getFoodOrderTotalValue;

public class PaymentActivity extends AppCompatActivity {

    Button btnPay, btnScan;
    ProgressBar progress;
    ApplicationData applicationData;
    ApplicationDataDalc applicationDataDalc;
    String Customer_MICKLE_PAY_ID = null;
    TextView txtTotalValueDue;
    Resturant resturant = null;
    ResturantDalc resturantDalc;

    public static List<FoodOrderDetail> foodOrderDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        try {
            this.applicationData = new ApplicationData();
            this.applicationDataDalc = new ApplicationDataDalc();
            this.resturantDalc = new ResturantDalc();
            this.btnPay = this.findViewById(R.id.btnPay);
            this.btnScan = this.findViewById(R.id.btnScanCustomerMICKLEPAYWALLETID);
            this.progress = this.findViewById(R.id.progressBarPayment);
            this.txtTotalValueDue = this.findViewById(R.id.txtPaymentTotalDue);
            this.btnPay.setEnabled(false);
            progress.setVisibility(View.VISIBLE);
            //
            ResturantUpdatedHandler onRestaurantDataFetched = new ResturantUpdatedHandler() {
                @Override
                public void invoke(List<Resturant> Resturant) {
                    resturant = Resturant.get(0);
                }
            };
            ResturantUpdatedHandler onRestaurantDataNotFound = new ResturantUpdatedHandler() {
                @Override
                public void invoke(List<Resturant> Resturant) {
                    resturant = null;
                }
            };
            this.resturantDalc.resturantDataFetched.addListener(onRestaurantDataFetched);
            this.resturantDalc.resturantNotFound.addListener(onRestaurantDataNotFound);
            this.resturantDalc.getResturantByResturantID(module.userData.getResturantID());
            //
            ApplicationDataEvenHandler onDataFetched = new ApplicationDataEvenHandler() {
                @Override
                public void invoke(List<ApplicationData> applicationDataList) {
                    progress.setVisibility(View.GONE);
                    applicationData = applicationDataList.get(0);
                    if (Customer_MICKLE_PAY_ID != null) {
                        btnPay.setEnabled(true);
                    }
                }
            };
            this.applicationDataDalc.onDataFetchedHandler.addListener(onDataFetched);
            ApplicationDataEvenHandler onDataNotFound = new ApplicationDataEvenHandler() {
                @Override
                public void invoke(List<ApplicationData> applicationDataList) {
                    progress.setVisibility(View.GONE);
                    applicationData = new ApplicationData();
                    btnPay.setEnabled(false);
                }
            };
            this.applicationDataDalc.onDataNotFoundHandler.addListener(onDataNotFound);
            this.applicationDataDalc.getApplicationData();
            //
            btnScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanQRCode();
                }
            });
            //
            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        progress.setVisibility(View.VISIBLE);
                        //validate payment inputs
                        String CurrencyCode = foodOrderDetails.get(0).getCurrency();
                        //Customers details
                        double totalDue = getFoodOrderTotalValue(foodOrderDetails);
                        String CUSTOMER_MICKLE_PAY_WALLET_ID = Customer_MICKLE_PAY_ID;

                        //Main Merchant Wallet details
                        if(resturant == null){
                            throw new Exception("Merchant WALLET ID required.");
                        }
                        String Merchant_MICKLE_PAY_WALLET_ID = resturant.getMicklePayWalletID();
                        double MerchantAmountDue = getFoodOrderTotalMerchantValue(foodOrderDetails);

                        //Sub Merchant Wallet Details
                        String Sub_Merchant_MICKLE_PAY_WALLET_ID = applicationData.getMicklePayWalletID();
                        double SubMerchantAmountDue = getFoodOrderTotalSubMerchantValue(foodOrderDetails);

                        //Make payment and get Authorization Code
                        int result = 1;
                        String authCode = "1234567890";
                        if (result == 1) {
                            PaymentDetails paymentDetails = new PaymentDetails(authCode, totalDue, totalDue, 0.0, "", "", "");
                            Intent intent = getIntent();
                            intent.putExtra("paymentDetails", paymentDetails);
                            setResult(RESULT_OK, intent);
                            progress.setVisibility(View.GONE);
                            finish();
                        }
                    } catch (Exception e) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            try {
                DecimalFormat dc = new DecimalFormat("#,###,##0.00");
                String CurrencyCode = foodOrderDetails.get(0).getCurrency();
                //Total Due
                txtTotalValueDue.setText((CurrencyCode + dc.format(getFoodOrderTotalValue(foodOrderDetails))));
            } catch (Exception e) {
                progress.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            progress.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void scanQRCode() {
        // The launcher with the Intent you want to start
        Intent intent = new Intent(PaymentActivity.this, ScanQRCodeActivity.class);
        mStartScanForResult.launch(intent);
        //
    }


    ActivityResultLauncher<Intent> mStartScanForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            // Handle the Intent
                            assert intent != null;
                            String QRCode = intent.getStringExtra("QRCode");
                            Customer_MICKLE_PAY_ID = QRCode;
                            btnPay.setEnabled(true);
                        } else {
                            Customer_MICKLE_PAY_ID = null;
                            btnPay.setEnabled(false);
                        }
                    } catch (Exception e) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(PaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

}