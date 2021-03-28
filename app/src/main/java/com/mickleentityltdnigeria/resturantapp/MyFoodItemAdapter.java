package com.mickleentityltdnigeria.resturantapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickleentityltdnigeria.resturantapp.dalc.FoodItemDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItemChild;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;
import static com.mickleentityltdnigeria.resturantapp.utils.module.foodItem;

public class MyFoodItemAdapter extends RecyclerView.Adapter<MyFoodItemAdapter.ViewHolder> {

    private List<FoodItem> foodItems = new ArrayList<>();
    private final Context myContext = ApplicationContextProvider.getContext();
    private MyFoodItemRecyclerViewItemClickListener mItemClickListener;
    private FoodItemDalc foodItemDalc;

    public void UpdateDate(List<FoodItem> foodItemList) {
        this.foodItems = foodItemList;
        notifyDataSetChanged();
    }

    public MyFoodItemAdapter(List<FoodItem> foodItems, MyFoodItemRecyclerViewItemClickListener mItemClickListener) {
        this.foodItems = foodItems;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public MyFoodItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_food_item_single_row, parent, false);
        MyFoodItemAdapter.ViewHolder myViewHolder = new MyFoodItemAdapter.ViewHolder(view, myContext);
        this.foodItemDalc = new FoodItemDalc();
        ProgressBar progress = myViewHolder.itemView.findViewById(R.id.progressBarMyUpdateFoodItem);
        //create click listener
        myViewHolder.itemView.findViewById(R.id.imgUpdateFoodItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClicked(foodItems.get(myViewHolder.getLayoutPosition()), myViewHolder.itemView.findViewById(R.id.imgUpdateFoodItem));
                notifyDataSetChanged();
            }
        });
        //
        FoodItemUpdatedHandler foodItemUpdated = new FoodItemUpdatedHandler() {
            @Override
            public void invoke(List<FoodItem> foodItems) {
                foodItemDalc.getFoodItemsByResturant(module.userData.getResturantID());
            }
        };
        foodItemDalc.foodItemsUpdated.addListener(foodItemUpdated);
        //
        FoodItemUpdatedHandler foodItemsFetched = new FoodItemUpdatedHandler() {
            @Override
            public void invoke(List<FoodItem> foodItemList) {
                progress.setVisibility(View.GONE);
                foodItems = foodItemList;
                notifyDataSetChanged();
            }
        };
        foodItemDalc.foodItemsFetched.addListener(foodItemsFetched);
        //
        FoodItemUpdatedHandler foodItemNotFound = new FoodItemUpdatedHandler() {
            @Override
            public void invoke(List<FoodItem> foodItemList) {
                progress.setVisibility(View.GONE);
                foodItems = new ArrayList<>();
                notifyDataSetChanged();
            }
        };
        foodItemDalc.foodItemsNotFound.addListener(foodItemNotFound);
        //add handler for btnUpdate
        myViewHolder.itemView.findViewById(R.id.btnUpdateFoodItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    progress.setVisibility(View.VISIBLE);
                    module.checkNetwork();
                    if(module.userType.equals(module.UserTypeSELLER2)){
                        throw new Exception("You lack the privilege to perform this task.");
                    }
                    FoodItem foodItem = foodItems.get(myViewHolder.getLayoutPosition());
                    EditText txtDesc = myViewHolder.itemView.findViewById(R.id.txtUpdateFoodItemDescription);
                    EditText txtPrice = myViewHolder.itemView.findViewById(R.id.txtUpdateFoodItemPrice);
                    if(foodItem.getFoodImg().isEmpty()){
                        throw new Exception("You must add a picture for this Item.");
                    }
                    if(txtDesc.getText().toString().isEmpty()){
                        throw new Exception("You must add a description for this Item.");
                    }
                    if(txtPrice.getText().toString().isEmpty()){
                        throw new Exception("You must add a price for this Item.");
                    }
                    Resturant resturant = MyFoodItemsFragment.resturant;
                    Map<String,FoodItemChild> zips = new HashMap<>();
                    for(String s:resturant.getZipCodes().split(" ")){
                        zips.put(s, new FoodItemChild(foodItem.getFoodID(), s.trim()));
                    }
                    foodItem.setZipCodes(zips);
                    foodItem.setUserID(module.userID);
                    foodItem.setFoodPrice(Double.valueOf(txtPrice.getText().toString()));
                    foodItem.setFoodDesc(txtDesc.getText().toString().trim());
                    foodItemDalc.UpdateFoodItem(foodItem);
                    //
                }catch (Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyFoodItemAdapter.ViewHolder holder, int position) {
        holder.bind(foodItems.get(position));
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private Context myContext;

        public ViewHolder(@NonNull View view, Context myContext) {
            super(view);
            this.myContext = myContext;
        }

        void bind(FoodItem foodItem) {
            ImageView img = itemView.findViewById(R.id.imgUpdateFoodItem);
            EditText txtDesc = itemView.findViewById(R.id.txtUpdateFoodItemDescription);
            EditText txtPrice = itemView.findViewById(R.id.txtUpdateFoodItemPrice);
            EditText txtCurrency = itemView.findViewById(R.id.txtUpdateFoodItemCurrency);
            img.setImageDrawable(ImageHelper.getInstance().imageFromString(foodItem.getFoodImg()));
            txtCurrency.setText(foodItem.getCurrency());
            txtPrice.setText(String.valueOf(foodItem.getFoodPrice()));
            txtDesc.setText(foodItem.getFoodDesc());
        }

    }
}

//RecyclerView Click Listener
interface MyFoodItemRecyclerViewItemClickListener {
    void onItemClicked(FoodItem foodItem, ImageView img);
}
