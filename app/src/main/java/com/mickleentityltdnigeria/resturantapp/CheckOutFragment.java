package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mickleentityltdnigeria.resturantapp.dalc.AddressDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.FoodOrderDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.UserDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.CartItem;
import com.mickleentityltdnigeria.resturantapp.data.model.FoodOrderDetail;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.AddressChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.CartItemChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.FoodOrderDetailsEventHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
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
    private List<Address> shippingAddresses = new ArrayList<Address>();
    private User user;
    private UserDalc userDalc;
    private FoodOrderDalc orderDalc;

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
    Button btnPlaceOrder, btnNewAddress;
    SwitchCompat switchAgreement;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        progress = view.findViewById(R.id.progressBarRegister);
        switchAgreement = view.findViewById(R.id.switchOrderAgreement);
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
        btnNewAddress =  view.findViewById(R.id.btnAddShippingAddress);
        //
        this.progress.setVisibility(View.VISIBLE);
        module.addressDalc = new AddressDalc();
        userDalc = new UserDalc();
        orderDalc = new FoodOrderDalc();
        //
        try {
            setBillingAddress();
            getShippingAddresses(view);
        } catch (Exception e) {
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        // Register interest in the CartItem Change.
        CartItemChangedHandler cartItemsFetched = new CartItemChangedHandler() {
            public void invoke(List<CartItem> cartItems) {
                setCartTotal(cartItems, view.findViewById(R.id.shoppingCartView));
                SetStatus(cartItems);
            }
        };
        //
        module.MyShoppingCart.cartItemsFetched.addListener(cartItemsFetched);
        module.MyShoppingCart.getCartItems(module.userName);
        //
        // Register interest in the Address Change.
        AddressChangedHandler addressAdded = new AddressChangedHandler() {
            public void invoke(List<Address> addresses) {
                shippingAddresses.addAll(addresses);
               notifyAll();
            }
        };
        //
        module.addressDalc.addressAdded.addListener(addressAdded);
        //
        view.findViewById(R.id.btnAddShippingAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Navigation.findNavController(view)
                        .navigate(R.id.action_checkOutFragment_to_newShippingAddressFragment);
                //
            }
        });
        //
        //
        view.findViewById(R.id.switchOrderAgreement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchAgreement.isSelected()){
                    btnPlaceOrder.setEnabled(true);
                }else{
                    btnPlaceOrder.setEnabled(false);
                }
            }
        });
        //
        view.findViewById(R.id.btnPlaceOrder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                progress.setVisibility(View.VISIBLE);
                try {
                    module.checkNetwork();
                    if((txtBillingAddress.getText().toString() == "" || txtBillingCity.getText().toString() == ""
                    || txtBillingZipCode.getText().toString() == "" || txtBillingCountry.getText().toString() == "")){
                        txtBillingAddress.requestFocus();
                        throw new Exception("Please provide a Billing Address");
                    }
                    //
                    if((txtShippingAddress.getText().toString() == "" || txtShippingCity.getText().toString() == ""
                            || txtShippingZipCode.getText().toString() == "" || txtShippingCountry.getText().toString() == "")){
                        txtShippingAddress.requestFocus();
                        throw new Exception("Please provide a Shipping Address.");
                    }
                    //
                    FoodOrderDetailsEventHandler foodOrderDetailAdded = new FoodOrderDetailsEventHandler() {
                        public void invoke(List<FoodOrderDetail> orderDetails) {
                            //
                            module.MyShoppingCart.getCartItems(module.userName);
                            //
                            Snackbar.make(view, "Order placed successfully.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            //
                            Navigation.findNavController(view)
                                    .navigate(R.id.action_checkOutFragment_to_customerOrderListFragment);
                        }
                    };
                    //
                    Address shipping = new Address("",module.userID,module.AddressTYPE_SHIPPING,txtShippingAddress.getText().toString(),txtShippingCity.getText().toString(),
                            txtShippingZipCode.getText().toString(),txtShippingState.getText().toString(),txtShippingCountry.getText().toString());

                    Address billing = new Address("",module.userID,module.AddressTYPE_BILLING,txtBillingAddress.getText().toString(),txtBillingCity.getText().toString(),
                            txtBillingZipCode.getText().toString(),txtBillingState.getText().toString(),txtBillingCountry.getText().toString());
                    //
                    //
                    if(shipping.city != module.city || shipping.country != module.country || shipping.zipCode != module.zipCode){
                        throw new Exception("Shipping address Country, City and ZipCode must be the same with your current location.");
                    }
                    orderDalc.foodOrderDetailsAdded.addListener(foodOrderDetailAdded);
                    orderDalc.PlaceOrder(module.cartItems,billing,shipping);
                    //
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
        UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                user = users.get(0);
                txtBillingAddress.setText(user.getContactAddress());
                txtBillingCity.setText(user.getCity());
                txtBillingZipCode.setText(user.getZipCode());
                txtBillingState.setText(user.getState());
                txtShippingCountry.setText(user.getCountry());
            }
        };
        userDalc.userDataFetched.addListener(userDataFetched);
        userDalc.getUserByName(module.userName);
    }

    private void getShippingAddresses(View v){
        this.progress.setVisibility( View.VISIBLE);
        try {
            AddressChangedHandler addressFetched = new AddressChangedHandler() {
                public void invoke(List<Address> addresses) {
                    shippingAddresses = addresses;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, module.addressDalc.getAddressList(shippingAddresses));
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
                }
            };
            module.addressDalc.addressesFetched.addListener(addressFetched);
            module.addressDalc.getAddresses(module.userID);
            //
        }catch (Exception e){
            Snackbar.make(v, e.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

        this.progress.setVisibility( View.GONE);
    }

    private void setCartTotal(List<CartItem> cartItems, View v) {
            this.progress.setVisibility( View.VISIBLE);
            Snackbar.make(v, "" + module.getCartTotalQty(cartItems) + " item(s) added to Cart.", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            SetStatus(cartItems);
            //
            //Toast.makeText(this, ""+ Qty + " items added to Cart.", Toast.LENGTH_SHORT).show();
            this.progress.setVisibility( View.GONE);
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
            this.txtStatus.setText("Order total (+ 1% markup): "+ currency +  dc.format(t));
        }catch (Exception e){
            Toast.makeText(this.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
        }
        this.progress.setVisibility( View.GONE);
    }
}