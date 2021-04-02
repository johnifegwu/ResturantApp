package com.mickleentityltdnigeria.resturantapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.FoodOrderDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrder;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderDetailsEventHandler;
import com.mickleentityltdnigeria.resturantapp.utils.PaymentDetails;
import com.mickleentityltdnigeria.resturantapp.utils.module;
import com.mickleentityltdnigeria.resturantapp.utils.paymentResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mickleentityltdnigeria.resturantapp.AppGlobals.StartActivity;
import static com.mickleentityltdnigeria.resturantapp.utils.module.getFoodOrderTotalValue;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeliverOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeliverOrderFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public DeliverOrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeliverOrderFragment.
     */

    public static DeliverOrderFragment newInstance(String param1, String param2) {
        DeliverOrderFragment fragment = new DeliverOrderFragment();
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
        return inflater.inflate(R.layout.fragment_deliver_order, container, false);
    }


    ProgressBar progress;
    Button btnCollectPayment;
    FoodOrderDalc foodOrderDalc;
    List<FoodOrderDetail> foodOrderDetails;
    CustomerOrderAdapter adapter;
    FoodOrder foodOrder = null;
    FoodOrderDetail foodOrderDetail = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            this.progress = view.findViewById(R.id.progressBarDeliverOrder);
            this.btnCollectPayment = view.findViewById(R.id.btnCollectPayment);
            foodOrderDalc = new FoodOrderDalc();
            foodOrderDetails = new ArrayList<>();
            this.progress.setVisibility(View.VISIBLE);
            // Register interest in the Order Change.
            FoodOrderDetailsEventHandler orderDetailsFetched = new FoodOrderDetailsEventHandler() {
                public void invoke(List<FoodOrderDetail> orderDetails) {
                    foodOrderDetails.addAll(orderDetails);
                    adapter.appendData(orderDetails);
                    btnCollectPayment.setEnabled(true);
                    progress.setVisibility(View.GONE);
                }
            };
            foodOrderDalc.foodOrderDetailsFetched.addListener(orderDetailsFetched);
            FoodOrderDetailsEventHandler orderDetailsNotFound = new FoodOrderDetailsEventHandler() {
                @Override
                public void invoke(List<FoodOrderDetail> orderDetails) {
                    foodOrderDetails = new ArrayList<>();
                    adapter.updateData(foodOrderDetails);
                    btnCollectPayment.setEnabled(false);
                    progress.setVisibility(View.GONE);
                }
            };
            foodOrderDalc.foodOrderDetailsNotFound.addListener(orderDetailsNotFound);
            FoodOrderDetailsEventHandler foodOrderDetailUpdated = new FoodOrderDetailsEventHandler() {
                @Override
                public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                    try{
                        adapter.clearData(foodOrderDetails.get(0));
                    }catch (Exception e){
                        progress.setVisibility(View.GONE);
                        Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            };
            foodOrderDalc.foodOrderDetailsUpdated.addListener(foodOrderDetailUpdated);
            //scan QR code
            view.findViewById(R.id.btnScanQQRCode).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scanQRCode();
                }
            });
            view.findViewById(R.id.btnCollectPayment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress.setVisibility(View.VISIBLE);
                    try{
                        module.checkNetwork();
                        //clear data
                        PaymentActivity.foodOrder.clear();
                        PaymentActivity.foodOrderDetails.clear();
                        //set order details
                        PaymentActivity.foodOrder.add(foodOrder);
                        PaymentActivity.foodOrderDetails.add(foodOrderDetail);
                        // The launcher with the Intent you want to start
                        Intent intent = new Intent(requireContext(),PaymentActivity.class);
                        mStartForResult.launch(intent);
                        //
                    }catch (Exception e){
                        progress.setVisibility(View.GONE);
                        Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //
            //Reference of RecyclerView
            RecyclerView mRecyclerView = view.findViewById(R.id.deliverOrderrecyclerView);

            //Linear Layout Manager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);

            //Set Layout Manager to RecyclerView
            mRecyclerView.setLayoutManager(linearLayoutManager);
            //
            adapter = new CustomerOrderAdapter(foodOrderDetails, new CustomerAdapterClickListener() {
                @Override
                public void onPrepareOrder(FoodOrder foodOrder, FoodOrderDetail foodOrderDetail) {

                }

                @Override
                public void onShipOrder(FoodOrder foodOrder, FoodOrderDetail foodOrderDetail) {

                }

                @Override
                public void onDeliverOrder(FoodOrder Order,FoodOrderDetail OrderDetail) {
                    progress.setVisibility(View.VISIBLE);
                    try{
                        module.checkNetwork();
                        //clear data
                        PaymentActivity.foodOrder.clear();
                        PaymentActivity.foodOrderDetails.clear();
                        //set order details
                        PaymentActivity.foodOrder.add(Order);
                        PaymentActivity.foodOrderDetails.add(OrderDetail);
                        // The launcher with the Intent you want to start
                        Intent intent = new Intent(requireContext(),PaymentActivity.class);
                        mStartForResult.launch(intent);
                        //
                    }catch (Exception e){
                        progress.setVisibility(View.GONE);
                        Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onPrintOrder(FoodOrder foodOrder, FoodOrderDetail foodOrderDetail) {
                    printOrder(foodOrder, foodOrderDetail);
                }

                @Override
                public void onCallCustomer(FoodOrder foodOrder) {
                    progress.setVisibility(View.VISIBLE);
                    try{
                        String phone = foodOrder.getShippingContactPhone();
                        Uri uri = Uri.parse("tel:" + phone);
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
                        StartActivity(callIntent);
                    }catch (Exception e){
                        Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onLocateCustomer(FoodOrder foodOrder) {
                    progress.setVisibility(View.VISIBLE);
                    try{
                        String ad = foodOrder.getShippingAddress() + ", " + foodOrder.getShippingCity() + ", " + foodOrder.getShippingCountry();
                        Uri uri = Uri.parse(("geo:0,0?q=" + ad));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                        //
                        if (mapIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                            StartActivity(mapIntent);
                        } else {
                            Toast.makeText(requireContext(), "Google Map App not installed, please install a Map App and try again.", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    progress.setVisibility(View.GONE);
                }
            });
            //Set adapter to RecyclerView
            mRecyclerView.setAdapter(adapter);
            //
        } catch (Exception e) {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void printOrder(FoodOrder foodOrder, FoodOrderDetail foodOrderDetail){
        progress.setVisibility(View.VISIBLE);
        try{
            module.checkNetwork();
            //clear data
            PrintOrderActivity.foodOrder.clear();
            PrintOrderActivity.foodOrderDetails.clear();
            //set order details
            PrintOrderActivity.foodOrder.add(foodOrder);
            PrintOrderActivity.foodOrderDetails.add(foodOrderDetail);
            // The launcher with the Intent you want to start
            Intent intent = new Intent(AppGlobals.getAppContext(), PrintOrderActivity.class);
            startActivity(intent);
            progress.setVisibility(View.GONE);
            //
        }catch (Exception e){
            progress.setVisibility(View.GONE);
            Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try{
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            // Handle the Intent
                            assert intent != null;
                            PaymentDetails paymentDetails = (PaymentDetails) intent.getSerializableExtra("paymentDetails");
                            //
                            if(PaymentActivity.foodOrderDetails != null && paymentDetails != null){
                                //perform validations
                                if(getFoodOrderTotalValue(PaymentActivity.foodOrderDetails) > (paymentDetails.getAmountCollected() - paymentDetails.getChangeGiven())){
                                    throw new Exception("Invalid payment!, Total Due can not be greater than Total Value collected.");
                                }
                                for(FoodOrderDetail foodOrderDetail : PaymentActivity.foodOrderDetails){
                                    //payment details
                                    foodOrderDetail.setPaid(true);
                                    foodOrderDetail.setAmountPaid(paymentDetails.getAmountCollected());
                                    foodOrderDetail.setChangeGiven(paymentDetails.getChangeGiven());
                                    foodOrderDetail.setDatePaid(new Date());
                                    foodOrderDetail.setPaymentMedium(paymentDetails.getPaymentMedium());
                                    foodOrderDetail.setPaymentNote(paymentDetails.getPaymentNote());
                                    foodOrderDetail.setPaymentDescription(paymentDetails.getPaymentNote());
                                    //set markup details
                                    foodOrderDetail.setMarkUpPaid(true);
                                    foodOrderDetail.setMarkUpValuePaid(foodOrderDetail.getMarkUpValue());
                                    //set deliver details
                                    foodOrderDetail.setDelivered(true);
                                    foodOrderDetail.setDeliveredBy(module.userName);
                                    foodOrderDetail.setDateDelivered(new Date());
                                    foodOrderDetail.setCollectedBy(paymentDetails.getItemsCollectedBy());
                                    foodOrderDalc.updateFoodOrderDetails(foodOrderDetail);
                                }
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(requireContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });


    private void scanQRCode(){
        progress.setVisibility(View.VISIBLE);
        // The launcher with the Intent you want to start
        Intent intent = new Intent(requireContext(),ScanQRCodeActivity.class);
        mStartScanForResult.launch(intent);
        //
    }

    ActivityResultLauncher<Intent> mStartScanForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            // Handle the Intent
                            assert intent != null;
                            String QRCode = intent.getStringExtra("QRCode");
                                    try{
                                        module.checkNetwork();
                                        foodOrderDalc.getFoodOrderDetailsByID(QRCode);
                                    }catch (Exception e){
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                        }
                    }catch (Exception e){
                        progress.setVisibility(View.GONE);
                        Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

}