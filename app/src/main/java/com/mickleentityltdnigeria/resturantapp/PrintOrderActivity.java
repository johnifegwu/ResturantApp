package com.mickleentityltdnigeria.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;

import java.util.ArrayList;
import java.util.List;

public class PrintOrderActivity extends AppCompatActivity {

    public static List<FoodOrder> foodOrder = new ArrayList<>();
    public static List<FoodOrderDetail> foodOrderDetails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_order);
    }

}