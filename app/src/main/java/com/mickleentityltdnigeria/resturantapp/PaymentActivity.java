package com.mickleentityltdnigeria.resturantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.utils.PaymentDetails;
import com.mickleentityltdnigeria.resturantapp.utils.paymentResult;

import java.util.ArrayList;
import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    int resultCode;
    Intent data;
    Button btnPay;
    ProgressBar progress;

    public static List<FoodOrder> foodOrder = new ArrayList<>();
    public static List<FoodOrderDetail> foodOrderDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnPay = this.findViewById(R.id.btnPay);
        progress = this.findViewById(R.id.progressBarPayment);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    progress.setVisibility(View.VISIBLE);
                    //validate payment inputs

                    //Make payment and get Authorization Code
                    int result = 1;
                    String authCode = "1234567890";
                    PaymentDetails paymentDetails = new PaymentDetails(authCode,0.0,0.0,0.0,"","","");
                    Intent intent = new Intent(AppGlobals.getAppContext(), PaymentDetails.class);
                    intent.putExtra("paymentDetails",paymentDetails);
                    switch (result) {
                        case 1:
                            setPaymentResult(paymentResult.OK, intent);
                            break;
                        case 0:
                            setPaymentResult(paymentResult.FAILED, intent);
                            break;
                        case -1:
                            setPaymentResult(paymentResult.INSUFFICIENT_BALANCE, intent);
                            break;
                        case -2:
                            setPaymentResult(paymentResult.CANCELED, intent);
                            break;
                        case -3:
                            setPaymentResult(paymentResult.NETWORK_FAILURE, intent);
                    }
                    progress.setVisibility(View.GONE);
                    finish();
                } catch (Exception e) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void setPaymentResult(paymentResult result, Intent data) {
        this.resultCode = result.getValue();
        this.data = data;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        resultCode = this.resultCode;
        data = this.data;
        super.onActivityResult(requestCode, resultCode, data);
    }
}