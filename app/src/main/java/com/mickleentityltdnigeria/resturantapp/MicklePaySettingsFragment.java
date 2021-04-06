package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.ApplicationDataDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.CountryDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.ApplicationData;
import com.mickleentityltdnigeria.resturantapp.data.model.Country;
import com.mickleentityltdnigeria.resturantapp.extensions.ApplicationDataEvenHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MicklePaySettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MicklePaySettingsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MicklePaySettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MicklePaySettingsFragment.
     */

    public static MicklePaySettingsFragment newInstance(String param1, String param2) {
        MicklePaySettingsFragment fragment = new MicklePaySettingsFragment();
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
        return inflater.inflate(R.layout.fragment_mickle_pay_settings, container, false);
    }

    boolean isAddNew = false;
    ApplicationData applicationData;
    ApplicationDataDalc applicationDataDalc;
    ProgressBar progress;
    EditText txtCompany, txtWalletID, txtAddress, txtCity, txtZipCode, txtState, txtContact, txtPhone, txtIDD, txtEmail;
    Spinner spinnerCountry;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try{
            this.applicationData = new ApplicationData();
            this.applicationDataDalc = new ApplicationDataDalc();
            this.progress = view.findViewById(R.id.progressBarSettings);
            txtCompany  = view.findViewById(R.id.txtCompanyName) ;
            txtWalletID  = view.findViewById(R.id.txtSettingsMicklePayWalletID);
            txtAddress  = view.findViewById(R.id.txtSettingsAddress);
            txtCity  = view.findViewById(R.id.txtSettingsCity);
            txtZipCode  = view.findViewById(R.id.txtSettingsZipCode);
            txtState  = view.findViewById(R.id.txtSettingsState);
            txtContact  = view.findViewById(R.id.txtSettingsContact);
            txtPhone  = view.findViewById(R.id.txtSettingsPhone);
            txtIDD  = view.findViewById(R.id.txtSettingsIDD);
            txtEmail = view.findViewById(R.id.txtSettingsEmail);
            spinnerCountry  = view.findViewById(R.id.spinnerSettingsCountry);
            //
            progress.setVisibility(View.VISIBLE);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, CountryDalc.getCountryNamesList(module.myCountries));
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
                    applicationData.setCompany_CurrencyCode(d.getCurrencyCode());
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            //
            ApplicationDataEvenHandler onDataFetched = new ApplicationDataEvenHandler() {
                @Override
                public void invoke(List<ApplicationData> applicationDataList) {
                    progress.setVisibility(View.GONE);
                    applicationData = applicationDataList.get(0);
                    isAddNew = false;
                    Country country = new Country();
                    int x = 0;
                    for (int i = 0; i < module.myCountries.size(); i++) {
                        Country c = module.myCountries.get(i);
                        if(c.getCountryName().equals(applicationData.getCompany_Country())){
                            country = c;
                            x = i;
                            break;
                        }
                    }
                    txtCompany.setText(applicationData.getCompany_Name());
                    txtWalletID.setText(applicationData.getMICKLE_PAY_WALLET_ID());
                    txtAddress.setText(applicationData.getCompany_Address());
                    txtCity.setText(applicationData.getCompany_City());
                    txtZipCode.setText(applicationData.getCompany_ZipCode());
                    txtState.setText(applicationData.getCompany_State());
                    txtContact.setText(applicationData.getContact_Person());
                    txtPhone.setText(applicationData.getContact_Phone().substring((country.dialCode.length()+1)));
                    txtIDD.setText(country.getDialCode());
                    txtEmail.setText(applicationData.getContact_Email());
                    spinnerCountry.setSelection(x);
                }
            };
            this.applicationDataDalc.onDataFetchedHandler.addListener(onDataFetched);
            ApplicationDataEvenHandler onDataNotFound = new ApplicationDataEvenHandler() {
                @Override
                public void invoke(List<ApplicationData> applicationDataList) {
                    progress.setVisibility(View.GONE);
                    applicationData = new ApplicationData();
                    isAddNew = true;
                }
            };
            this.applicationDataDalc.onDataNotFoundHandler.addListener(onDataNotFound);
            ApplicationDataEvenHandler onDataAdded = new ApplicationDataEvenHandler() {
                @Override
                public void invoke(List<ApplicationData> applicationDataList) {
                    progress.setVisibility(View.GONE);
                    applicationData = applicationDataList.get(0);
                    isAddNew = false;
                }
            };
            this.applicationDataDalc.onDataAddedHandler.addListener(onDataAdded);
            ApplicationDataEvenHandler onDataUpdated = new ApplicationDataEvenHandler() {
                @Override
                public void invoke(List<ApplicationData> applicationDataList) {
                    progress.setVisibility(View.GONE);
                    applicationData = applicationDataList.get(0);
                    isAddNew = false;
                }
            };
            this.applicationDataDalc.onDataUpdatedHandler.addListener(onDataUpdated);
            //
            view.findViewById(R.id.btnSettingsSubmit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progress.setVisibility(View.VISIBLE);
                    try{
                        module.checkNetwork();
                        if(txtCompany.getText().toString().isEmpty()){
                            txtCompany.setError("Company name is required.");
                            throw new Exception("Company name is required.");
                        }
                        if(txtWalletID.getText().toString().isEmpty()){
                            txtWalletID.setError("MICKLE-PAY WALLET ID is required.");
                            throw new Exception("MICKLE-PAY WALLET ID is required.");
                        }
                        //
                        applicationData.setCompany_Address(txtAddress.getText().toString().trim());
                        applicationData.setCompany_City(txtCity.getText().toString().trim());
                        applicationData.setCompany_Country(spinnerCountry.getSelectedItem().toString());
                        applicationData.setCompany_Name(txtCompany.getText().toString().trim());
                        applicationData.setCompany_State(txtState.getText().toString().trim());
                        applicationData.setCompany_ZipCode(txtZipCode.getText().toString().trim());
                        applicationData.setContact_Email(txtEmail.getText().toString().trim());
                        applicationData.setContact_Person(txtContact.getText().toString().trim());
                        applicationData.setContact_Phone((txtIDD.getText().toString().trim() + txtPhone.getText().toString().trim()));
                        applicationData.setMICKLE_PAY_WALLET_ID(txtWalletID.getText().toString().trim());
                        //
                        if(isAddNew){
                            applicationDataDalc.addApplicationData(applicationData);
                        }else{
                            applicationDataDalc.updateApplicationData(applicationData);
                        }
                        Toast.makeText(requireContext(), "Data Updated Successfully.", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        progress.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch (Exception e){
            progress.setVisibility(View.GONE);
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}