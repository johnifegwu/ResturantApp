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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShoppingCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingCartFragment extends Fragment {

    TextView status;
    Button btnCheckOut;
    ProgressBar progress;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ShoppingCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingCartFragment.
     */
    public static ShoppingCartFragment newInstance(String param1, String param2) {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
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
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.status = (TextView) view.findViewById(R.id.txtCartStatus);
        this.progress = (ProgressBar) view.findViewById(R.id.progressBarCheckOut);
        this.progress.setVisibility( View.VISIBLE);
        //
        view.findViewById(R.id.btnCheckOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_shoppingCartFragment_to_checkOutFragment);
                //
            }
        });
        // Register interest in the CartItem Change.
        CartItemChangedHandler cartItemsFetched = new CartItemChangedHandler() {
            public void invoke(List<CartItem> cartItems) {
                displayCartQty(cartItems, view.findViewById(R.id.shoppingCartView));
            }
        };
       module.MyShoppingCart.cartItemsFetched.addListener(cartItemsFetched);
       module.MyShoppingCart.getCartItems(module.userName);
        try {

            //Reference of RecyclerView
            RecyclerView mRecyclerView = view.findViewById(R.id.shoppingCartRecyclerView);

            //Linear Layout Manager
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppGlobals.getAppContext(), RecyclerView.VERTICAL, false);

            //Set Layout Manager to RecyclerView
            mRecyclerView.setLayoutManager(linearLayoutManager);

            //Create adapter
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(module.cartItems, new CartRecyclerViewItemClickListener() {
                @Override
                public void onItemClicked(@NotNull CartItem cartItem) {

                }
            });

            //Set adapter to RecyclerView
            mRecyclerView.setAdapter(adapter);
            //
        }catch (Exception e){
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        this.progress.setVisibility( View.GONE);
    }

    private void updateAdapter(View view){
        try {
            //Reference of RecyclerView
            RecyclerView mRecyclerView = view.findViewById(R.id.shoppingCartRecyclerView);

            //Create adapter
            ShoppingCartAdapter adapter = new ShoppingCartAdapter(module.cartItems, new CartRecyclerViewItemClickListener() {
                @Override
                public void onItemClicked(@NotNull CartItem cartItem) {

                }
            });

            //Set adapter to RecyclerView
            mRecyclerView.swapAdapter(adapter,false);
        }catch (Exception e){
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    private void SetStatus(List<CartItem> cartItems){
        this.progress.setVisibility( View.VISIBLE);
        try {
            DecimalFormat dc = new DecimalFormat("#,###,##0.00");
            double t = module.getCartTotalValue(cartItems);
            //
            String currency = "$";
            if(module.cartItems.size()>0){
                currency = module.cartItems.get(0).getCurrency();
            }
            //
            this.status.setText("Cart total (+ 1% markup): "+ currency +  dc.format(t));
        }catch (Exception e){
            Toast.makeText(this.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
        this.progress.setVisibility( View.GONE);
    }

    public void displayCartQty(List<CartItem> cartItems, View v) {
        this.progress.setVisibility( View.VISIBLE);
        Snackbar.make(v, "" + module.getCartTotalQty(cartItems) + " item(s) added to Cart.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        SetStatus(cartItems);
        updateAdapter(v);
        //Toast.makeText(this, ""+ Qty + " items added to Cart.", Toast.LENGTH_SHORT).show();
        this.progress.setVisibility( View.GONE);
    }
}