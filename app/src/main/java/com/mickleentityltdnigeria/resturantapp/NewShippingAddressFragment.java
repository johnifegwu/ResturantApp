package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.service.Service;
import com.mickleentityltdnigeria.resturantapp.utils.module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewShippingAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewShippingAddressFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NewShippingAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewShippingAddressFragment.
     */

    public static NewShippingAddressFragment newInstance(String param1, String param2) {
        NewShippingAddressFragment fragment = new NewShippingAddressFragment();
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
        return inflater.inflate(R.layout.fragment_new_shipping_address, container, false);
    }

    ProgressBar progress;
    Button btnSave;
    EditText txtShippingAddress, txtShippingCity, txtShippingZipCode, txtShippingState, txtShippingCountry;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progress = view.findViewById(R.id.progressBarRegister);
        //
        txtShippingAddress = view.findViewById(R.id.txtNewShippingAddress);
        txtShippingCity = view.findViewById(R.id.txtNewShippingCity);
        txtShippingZipCode = view.findViewById(R.id.txtNewShippingZipCode);
        txtShippingState = view.findViewById(R.id.txtNewShippingState);
        txtShippingCountry = view.findViewById(R.id.txtNewShippingCountry);
        //
        btnSave =  view.findViewById(R.id.btnSaveNewShippingAddress);
        //
        view.findViewById(R.id.btnSaveNewShippingAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                try {
                    //
                    if(txtShippingAddress.getText().toString() == "" || txtShippingCity.getText().toString() == "" || txtShippingZipCode.getText().toString() == "" ||
                            txtShippingCountry.getText().toString() == ""){
                        txtShippingAddress.requestFocus();
                        throw new Exception("All fields are required.");
                    }
                    //
                    Address address = new Address("", module.userID,module.AddressTYPE_SHIPPING,txtShippingAddress.getText().toString()
                    , txtShippingCity.getText().toString(),txtShippingZipCode.getText().toString(),txtShippingState.getText().toString(),txtShippingCountry.getText().toString());
                    Service.address().AddAddress(address);
                    //
                    Navigation.findNavController(view)
                            .navigate(R.id.action_newShippingAddressFragment_to_checkOutFragment);
                    //
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                progress.setVisibility(View.GONE);
            }
        });
        //

    }

}