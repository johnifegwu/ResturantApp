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
import java.text.NumberFormat;
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
    double TotalDue = 0;
    String TranDesc = "";

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
                        String tran_curr = foodOrderDetails.get(0).getCurrency();
                        //Customers details
                        double tran_amount = getFoodOrderTotalValue(foodOrderDetails);
                        TotalDue = tran_amount;
                        String c_wallet_id = Customer_MICKLE_PAY_ID;
                        String tran_desc = "Order by: " + foodOrderDetails.get(0).getFoodOrder().getShippingContactPerson();
                        TranDesc = tran_desc;

                        //Main Merchant Wallet details
                        if(resturant == null){
                            throw new Exception("Merchant WALLET ID required.");
                        }
                        String m_wallet_id = resturant.getMicklePayWalletID();
                        double m_amount = getFoodOrderTotalMerchantValue(foodOrderDetails);

                        //Sub Merchant Wallet Details
                        String s_wallet_id = applicationData.getMicklePayWalletID();
                        double s_amount = getFoodOrderTotalSubMerchantValue(foodOrderDetails);

                        //Make payment and get Transaction Status
                        String api_key = "1234567890";
                        Uri uri = Uri.parse("mickle-pay:" + resturant.getResturantID());
                        Intent payMerchant = new Intent(Intent.ACTION_VIEW, uri);
                        payMerchant.putExtra("api_key", api_key);
                        payMerchant.putExtra("c_wallet_id", c_wallet_id);
                        payMerchant.putExtra("tran_amount", tran_amount);
                        payMerchant.putExtra("tran_curr", tran_curr);
                        payMerchant.putExtra("tran_desc", tran_desc);
                        payMerchant.putExtra("m_wallet_id", m_wallet_id);
                        payMerchant.putExtra("m_amount", m_amount);
                        payMerchant.putExtra("s_wallet_id", s_wallet_id);
                        payMerchant.putExtra("s_amount", s_amount);
                        mMakePaymentForResult.launch(payMerchant);
                    } catch (Exception e) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

            try {
                NumberFormat dc = NumberFormat.getInstance();
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

    ActivityResultLauncher<Intent> mMakePaymentForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        Intent intent = result.getData();
                        assert intent != null;
                        String tranStatus = intent.getStringExtra("TransactStatus");
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // Handle the Intent
                            Toast.makeText(PaymentActivity.this, "Payment succeeded with code: " + tranStatus, Toast.LENGTH_SHORT ).show();
                            PaymentDetails paymentDetails = new PaymentDetails(tranStatus, TotalDue, TotalDue, 0.0, "MICKLE-PAY", TranDesc, module.userName);
                            intent.putExtra("paymentDetails", paymentDetails);
                            intent.putExtra("TransactStatus",tranStatus);
                            setResult(RESULT_OK, intent);
                        } else {
                            // Handle the Intent
                            Toast.makeText(PaymentActivity.this, "Payment failed with code: " + tranStatus, Toast.LENGTH_SHORT ).show();
                            intent.putExtra("TransactStatus",tranStatus);
                            setResult(RESULT_CANCELED, intent);
                        }
                        finish();
                    } catch (Exception e) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(PaymentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

}