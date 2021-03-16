package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.CountryDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Country;
import com.mickleentityltdnigeria.resturantapp.utils.module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellOnBonAppetit2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellOnBonAppetit2Fragment extends Fragment {

    EditText txtAddress, txtEmail, txtIDD, txtPhone, txtCity, txtZipCode, txtState;
    TextView txtCurrency;
    Spinner spinnerCountry;
    Button btnNext, btnBack;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SellOnBonAppetit2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellOnBonAppetit2Fragment.
     */
    public static SellOnBonAppetit2Fragment newInstance(String param1, String param2) {
        SellOnBonAppetit2Fragment fragment = new SellOnBonAppetit2Fragment();
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
        return inflater.inflate(R.layout.fragment_sell_on_bon_appetit2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.btnNext = view.findViewById(R.id.btnNext2);
        this.btnBack = view.findViewById(R.id.btnBack1);
        this.txtCity = view.findViewById(R.id.txtNewRestaurantCity);
        this.txtEmail = view.findViewById(R.id.txtNewRestaurantEmail);
        this.txtAddress = view.findViewById(R.id.txtNewRestaurantAddress);
        this.txtIDD = view.findViewById(R.id.txtNewRestaurantIDD);
        this.txtPhone = view.findViewById(R.id.txtNewRestaurantPhone);
        this.txtState = view.findViewById(R.id.txtNewRestaurantState);
        this.txtZipCode = view.findViewById(R.id.txtNewRestaurantZipCode);
        this.spinnerCountry = view.findViewById(R.id.spinnerNewRestaurantCountry);
        this.txtCurrency = view.findViewById(R.id.txtNewRestaurantCurrency);
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, CountryDalc.getCountryNamesList(module.myCountries));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(adapter);
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Country d = module.myCountries.get(position);
                //Get selected value of key
                String value = d.getCountryName();
                String key = d.getCountryName();
                txtIDD.setText(("+" + d.getDialCode()));
                txtCurrency.setText(d.getCurrencyCode());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SellOnBonAppetit2Fragment.this)
                        .navigate(R.id.action_sellOnBonAppetit2Fragment_to_sellOnBonAppetittFragment);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(txtEmail.getText().toString().isEmpty() || txtZipCode.getText().toString().isEmpty() || txtCity.getText().toString().isEmpty() || txtIDD.getText().toString().isEmpty() || txtPhone.getText().toString().isEmpty()){
                        throw new Exception("Country, City, Zip Code, Phone number and Email are required fields.");
                    }
                    module.newResturant.setZipCode(txtZipCode.getText().toString().trim());
                    module.newResturant.setCity(txtCity.getText().toString().trim());
                    module.newResturant.setState(txtState.getText().toString().trim());
                    module.newResturant.setPhone((txtIDD.getText().toString().trim() + txtPhone.getText().toString().trim()));
                    module.newResturant.setAddress(txtAddress.getText().toString());
                    module.newResturant.setEmail(txtEmail.getText().toString().trim());
                    module.newResturant.setCountry(spinnerCountry.getSelectedItem().toString());
                    module.newResturant.setCurrencyCode(txtCurrency.getText().toString().trim());
                    //
                    NavHostFragment.findNavController(SellOnBonAppetit2Fragment.this)
                            .navigate(R.id.action_sellOnBonAppetit2Fragment_to_sellOnBonAppetit3Fragment);
                    //
                }catch(Exception e){
                    Toast.makeText(requireContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}