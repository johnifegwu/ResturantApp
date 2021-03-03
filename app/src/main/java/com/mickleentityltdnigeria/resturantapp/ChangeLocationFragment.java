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
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.CountryDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Address;
import com.mickleentityltdnigeria.resturantapp.data.model.Country;
import com.mickleentityltdnigeria.resturantapp.data.model.CurrentLocation;
import com.mickleentityltdnigeria.resturantapp.extensions.AddressChangedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.CurrentLocationChangedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangeLocationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeLocationFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ChangeLocationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangeLocationFragment.
     */

    public static ChangeLocationFragment newInstance(String param1, String param2) {
        ChangeLocationFragment fragment = new ChangeLocationFragment();
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
        return inflater.inflate(R.layout.fragment_change_location, container, false);
    }

    Spinner spinnerCountry;
    EditText txtCity, txtZipCode, txtState;
    Button btnSave;
    ProgressBar progress;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtState = view.findViewById(R.id.txtNewLocationState);
        txtCity = view.findViewById(R.id.txtNewLocationCity);
        txtZipCode = view.findViewById(R.id.txtNewLocationZipCode);
        progress = view.findViewById(R.id.progressBarNewLocation);
        spinnerCountry = view.findViewById(R.id.spinnerNewLocationCountry);
        //
        btnSave =  view.findViewById(R.id.btnSaveNewShippingAddress);
        //
        try {
            progress.setVisibility(View.VISIBLE);
            //
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, CountryDalc.getCountryNamesList(module.myCountries));
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCountry.setAdapter(adapter);
            spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    Country d = module.myCountries.get(position);
                    //Get selected value of key
                    String value = d.getCountryName().toString();
                    String key = d.getCountryName().toString();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }catch (Exception e){
            progress.setVisibility(View.GONE);
            Toast.makeText(view.getContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
        view.findViewById(R.id.btnSaveNewShippingAddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setVisibility(View.VISIBLE);
                try {
                    //
                    module.checkNetwork();
                    if(!spinnerCountry.isSelected() || txtCity.getText().toString().isEmpty() || txtZipCode.getText().toString().isEmpty()){
                        spinnerCountry.requestFocus();
                        throw new Exception("Country, City and ZipCode are required fields.");
                    }
                    //
                    CurrentLocationChangedHandler locationUpdated = new CurrentLocationChangedHandler() {
                        public void invoke(List<CurrentLocation> location) {
                            //
                            Toast.makeText(view.getContext(), "Shipping successfully.", Toast.LENGTH_LONG).show();
                            //
                            Navigation.findNavController(view)
                                    .navigate(R.id.action_changeLocationFragment_to_FirstFragment);
                        }
                    };
                    //
                    CurrentLocation cl = new CurrentLocation( module.locationID,module.userID,spinnerCountry.getSelectedItem().toString(),txtState.getText().toString(),txtCity.getText().toString(),txtZipCode.getText().toString());
                    module.MyCurrentLocation.locationsUpdated.addListener(locationUpdated);
                    module.MyCurrentLocation.UpdateCurrentLocation(cl);
                    //
                }catch (Exception e){
                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                progress.setVisibility(View.GONE);
            }
        });
        //
    }
}