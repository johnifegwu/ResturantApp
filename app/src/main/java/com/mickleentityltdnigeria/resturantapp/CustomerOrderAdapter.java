package com.mickleentityltdnigeria.resturantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickleentityltdnigeria.resturantapp.dalc.FoodItemDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.FoodOrderDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodItemUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderEventHandler;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrderAdapter extends RecyclerView.Adapter<CustomerOrderAdapter.ViewHolder> {

    private List<FoodOrderDetail> foodOrderDetails = new ArrayList<>();
    private final Context myContext = ApplicationContextProvider.getContext();
    public CustomerAdapterClickListener mItemClickListener;

    public CustomerOrderAdapter(List<FoodOrderDetail> foodOrderDetails, CustomerAdapterClickListener mItemClickListener) {
        this.foodOrderDetails = foodOrderDetails;
        this.mItemClickListener = mItemClickListener;
    }

    public void updateData(List<FoodOrderDetail> foodOrderDetailList) {
        this.foodOrderDetails = foodOrderDetailList;
        notifyDataSetChanged();
    }

    public void appendData(List<FoodOrderDetail> foodOrderDetailList) {
        this.foodOrderDetails.removeAll(foodOrderDetailList);
        this.foodOrderDetails.addAll(foodOrderDetailList);
        notifyDataSetChanged();
    }

    public void clearData(FoodOrderDetail foodOrderDetail) {
        this.foodOrderDetails.remove(foodOrderDetail);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomerOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_orders_single_row, parent, false);
        return new CustomerOrderAdapter.ViewHolder(view, myContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerOrderAdapter.ViewHolder holder, int position) {
        holder.bind(foodOrderDetails.get(position));
    }

    @Override
    public int getItemCount() {
        return foodOrderDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private Context myContext;

        public ViewHolder(@NonNull View view, Context myContext) {
            super(view);
            this.myContext = myContext;
        }

        void bind(FoodOrderDetail foodOrderDetail) {
            FoodItemDalc foodItemDalc = new FoodItemDalc();
            FoodOrderDalc foodOrderDalc = new FoodOrderDalc();
            try {
                ImageView imgCusOrderImage = itemView.findViewById(R.id.imgCusOrderImage);
                TextView txtCusOrderItemDesc = itemView.findViewById(R.id.txtCusOrderItemDesc);
                TextView txtCusOrderQty = itemView.findViewById(R.id.txtCusOrderQty);
                TextView txtCusOrderPrice = itemView.findViewById(R.id.txtCusOrderPrice);
                TextView txtCusOrderTotal = itemView.findViewById(R.id.txtCusOrderTotal);
                TextView txtCusOrderContactPerson = itemView.findViewById(R.id.txtCusOrderContactPerson);
                TextView txtCusOrderPhone = itemView.findViewById(R.id.txtCusOrderPhone);
                TextView txtCusOrderAddress = itemView.findViewById(R.id.txtCusOrderAddress);
                TextView txtCusOrderCity = itemView.findViewById(R.id.txtCusOrderCity);
                TextView txtCusOrderZipCode = itemView.findViewById(R.id.txtCusOrderZipCode);
                //Button
                Button btnPrepareCusOrder = itemView.findViewById(R.id.btnPrepareCusOrder);
                Button btnShipCusOrder = itemView.findViewById(R.id.btnShipCusOrder);
                //Image Button
                ImageView btnDeliverCusOrder = itemView.findViewById(R.id.btnDeliverCusOrder);
                ImageView btnPrintCusOrder = itemView.findViewById(R.id.btnPrintCusOrder);
                ImageView btnCusOrderCallCustomer = itemView.findViewById(R.id.btnCusOrderCallCustomer);
                ImageView btnCusOrderLocation = itemView.findViewById(R.id.btnCusOrderLocation);
                //progress
                ProgressBar progress = itemView.findViewById(R.id.progressBarNewOrder);
                //Initialize controls
                if (foodOrderDetail.isCanceled) {
                    btnPrepareCusOrder.setEnabled(false);
                    btnShipCusOrder.setEnabled(false);
                    btnDeliverCusOrder.setEnabled(false);
                    btnPrintCusOrder.setEnabled(false);
                } else if (!foodOrderDetail.isPrinted && !foodOrderDetail.isShipped && !foodOrderDetail.isDelivered) {
                    btnPrepareCusOrder.setEnabled(true);
                    btnShipCusOrder.setEnabled(false);
                    btnDeliverCusOrder.setEnabled(false);
                } else if (foodOrderDetail.isPrinted && !foodOrderDetail.isShipped && !foodOrderDetail.isDelivered) {
                    btnPrepareCusOrder.setEnabled(false);
                    btnShipCusOrder.setEnabled(true);
                    btnDeliverCusOrder.setEnabled(false);
                } else if (foodOrderDetail.isPrinted && foodOrderDetail.isShipped && !foodOrderDetail.isDelivered) {
                    btnPrepareCusOrder.setEnabled(false);
                    btnShipCusOrder.setEnabled(false);
                    btnDeliverCusOrder.setEnabled(true);
                } else if (foodOrderDetail.isPrinted && foodOrderDetail.isShipped && foodOrderDetail.isDelivered) {
                    btnPrepareCusOrder.setEnabled(false);
                    btnShipCusOrder.setEnabled(false);
                    btnDeliverCusOrder.setEnabled(false);
                }
                //Set picture and Text
                imgCusOrderImage.setImageResource(R.drawable.loadingimage);
                try {
                    NumberFormat dc =  NumberFormat.getNumberInstance(); // new DecimalFormat("#,###,##0.00");
                    String cu = foodOrderDetail.getCurrency();
                    //
                    FoodItemUpdatedHandler foodItemsFetched = new FoodItemUpdatedHandler() {
                        @Override
                        public void invoke(List<FoodItem> foodItems) {
                            progress.setVisibility(View.GONE);
                            foodOrderDetail.setFoodImg(foodItems.get(0).getFoodImg());
                            imgCusOrderImage.setImageDrawable(ImageHelper.getInstance().imageFromString(foodOrderDetail.getFoodImg()));
                        }
                    };
                    foodItemDalc.foodItemsFetched.addListener(foodItemsFetched);
                    if(foodOrderDetail.getFoodImg() == null){
                        progress.setVisibility(View.VISIBLE);
                        foodItemDalc.getFoodItemByFoodID(foodOrderDetail.getFoodID());
                    }else{
                        imgCusOrderImage.setImageDrawable(ImageHelper.getInstance().imageFromString(foodOrderDetail.getFoodImg()));
                    }
                    //
                    txtCusOrderItemDesc.setText(foodOrderDetail.getFoodDesc());
                    //Continue Set Text
                    txtCusOrderQty.setText(String.valueOf(foodOrderDetail.getFoodQty()));
                    txtCusOrderPrice.setText((cu + dc.format(foodOrderDetail.getFoodPrice())));
                    txtCusOrderTotal.setText((cu + dc.format((foodOrderDetail.getSubTotal()))));
                    //set onClickListener for Prepare, Ship and Deliver
                    btnPrepareCusOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.onPrepareOrder(foodOrderDetail.getFoodOrder(), foodOrderDetail);
                        }
                    });
                    btnShipCusOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.onShipOrder(foodOrderDetail.getFoodOrder(), foodOrderDetail);
                        }
                    });
                    btnDeliverCusOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.onDeliverOrder(foodOrderDetail.getFoodOrder(), foodOrderDetail);
                        }
                    });
                    btnPrintCusOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.onPrintOrder(foodOrderDetail.getFoodOrder(), foodOrderDetail);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                //
                FoodOrderEventHandler foodOrderFetched = new FoodOrderEventHandler() {
                    @Override
                    public void invoke(List<FoodOrder> foodOrders) {
                        foodOrderDetail.setFoodOrder(foodOrders.get(0));
                        txtCusOrderContactPerson.setText(foodOrderDetail.getFoodOrder().getShippingContactPerson());
                        txtCusOrderPhone.setText(foodOrderDetail.getFoodOrder().getShippingContactPhone());
                        txtCusOrderAddress.setText(foodOrderDetail.getFoodOrder().getShippingAddress());
                        txtCusOrderCity.setText(foodOrderDetail.getFoodOrder().getShippingCity());
                        txtCusOrderZipCode.setText(foodOrderDetail.getFoodOrder().getShippingZipCode());
                        //set onClickListener for Calls and Location
                        btnCusOrderCallCustomer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mItemClickListener.onCallCustomer(foodOrderDetail.getFoodOrder());
                            }
                        });
                        btnCusOrderLocation.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mItemClickListener.onLocateCustomer(foodOrderDetail.getFoodOrder());
                            }
                        });
                    }
                };
                if(foodOrderDetail.getFoodOrder() == null){
                    foodOrderDalc.foodOrdersFetched.addListener(foodOrderFetched);
                    foodOrderDalc.getFoodOrderByOrderID(foodOrderDetail.getOderID());
                }else{
                    txtCusOrderContactPerson.setText(foodOrderDetail.getFoodOrder().getShippingContactPerson());
                    txtCusOrderPhone.setText(foodOrderDetail.getFoodOrder().getShippingContactPhone());
                    txtCusOrderAddress.setText(foodOrderDetail.getFoodOrder().getShippingAddress());
                    txtCusOrderCity.setText(foodOrderDetail.getFoodOrder().getShippingCity());
                    txtCusOrderZipCode.setText(foodOrderDetail.getFoodOrder().getShippingZipCode());
                    //set onClickListener for Calls and Location
                    btnCusOrderCallCustomer.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.onCallCustomer(foodOrderDetail.getFoodOrder());
                        }
                    });
                    btnCusOrderLocation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mItemClickListener.onLocateCustomer(foodOrderDetail.getFoodOrder());
                        }
                    });
                }

            } catch (Exception e) {
                Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

//RecyclerView Click Listener
interface CustomerAdapterClickListener {
    void onPrepareOrder(FoodOrder foodOrder, FoodOrderDetail foodOrderDetail);

    void onShipOrder(FoodOrder foodOrder, FoodOrderDetail foodOrderDetail);

    void onDeliverOrder(FoodOrder foodOrder, FoodOrderDetail foodOrderDetail);

    void onPrintOrder(FoodOrder foodOrder, FoodOrderDetail foodOrderDetail);

    void onCallCustomer(FoodOrder foodOrder);

    void onLocateCustomer(FoodOrder foodOrder);

}
