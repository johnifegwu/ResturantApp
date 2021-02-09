package com.mickleentityltdnigeria.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.data.FoodItem;

import java.io.InputStream;

public class Show_Pic_Activity extends AppCompatActivity {

    ImageView foodImg;
    TextView foodText, foodPrice;
    public FoodItem foodItem;
    private Context myContext = ApplicationContextProvider.getContext();
    private AssetManager assetManager = myContext.getAssets();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__pic_);
        Intent bundle = getIntent();
        if (bundle != null) {
            this.foodImg = (ImageView) findViewById(R.id.imgFood);
            this.foodText = (TextView) findViewById(R.id.txtFoodDesc);
            this.foodPrice = (TextView) findViewById(R.id.txtPrice);

            this.foodItem = (FoodItem) bundle.getSerializableExtra("payLoad");
            try {
                InputStream ims = assetManager.open(this.foodItem.getFoodUrl());
                Drawable d = Drawable.createFromStream(ims, null);
                this.foodImg.setImageDrawable(d);
                this.foodText.setText(foodItem.getFoodDesc());
                this.foodPrice.setText("$"+foodItem.getFoodPrice());
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}