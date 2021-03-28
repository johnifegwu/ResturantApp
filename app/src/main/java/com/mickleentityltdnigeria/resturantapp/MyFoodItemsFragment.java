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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.FoodItemDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyFoodItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyFoodItemsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MyFoodItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyFoodItemsFragment.
     */
    public static MyFoodItemsFragment newInstance(String param1, String param2) {
        MyFoodItemsFragment fragment = new MyFoodItemsFragment();
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
        return inflater.inflate(R.layout.fragment_my_food_items, container, false);
    }

    Button btnNewFoodItem;
    ProgressBar progress;
    public static Resturant resturant = new Resturant();
    ResturantDalc resturantDalc;
    RecyclerView recyclerView;
    MyFoodItemAdapter adapter;
    List<FoodItem> foodItemList = new ArrayList<>();
    FoodItemDalc foodItemDalc;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        resturantDalc = new ResturantDalc();
        foodItemDalc = new FoodItemDalc();
        //
        this.progress = view.findViewById(R.id.progressBarMyFoodItems);
        this.btnNewFoodItem = view.findViewById(R.id.btnNewFoodItem);
        this.btnNewFoodItem.setEnabled(false);
        ResturantUpdatedHandler restaurantsFetched = new ResturantUpdatedHandler() {
            @Override
            public void invoke(List<Resturant> Resturant) {
                progress.setVisibility(View.GONE);
                resturant = Resturant.get(0);
                if(resturant.isApproved() && !resturant.isSuspended()){
                    btnNewFoodItem.setEnabled(true);
                }
            }
        };
        resturantDalc.resturantDataFetched.addListener(restaurantsFetched);
        progress.setVisibility(View.VISIBLE);
        resturantDalc.getResturantByResturantID(module.userData.getResturantID());
       //
        btnNewFoodItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(module.userType.equals(module.UserTypeSELLER2)){
                        throw new Exception("You lack the privilege to perform this task.");
                    }
                    NavHostFragment.findNavController(MyFoodItemsFragment.this)
                            .navigate(R.id.action_myFoodItemsFragment_to_myNewFoodItemFragment);
                }catch (Exception e){
                    Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Reference of RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewFoodItems);

        //Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false);

        //Set Layout Manager to RecyclerView
        recyclerView.setLayoutManager(linearLayoutManager);

        //set adapter
        adapter = new MyFoodItemAdapter(foodItemList, new MyFoodItemRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(FoodItem foodItem, ImageView img) {
                UpdatePicture(foodItem,img);
            }
        });
        //Set adapter to RecyclerView
        recyclerView.setAdapter(adapter);
        //get foodItems
        FoodItemUpdatedHandler foodItemsFetched = new FoodItemUpdatedHandler() {
            @Override
            public void invoke(List<FoodItem> foodItems) {
                progress.setVisibility(View.GONE);
                foodItemList = foodItems;
                adapter.UpdateDate(foodItemList);
            }
        };
        foodItemDalc.foodItemsFetched.addListener(foodItemsFetched);
        foodItemDalc.getFoodItemsByResturant(module.userData.getResturantID());
        //
    }

    private void UpdatePicture(FoodItem foodItem, ImageView img){
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        // Handle the returned Uri
                        try {
                            foodItem.setFoodImg(ImageHelper.getInstance().byteArrayToString(ImageHelper.getInstance().decodeFile(uri)));
                            img.setImageDrawable(ImageHelper.getInstance().imageFromString(foodItem.getFoodImg()));
                        } catch (Exception e) {
                            Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}