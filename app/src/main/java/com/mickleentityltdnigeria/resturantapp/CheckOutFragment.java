package com.mickleentityltdnigeria.resturantapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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

import com.google.android.gms.common.internal.StringResourceValueReader;
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

import static com.mickleentityltdnigeria.resturantapp.AppGlobals.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CheckOutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckOutFragment extends Fragment {

    ProgressBar progress;
    private List<Address> shippingAddresses = new ArrayList<>();
    private User user;
    Address shippingAddress;
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

    EditText txtBillingAddress,txtBillingPhone, txtBillingCity, txtBillingZipCode, txtBillingContactPerson;
    EditText txtShippingAddress,txtShippingPhone , txtShippingCity, txtShippingZipCode, txtShippingContactPerson;
    TextView txtGetMICKLEPAYWALLETID;
    Spinner spinerPlaceOderAddress;
    TextView txtStatus;
    Button btnPlaceOrder, btnNewAddress;
    SwitchCompat switchAgreement;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        progress = view.findViewById(R.id.progressBarCheckOut);
        switchAgreement = view.findViewById(R.id.switchOrderAgreement);
        txtBillingAddress = view.findViewById(R.id.txtPlaceOrderAddress);
        txtBillingCity = view.findViewById(R.id.txtPlaceOrderCity);
        txtBillingZipCode = view.findViewById(R.id.txtPlaceOrderZipCode);
        txtBillingContactPerson = view.findViewById(R.id.txtBillingContactPerson);
        txtBillingPhone = view.findViewById(R.id.txtBillingPhone);
        //
        txtShippingAddress = view.findViewById(R.id.txtPlaceOrderShiipingAddress);
        txtShippingCity = view.findViewById(R.id.txtPlaceOrderShiipingCity);
        txtShippingZipCode = view.findViewById(R.id.txtPlaceOrderShiipingZipCode);
        txtShippingContactPerson = view.findViewById(R.id.txtShippingContactPerson);
        txtShippingPhone = view.findViewById(R.id.txtCheckoutShippingPhone);
        //
        spinerPlaceOderAddress = view.findViewById(R.id.spinerPlaceOderAddress);
        txtStatus = view.findViewById(R.id.txtOrderCartStatus);
        btnPlaceOrder = view.findViewById(R.id.btnPlaceOrder);
        btnNewAddress = view.findViewById(R.id.btnAddShippingAddress);
        txtGetMICKLEPAYWALLETID = view.findViewById(R.id.txtGetMICKLEPAYWALLETID);
        //
        this.progress.setVisibility(View.VISIBLE);
        module.addressDalc = new AddressDalc();
        userDalc = new UserDalc();
        orderDalc = new FoodOrderDalc();
        btnPlaceOrder.setEnabled(false);
        //
        txtGetMICKLEPAYWALLETID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String web = getString(R.string.mickle_pay_url);
                    Uri uri = Uri.parse("https://" + web);
                    Intent callIntent = new Intent(Intent.ACTION_VIEW, uri);
                    StartActivity(callIntent);
                }catch (Exception e){
                    Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        //
        try {
            setBillingAddress();
            getShippingAddresses();
        } catch (Exception e) {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        // Register interest in the CartItem Change.
        CartItemChangedHandler cartItemsFetched = new CartItemChangedHandler() {
            public void invoke(List<CartItem> cartItems) {
                setCartTotal(cartItems);
                SetStatus(cartItems);
            }
        };
        //
        try{
            module.MyShoppingCart.cartItemsFetched.removeListener("CheckOutShoppingCartFetched");
        }catch (Exception e){
        }
        module.MyShoppingCart.cartItemsFetched.addListener("CheckOutShoppingCartFetched",cartItemsFetched);
        try {
            module.checkNetwork();
            module.MyShoppingCart.getCartItems(module.userName);
        } catch (Exception e) {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
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
        view.findViewById(R.id.switchOrderAgreement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchAgreement.isChecked()) {
                    btnPlaceOrder.setEnabled(true);
                } else {
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
                    if ((txtBillingAddress.getText().toString().isEmpty() || txtBillingCity.getText().toString().isEmpty()
                            || txtBillingZipCode.getText().toString().isEmpty() || txtBillingContactPerson.getText().toString().isEmpty() || txtBillingPhone.getText().toString().isEmpty())) {
                        txtBillingAddress.requestFocus();
                        throw new Exception("Please provide a Billing Address");
                    }
                    //
                    if ((txtShippingAddress.getText().toString().isEmpty() || txtShippingCity.getText().toString().isEmpty()||txtShippingPhone.getText().toString().isEmpty()
                            || txtShippingZipCode.getText().toString().isEmpty() || txtShippingContactPerson.getText().toString().isEmpty())) {
                        txtShippingAddress.requestFocus();
                        throw new Exception("Please provide a Shipping Address.");
                    }
                    //
                    FoodOrderDetailsEventHandler foodOrderDetailAdded = new FoodOrderDetailsEventHandler() {
                        public void invoke(List<FoodOrderDetail> orderDetails) {
                            //
                            try {
                                module.MyShoppingCart.getCartItems(module.userName);
                                //
                                Toast.makeText(requireContext(), "Order placed successfully.", Toast.LENGTH_SHORT).show();
                                //
                                NavHostFragment.findNavController(CheckOutFragment.this)
                                        .navigate(R.id.action_checkOutFragment_to_customerOrderListFragment);
                            } catch (Exception e) {
                                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    //
                    Address billing = new Address("", module.userID, module.AddressTYPE_SHIPPING, user.getContactAddress(), user.getCity(),
                            user.getZipCode(), user.getState(), user.getCountry(), user.getFirstName() + " " + user.getLastName(), user.getMobilePhone());
                    //
                    if (!shippingAddress.getCountry().equals(module.country)  && !shippingAddress.getZipCode().equals(module.zipCode)) {
                        throw new Exception("Shipping address Country and ZipCode must be the same with your current location.");
                    }
                    orderDalc.foodOrderDetailsAdded.addListener(foodOrderDetailAdded);
                    orderDalc.PlaceOrder(module.cartItems, billing, shippingAddress);
                    //
                } catch (Exception e) {
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                txtBillingContactPerson.setText((user.getFirstName() + " " + user.getLastName()));
                txtBillingPhone.setText(user.getMobilePhone());
                //disable
                txtBillingAddress.setKeyListener(null);
                txtBillingCity.setKeyListener(null);
                txtBillingZipCode.setKeyListener(null);
                txtBillingContactPerson.setKeyListener(null);
                txtBillingPhone.setKeyListener(null);
            }
        };
        userDalc.userDataFetched.addListener(userDataFetched);
        userDalc.getUserByName(module.userName);
    }


    private void getShippingAddresses() {
        this.progress.setVisibility(View.VISIBLE);
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, module.addressDalc.getAddressList(shippingAddresses));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinerPlaceOderAddress.setAdapter(adapter);
            spinerPlaceOderAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    shippingAddress = shippingAddresses.get(position);

                    //Get selected value of key
                    String value = shippingAddress.getContactAddress();
                    String key = shippingAddress.getContactAddress();
                    txtShippingAddress.setText(shippingAddress.getContactAddress());
                    txtShippingCity.setText(shippingAddress.getCity());
                    txtShippingZipCode.setText(shippingAddress.getZipCode());
                    txtShippingContactPerson.setText(shippingAddress.getContactPerson());
                    txtShippingPhone.setText(shippingAddress.getContactPhone());
                    //disabled
                    txtShippingAddress.setKeyListener(null);
                    txtShippingCity.setKeyListener(null);
                    txtShippingZipCode.setKeyListener(null);
                    txtShippingContactPerson.setKeyListener(null);
                    txtShippingPhone.setKeyListener(null);
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            AddressChangedHandler addressFetched = new AddressChangedHandler() {
                public void invoke(List<Address> addresses) {
                    shippingAddresses = addresses;
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, module.addressDalc.getAddressList(shippingAddresses));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinerPlaceOderAddress.setAdapter(adapter);
                }
            };
            //
            AddressChangedHandler addressAdded = new AddressChangedHandler() {
                public void invoke(List<Address> addresses) {
                    shippingAddresses.addAll(addresses);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, module.addressDalc.getAddressList(shippingAddresses));
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinerPlaceOderAddress.setAdapter(adapter);
                }
            };
            try{
                module.addressDalc.addressesFetched.removeListener("CheckOutShoppingCartAddressFetched");
                module.addressDalc.addressAdded.removeListener("CheckOutShoppingCartAddressAdded");
            }catch (Exception e){
            }
            module.addressDalc.addressesFetched.addListener("CheckOutShoppingCartAddressFetched",addressFetched);
            module.addressDalc.addressAdded.addListener("CheckOutShoppingCartAddressAdded",addressAdded);
            //
            module.addressDalc.getAddresses(module.userID);
            //
        } catch (Exception e) {
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        this.progress.setVisibility(View.GONE);
    }

    private void setCartTotal(List<CartItem> cartItems) {
        this.progress.setVisibility(View.VISIBLE);
        SetStatus(cartItems);
        this.progress.setVisibility(View.GONE);
    }

    private void SetStatus(List<CartItem> cartItems) {
        this.progress.setVisibility(View.VISIBLE);
        try {
            DecimalFormat dc = new DecimalFormat("#,###,##0.00");
            double t = module.getCartTotalValue(cartItems);
            //
            String currency = "$";
            if (module.cartItems.size() > 0) {
                currency = module.cartItems.get(0).getCurrency();
            }
            //
            this.txtStatus.setText(("Order total (+ 1% markup): " + currency + dc.format(t)));
        } catch (Exception e) {
            Toast.makeText(this.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        this.progress.setVisibility(View.GONE);
    }
}