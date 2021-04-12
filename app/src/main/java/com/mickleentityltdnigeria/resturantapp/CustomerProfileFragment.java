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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.mickleentityltdnigeria.resturantapp.dalc.CountryDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.UserDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Country;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerProfileFragment extends Fragment {

    EditText txtProfileEmail, txtProfileFirstName, txtProfileMiddleName, txtProfileLastName;
    EditText txtProfileAddress, txtProfileCity
            , txtProfileZipCode, txtProfileState, txtProfileIDD, txtProfilePhone;
    Spinner spinnerProfileCountry;
    ProgressBar progress;
    Button btnSaveProfile;
    UserDalc userData;
    User user = new User();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CustomerProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerProfileFragment.
     */
    public static CustomerProfileFragment newInstance(String param1, String param2) {
        CustomerProfileFragment fragment = new CustomerProfileFragment();
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
        return inflater.inflate(R.layout.fragment_customer_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.txtProfileAddress = view.findViewById(R.id.txtProfileAddress);
        this.txtProfileCity = view.findViewById(R.id.txtProfileCity);
        this.txtProfileEmail = view.findViewById(R.id.txtProfileEmail);
        this.txtProfileFirstName = view.findViewById(R.id.txtProfileFirstName);
        this.txtProfileIDD = view.findViewById(R.id.txtProfileIDD);
        this.txtProfileLastName = view.findViewById(R.id.txtProfileLastName);
        this.txtProfileMiddleName = view.findViewById(R.id.txtProfileMiddleName);
        this.txtProfilePhone = view.findViewById(R.id.txtProfilePhone);
        this.txtProfileState = view.findViewById(R.id.txtProfileState);
        this.txtProfileZipCode = view.findViewById(R.id.txtProfileZipCode);
        this.btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
        this.spinnerProfileCountry = view.findViewById(R.id.spinnerProfileCountry);
        this.progress = view.findViewById(R.id.progressBarProfile);
        progress.setVisibility(View.VISIBLE);
        this.txtProfileIDD.setKeyListener(null);
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, CountryDalc.getCountryNamesList(module.myCountries));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProfileCountry.setAdapter(adapter);
        spinnerProfileCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Country d = module.myCountries.get(position);
                //Get selected value of key
                String value = d.getCountryName();
                String key = d.getCountryName();
                txtProfileIDD.setText(("+" + d.getDialCode()));
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        //
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                try{
                    //
                    module.checkNetwork();
                    //
                    if(txtProfileAddress.getText().toString().isEmpty() || txtProfileCity.getText().toString().isEmpty() || txtProfileZipCode.getText().toString().isEmpty() ||
                            spinnerProfileCountry.getSelectedItem().toString().isEmpty() || txtProfileIDD.getText().toString().isEmpty() || txtProfilePhone.getText().toString().isEmpty()){
                        txtProfileAddress.requestFocus();
                        throw new Exception("All fields are required.");
                    }
                    user.setContactAddress(txtProfileAddress.getText().toString());
                    user.setMobilePhone((txtProfileIDD.getText().toString().trim() + txtProfilePhone.getText().toString().trim()));
                    user.setCity(txtProfileCity.getText().toString());
                    user.setCountry(spinnerProfileCountry.getSelectedItem().toString());
                    user.setZipCode(txtProfileZipCode.getText().toString());
                    user.setState(txtProfileState.getText().toString());
                    //
                    userData.UpdateUser(user);
                    module.userData = user;
                    progress.setVisibility(View.GONE);
                    Toast.makeText(requireContext(),"Profile update was successful.",Toast.LENGTH_LONG).show();
                    //
                    if(module.userType.equals(module.UserTypeCUSTOMER) ){
                        Navigation.findNavController(requireView())
                                .navigate(R.id.action_CustomerProfileFragment_to_FirstFragment);
                    }else if(module.userType.equals(module.UserTypeSELLER)){

                    } else if(module.userType.equals(module.UserTypeSUPPER)){

                    }
                    //
                }catch (Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                progress.setVisibility(View.GONE);
            }
        });
        setGetUserData();
    }


    private void setGetUserData(){
        userData = new UserDalc();
        //Try to fetch user data.
        UserUpdatedHandler userNotFound = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                progress.setVisibility(View.GONE);
                //
                txtProfileAddress.setText("");
                txtProfileCity.setText("");
                txtProfileEmail.setText("");
                txtProfileFirstName.setText("");
                txtProfileIDD.setText("");
                txtProfileLastName.setText("");
                txtProfileMiddleName.setText("");
                txtProfilePhone.setText("");
                txtProfileState.setText("");
                txtProfileZipCode.setText("");
                spinnerProfileCountry.setSelection(0);
                btnSaveProfile.setEnabled(false);
                Toast.makeText(requireContext(), "Profile not found or network problem, make sure you have an internet connection and try again.", Toast.LENGTH_LONG).show();
            }
        };
        //
        UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                try {
                    User u = users.get(0);
                    user = u;
                    Country country = new Country();
                    int x = 0;
                    for (int i = 0; i < module.myCountries.size(); i++) {
                        Country c = module.myCountries.get(i);
                        if(c.getCountryName().equals(u.getCountry().toUpperCase())){
                            country = c;
                            x = i;
                            break;
                        }
                    }
                    //
                    txtProfileAddress.setText(u.getContactAddress());
                    txtProfileCity.setText(u.getCity());
                    txtProfileEmail.setText(u.geteMail());
                    txtProfileFirstName.setText(u.getFirstName());
                    txtProfileIDD.setText(country.getDialCode());
                    txtProfileLastName.setText(u.getLastName());
                    txtProfileMiddleName.setText(u.getMiddleName());
                    txtProfilePhone.setText(u.getMobilePhone().substring((country.dialCode.length()+1)));
                    txtProfileState.setText(u.getState());
                    txtProfileZipCode.setText(u.getZipCode());
                    spinnerProfileCountry.setSelection(x);
                    btnSaveProfile.setEnabled(true);
                    //
                    userData.newUserAdded.removeListener("ProfileUserNotFound");
                    userData.userDataFetched.removeListener("ProfileUserDataFetched");
                    //
                } catch (Exception e) {
                    Toast.makeText(requireContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
                progress.setVisibility( View.GONE);
            }
        };
        //
        userData.newUserAdded.addListener("ProfileUserNotFound", userNotFound);
        userData.userDataFetched.addListener("ProfileUserDataFetched", userDataFetched);
        userData.getUserByName(module.userName);
    }
}