package com.mickleentityltdnigeria.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.data.FoodItem;

import java.io.InputStream;

import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.module;

public class Show_Pic_Activity extends AppCompatActivity {

    ImageView foodImg;
    TextView foodText, foodPrice;
    Button btnAdd;
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
            this.btnAdd = (Button) findViewById(R.id.btnAdd);
            //
            this.foodItem = (FoodItem) bundle.getSerializableExtra("payLoad");
            //
            try {
                InputStream ims = assetManager.open(this.foodItem.getFoodUrl());
                Drawable d = Drawable.createFromStream(ims, null);
                this.foodImg.setImageDrawable(d);
                this.foodText.setText(foodItem.getFoodDesc());
                this.foodPrice.setText("$"+foodItem.getFoodPrice());
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            btnAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    addToCart(1);
                }
            });
            // Register interest in the completed report
            CartItemChangedHandler cartChanged = new CartItemChangedHandler() {
                public void invoke(int qty) {
                    displayCartQty(qty);
                }
            };

            module.shoppingCart.cartItemChanged.addListener(cartChanged);
        }
    }

    public void addToCart(int Qty){
        module.shoppingCart.addCartItem(this.foodItem.getID(),this.foodItem.getFoodPrice(),this.foodItem.getFoodDesc(), Qty,this.foodItem.getFoodUrl());
    }

    public void displayCartQty(int Qty) {
        Toast.makeText(this, ""+ Qty + " items added to Cart.", Toast.LENGTH_SHORT).show();
    }

}