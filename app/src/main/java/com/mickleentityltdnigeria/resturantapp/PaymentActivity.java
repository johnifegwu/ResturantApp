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
                    if(result == 1){
                        PaymentDetails paymentDetails = new PaymentDetails(authCode,0.0,0.0,0.0,"","","");
                        Intent intent = getIntent();
                        intent.putExtra("paymentDetails",paymentDetails);
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


    }

}