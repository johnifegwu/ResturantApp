package com.mickleentityltdnigeria.resturantapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.FoodItemDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyNewFoodItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyNewFoodItemFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MyNewFoodItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyNewFoodItemFragment.
     */
    public static MyNewFoodItemFragment newInstance(String param1, String param2) {
        MyNewFoodItemFragment fragment = new MyNewFoodItemFragment();
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
        return inflater.inflate(R.layout.fragment_my_new_food_item, container, false);
    }

    FoodItem foodItem;
    ImageView imgNewFoodItem;
    Button btnAdd;
    EditText txtDesc, txtPrice, txtCurrencyCode;
    ProgressBar progress;
    FoodItemDalc foodItemDalc;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        foodItem = new FoodItem();
        foodItemDalc = new FoodItemDalc();
        progress = view.findViewById(R.id.progressBarMyNewFoodItem);
        txtDesc = view.findViewById(R.id.txtFoodItemDescription);
        txtPrice = view.findViewById(R.id.txtFoodItemPrice);
        txtCurrencyCode = view.findViewById(R.id.txtFoodItemCurrency);
        btnAdd = view.findViewById(R.id.btnAddNewFoodItem);
        imgNewFoodItem = view.findViewById(R.id.imgNewFoodItem);
        imgNewFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });
        FoodItemUpdatedHandler foodItemAdded = new FoodItemUpdatedHandler() {
            @Override
            public void invoke(List<FoodItem> foodItems) {
                progress.setVisibility(View.GONE);
                txtDesc.setText("");
                txtPrice.setText("");
                NavHostFragment.findNavController(MyNewFoodItemFragment.this)
                        .navigate(R.id.action_myNewFoodItemFragment_to_myFoodItemsFragment);
            }
        };
        foodItemDalc.foodItemsAdded.addListener(foodItemAdded);
        //set currency code
        Resturant resturant = MyFoodItemsFragment.resturant;
        txtCurrencyCode.setText(resturant.getCurrencyCode());
        //
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    progress.setVisibility(View.VISIBLE);
                    module.checkNetwork();
                    if(module.userType.equals(module.UserTypeSELLER2)){
                        throw new Exception("You lack the privilege to perform this task.");
                    }
                    if(foodItem.getFoodImg().isEmpty()){
                        throw new Exception("You must add a picture for this Item.");
                    }
                    if(txtDesc.getText().toString().isEmpty()){
                        throw new Exception("You must add a description for this Item.");
                    }
                    if(txtPrice.getText().toString().isEmpty()){
                        throw new Exception("You must add a price for this Item.");
                    }
                    foodItem.setZipCodes(resturant.getZipCodes());
                    foodItem.setCurrency(resturant.getCurrencyCode());
                    foodItem.setResturantID(resturant.getResturantID());
                    foodItem.setUserID(module.userID);
                    foodItem.setFoodPrice(Double.valueOf(txtPrice.getText().toString()));
                    foodItem.setFoodDesc(txtDesc.getText().toString().trim());
                    foodItemDalc.AddFoodItem(foodItem);
                    //
                }catch (Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(requireContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    try {
                        foodItem.setFoodImg(ImageHelper.getInstance().byteArrayToString(ImageHelper.getInstance().decodeFile(uri)));
                        imgNewFoodItem.setImageDrawable(ImageHelper.getInstance().imageFromString(foodItem.getFoodImg()));
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


}