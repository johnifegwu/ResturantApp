package com.mickleentityltdnigeria.resturantapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.service.CartService;
import com.mickleentityltdnigeria.resturantapp.service.Service;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class ShoppingCartActivity extends AppCompatActivity {

    //private final Context myContext = ApplicationContextProvider.getContext();
    TextView status;
    List<CartItem> cartItems = new ArrayList<CartItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        AppGlobals.setAppContext(this);
        this.status = (TextView) findViewById(R.id.txtCartStatus);

        // Register interest in the CartItem Change.
        CartItemChangedHandler cartChanged = new CartItemChangedHandler() {
            public void invoke(int qty) {
                displayCartQty(qty, findViewById(R.id.shoppingCartView));
            }
        };

        try {
            Service.cart().Cart.cartItemChanged.addListener(cartChanged);

            this.cartItems = Service.cart().getCartItems(module.userName);

            //Reference of RecyclerView
            RecyclerView mRecyclerView = this.findViewById(R.id.shoppingCartRecyclerView);

            //Linear Layout Manager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppGlobals.getAppContext(), RecyclerView.VERTICAL, false);

            //Set Layout Manager to RecyclerView
            mRecyclerView.setLayoutManager(linearLayoutManager);

            //Create adapter
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(cartItems, new CartRecyclerViewItemClickListener() {
                @Override
                public void onItemClicked(@NotNull CartItem cartItem) {

                }
            });

            //Set adapter to RecyclerView
            mRecyclerView.setAdapter(adapter);
            //
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }finally {
            String currency = "$";
            if(this.cartItems.size()>0) currency = this.cartItems.get(0).getCurrency();
            SetStatus(currency);
        }

    }

    private void updateAdapter(){
        try {
            //Reference of RecyclerView
            RecyclerView mRecyclerView = this.findViewById(R.id.shoppingCartRecyclerView);

            //Create adapter
            this.cartItems = Service.cart().Cart.getCartItems(module.userName);
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(cartItems, new CartRecyclerViewItemClickListener() {
                @Override
                public void onItemClicked(@NotNull CartItem cartItem) {

                }
            });

            //Set adapter to RecyclerView
            mRecyclerView.swapAdapter(adapter,false);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void SetStatus(String currency){
        DecimalFormat dc = new DecimalFormat("#,###,##0.00");
        this.status.setText("Cart total: "+ currency +  dc.format(Service.cart().Cart.getCartTotal(module.userName)));
    }

    public void displayCartQty(int Qty, View v) {
        Snackbar.make(v, "" + Qty + " item(s) added to Cart.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        updateAdapter();
        String currency = "$";
        if(this.cartItems.size()>0){
            currency = this.cartItems.get(0).getCurrency();
        }
        SetStatus(currency);
        //Toast.makeText(this, ""+ Qty + " items added to Cart.", Toast.LENGTH_SHORT).show();
    }
}