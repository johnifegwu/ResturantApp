package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.AddressChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.service.Service;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckOutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckOutFragment extends Fragment {

    ProgressBar progress;
    private List<CartItem> cartItems = new ArrayList<CartItem>();
    private List<Address> shippingAddresses = new ArrayList<Address>();
    private User user;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CheckOutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckOutFragment.
     */

    public static CheckOutFragment newInstance(String param1, String param2) {
        CheckOutFragment fragment = new CheckOutFragment();
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
        return inflater.inflate(R.layout.fragment_check_out, container, false);
    }

    EditText txtBillingAddress, txtBillingCity, txtBillingZipCode, txtBillingState, txtBillingCountry;
    EditText txtShippingAddress, txtShippingCity, txtShippingZipCode, txtShippingState, txtShippingCountry;
    Spinner countryList;
    TextView txtStatus;
    Button btnPlaceOrder;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        progress = view.findViewById(R.id.progressBarRegister);
        txtBillingAddress = view.findViewById(R.id.txtPlaceOrderAddress);
        txtBillingCity = view.findViewById(R.id.txtPlaceOrderCity);
        txtBillingZipCode = view.findViewById(R.id.txtPlaceOrderZipCode);
        txtBillingState = view.findViewById(R.id.txtPlaceOrderState);
        txtBillingCountry = view.findViewById(R.id.txtPlaceOrderCountry);
        //
        txtShippingAddress = view.findViewById(R.id.txtPlaceOrderShiipingAddress);
        txtShippingCity = view.findViewById(R.id.txtPlaceOrderShiipingCity);
        txtShippingZipCode = view.findViewById(R.id.txtPlaceOrderShiipingZipCode);
        txtShippingState = view.findViewById(R.id.txtPlaceOrderShiipingState);
        txtShippingCountry = view.findViewById(R.id.txtPlaceOrderShiipingCountry);
        //
        countryList =  view.findViewById(R.id.spinerPlaceOderAddress);
        txtStatus =  view.findViewById(R.id.txtOrderCartStatus);
        btnPlaceOrder =  view.findViewById(R.id.btnPlaceOrder);
        //
        this.progress.setVisibility(View.VISIBLE);
        //
        try {
            setBillingAddress();
            getBillingAddresses(view);
            this.cartItems = Service.cart().getCartItems(module.userName);
            String currency = "$";
            if(this.cartItems.size()>0) currency = this.cartItems.get(0).getCurrency();
            SetStatus(currency);
        } catch (Exception e) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        // Register interest in the CartItem Change.
        CartItemChangedHandler cartChanged = new CartItemChangedHandler() {
            public void invoke(int qty) {
                setCartTotal(qty, view.findViewById(R.id.shoppingCartView));
            }
        };
        //
        Service.cart().Cart.cartItemChanged.addListener(cartChanged);
        //
        // Register interest in the Address Change.
        AddressChangedHandler addressChanged = new AddressChangedHandler() {
            public void invoke(String add) {
                getBillingAddresses(view);
            }
        };
        Service.address().address.addressChanged.addListener(addressChanged);
        //
        view.findViewById(R.id.btnPlaceOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                progress.setVisibility(View.VISIBLE);
                try {
                    //TODO perform checkout here

                    //
                    Navigation.findNavController(view)
                            .navigate(R.id.action_checkOutFragment_to_FirstFragment);
                } catch (Exception e) {
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                progress.setVisibility(View.GONE);
            }
        });
        //
        view.findViewById(R.id.btnAddShippingAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                progress.setVisibility(View.VISIBLE);
                try {
                    Navigation.findNavController(view)
                            .navigate(R.id.action_checkOutFragment_to_newShippingAddressFragment);
                } catch (Exception e) {
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                progress.setVisibility(View.GONE);
            }
        });
        progress.setVisibility(View.GONE);
    }

    private void setBillingAddress() {
        this.user = Service.user().getUserByName(module.userName);
        this.txtBillingAddress.setText(user.getContactAddress());
        this.txtBillingCity.setText(user.getCity());
        this.txtBillingZipCode.setText(user.getZipCode());
        this.txtBillingState.setText(user.getState());
        this.txtShippingCountry.setText(user.getCountry());
    }

    private void getBillingAddresses(View v){
        this.progress.setVisibility( View.VISIBLE);
        try {
            this.shippingAddresses = Service.address().getAddresses(module.userID);
            //
            ArrayAdapter<Address> adapter = new ArrayAdapter<Address>(v.getContext(), android.R.layout.simple_spinner_item, shippingAddresses);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countryList.setAdapter(adapter);
            countryList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Address d = shippingAddresses.get(position);

                    //Get selected value of key
                    String value = d.getContactAddress();
                    String key = d.getContactAddress();
                    txtShippingAddress.setText(d.getContactAddress());
                    txtShippingCity.setText(d.getCity());
                    txtShippingZipCode.setText(d.getZipCode());
                    txtShippingState.setText(d.getState());
                    txtShippingCountry.setText(d.getCountry());
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            //
        }catch (Exception e){
            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        this.progress.setVisibility( View.GONE);
    }

    private void setCartTotal(int Qty, View v) {
            this.progress.setVisibility( View.VISIBLE);
            Snackbar.make(v, "" + Qty + " item(s) added to Cart.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //
            String currency = "$";
            if(this.cartItems.size()>0){
                currency = this.cartItems.get(0).getCurrency();
            }
            //
            SetStatus(currency);
            //
            //Toast.makeText(this, ""+ Qty + " items added to Cart.", Toast.LENGTH_SHORT).show();
            this.progress.setVisibility( View.GONE);
    }

    private void SetStatus(String currency){
        DecimalFormat dc = new DecimalFormat("#,###,##0.00");
        this.txtStatus.setText("Cart total: "+ currency +  dc.format(Service.cart().Cart.getCartTotal(module.userName)));
    }
}