package com.mickleentityltdnigeria.resturantapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowPictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowPictureFragment extends Fragment {

    ImageView foodImg;
    TextView foodText, foodPrice, txtFulfilledBy;
    Button btnAdd;
    ResturantDalc resturantDalc;

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
    public static Resturant resturant = new Resturant();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.progress = view.findViewById(R.id.progressBarShowPic);
        this.foodImg = view.findViewById(R.id.imgFood);
        this.foodText = view.findViewById(R.id.txtFoodDesc);
        this.foodPrice = view.findViewById(R.id.txtPrice);
        this.btnAdd = view.findViewById(R.id.btnAdd);
        this.txtFulfilledBy = view.findViewById(R.id.txtFullfilledBy);
        this.resturantDalc = new ResturantDalc();
        //
        if (module.foodItem != null) {
            //
            this.progress.setVisibility( View.VISIBLE);
            try {
                InputStream ims = new ByteArrayInputStream(ImageHelper.getInstance().base64StringToByteArray(module.foodItem.getFoodImg())); //assetManager.open(this.foodItem.getFoodUrl());
                Drawable d = Drawable.createFromStream(ims, null);
                this.foodImg.setImageDrawable(d);
                this.foodText.setText(module.foodItem.getFoodDesc());
                this.foodPrice.setText((module.foodItem.getCurrency() + module.foodItem.getFoodPrice()));
            }catch (Exception e){
                Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            btnAdd.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {
                    addToCart(1, module.foodItem);
                }
            });
            // Register interest in the completed report
            CartItemChangedHandler cartItemAdded = new CartItemChangedHandler() {
                public void invoke(List<CartItem> cartItems) {
                    callGetCartItems(cartItems, view);
                }
            };

            module.MyShoppingCart.cartItemsAdded.addListener("showPictureCartItemAdded",cartItemAdded);

            ResturantUpdatedHandler RestaurantFetched = new ResturantUpdatedHandler() {
                @Override
                public void invoke(List<Resturant> Resturant) {
                    resturant = Resturant.get(0);
                    SpannableString content = new SpannableString(("Order fulfilled by: " + resturant.getResturantName()));
                    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                    txtFulfilledBy.setText(content);
                }
            };
            resturantDalc.resturantDataFetched.addListener(RestaurantFetched);
            resturantDalc.getResturantByResturantID(module.foodItem.getResturantID());
            this.progress.setVisibility( View.GONE);
            txtFulfilledBy.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    NavHostFragment.findNavController(ShowPictureFragment.this)
                            .navigate(R.id.action_showPictureFragment_to_showMerchantFragment);
                }
            });
        }
    }

    private void addToCart(int Qty, FoodItem foodItem) {
        progress.setVisibility(View.VISIBLE);
        CartItem cartItem = new CartItem("",foodItem.foodID, foodItem.resturantID,module.userName,foodItem.foodImg,foodItem.foodPrice
                ,foodItem.currency,foodItem.foodDesc,foodItem.foodImgUrl,Qty);
        module.MyShoppingCart.AddCartItem(cartItem);
    }

    private void callGetCartItems(List<CartItem> cartItems, View view){
        progress.setVisibility(View.GONE);
        module.MyShoppingCart.getCartItems(module.userName);
    }

   /* private void displayCartQty(List<CartItem> cartItems) {
        module.MyShoppingCart.getCartItems(module.userName);
    }*/

}