package com.mickleentityltdnigeria.resturantapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.service.Service;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowPictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowPictureFragment extends Fragment {

    ImageView foodImg;
    TextView foodText, foodPrice;
    Button btnAdd;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ShowPictureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowPictureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowPictureFragment newInstance(String param1, String param2) {
        ShowPictureFragment fragment = new ShowPictureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_picture, container, false);
    }

    ProgressBar progress;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.progress = (ProgressBar) view.findViewById(R.id.progressBarShowPic);
        this.progress.setVisibility( View.VISIBLE);
        //
        if (module.foodItem != null) {
            this.foodImg = (ImageView) view.findViewById(R.id.imgFood);
            this.foodText = (TextView) view.findViewById(R.id.txtFoodDesc);
            this.foodPrice = (TextView) view.findViewById(R.id.txtPrice);
            this.btnAdd = (Button) view.findViewById(R.id.btnAdd);
            //
            try {
                InputStream ims = new ByteArrayInputStream(ImageHelper.getInstant().base64StringToByteArray(module.foodItem.getFoodImg())); //assetManager.open(this.foodItem.getFoodUrl());
                Drawable d = Drawable.createFromStream(ims, null);
                this.foodImg.setImageDrawable(d);
                this.foodText.setText(module.foodItem.getFoodDesc());
                this.foodPrice.setText(module.foodItem.getCurrency()+module.foodItem.getFoodPrice());
            }catch (Exception e){
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            final View[] vw = new View[1];
            btnAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    vw[0] = v;
                    addToCart(1);
                }
            });
            // Register interest in the completed report
            CartItemChangedHandler cartChanged = new CartItemChangedHandler() {
                public void invoke(int qty) {
                    displayCartQty(qty, vw[0]);
                }
            };

            Service.cart().Cart.cartItemChanged.addListener(cartChanged);
            this.progress.setVisibility( View.GONE);
        }
    }

    public void addToCart(int Qty){
        this.progress.setVisibility( View.VISIBLE);
        CartItem cartItem = new CartItem();
        cartItem.setCartQty(Qty);
        cartItem.setCartID("");
        cartItem.setFoodDesc(module.foodItem.getFoodDesc());
        cartItem.setFoodID(module.foodItem.getFoodID());
        cartItem.setFoodImg(module.foodItem.getFoodImg());
        cartItem.setFoodImgUrl(module.foodItem.getFoodImgUrl());
        cartItem.setFoodPrice(module.foodItem.getFoodPrice());
        cartItem.setUserID(module.userID);
        try {
            Service.cart().addCartItem(cartItem, module.userName);
        }catch (Exception e){
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        this.progress.setVisibility( View.GONE);
    }

    public void displayCartQty(int Qty, View v) {
        Snackbar.make(v , ""+ Qty + " item(s) added to Cart.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        //Toast.makeText(this, ""+ Qty + " items added to Cart.", Toast.LENGTH_SHORT).show();
    }

}