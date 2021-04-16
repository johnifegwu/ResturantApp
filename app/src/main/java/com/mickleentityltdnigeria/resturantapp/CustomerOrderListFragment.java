package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.FoodOrderDalc;
import com.mickleentityltdnigeria.resturantapp.data.CustomerOrderListAdapter;
import com.mickleentityltdnigeria.resturantapp.data.CustomerOrderListRecyclerViewItemClickListener;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderDetailsEventHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.OrderDetailChangedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerOrderListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  CustomerOrderListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CustomerOrderListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerOrderListFragment.
     */
    public static CustomerOrderListFragment newInstance(String param1, String param2) {
        CustomerOrderListFragment fragment = new CustomerOrderListFragment();
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
        return inflater.inflate(R.layout.fragment_customer_order_list, container, false);
    }

    ProgressBar progress;
    FoodOrderDalc orderDalc;
    List<FoodOrderDetail> foodOrderDetails = new ArrayList<>();
    CustomerOrderListAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.progress = view.findViewById(R.id.progressBarCustomerOrderList);
        orderDalc = new FoodOrderDalc();
        this.progress.setVisibility(View.VISIBLE);

        // Register interest in the Order Change.
        FoodOrderDetailsEventHandler foodOrderUpdated = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> foodOrderDetails) {
                try{
                    orderDalc.getFoodOrderDetailsByUserID(module.userID);
                }catch(Exception e){
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        FoodOrderDetailsEventHandler orderDetailsFetched = new FoodOrderDetailsEventHandler() {
            public void invoke(List<FoodOrderDetail> orderDetails) {
                foodOrderDetails = orderDetails;
                adapter.updateData(foodOrderDetails);
                progress.setVisibility(View.GONE);
            }
        };
        FoodOrderDetailsEventHandler orderDetailsNotFound = new FoodOrderDetailsEventHandler() {
            @Override
            public void invoke(List<FoodOrderDetail> orderDetails) {
                foodOrderDetails = orderDetails;
                adapter.updateData(foodOrderDetails);
                progress.setVisibility(View.GONE);
            }
        };
        try{
            orderDalc.foodOrderDetailsUpdated.addListener(foodOrderUpdated);
            orderDalc.foodOrderDetailsNotFound.addListener(orderDetailsNotFound);
            orderDalc.foodOrderDetailsFetched.addListener(orderDetailsFetched);
            orderDalc.getFoodOrderDetailsByUserID(module.userID);
        }catch(Exception e){
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //
        view.findViewById(R.id.btnContinueShopping).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                NavHostFragment.findNavController(CustomerOrderListFragment.this)
                        .navigate(R.id.action_customerOrderListFragment_to_FirstFragment);
                //
            }
        });
        //
        try {

            //Reference of RecyclerView
            RecyclerView mRecyclerView = view.findViewById(R.id.customerOrderListRecyclerView);

            //Linear Layout Manager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);

            //Set Layout Manager to RecyclerView
            mRecyclerView.setLayoutManager(linearLayoutManager);

            //Create adapter
            adapter = new CustomerOrderListAdapter(foodOrderDetails, new CustomerOrderListRecyclerViewItemClickListener() {
                @Override
                public void onItemClicked(@NotNull FoodOrderDetail foodOrderDetail) {

                }

                @Override
                public void onUpdateButtonClicked(@NotNull FoodOrderDetail foodOrderDetail) {
                    try {
                        //
                        FoodOrderDetail fd = foodOrderDetail;
                        if (!fd.isPrinted()) {
                            //
                            fd.setCanceled(true);
                            progress.setVisibility(View.VISIBLE);
                            orderDalc.updateFoodOrderDetails(fd);
                            //
                        }
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //Set adapter to RecyclerView
            mRecyclerView.setAdapter(adapter);
            //
        } catch (Exception e) {
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}