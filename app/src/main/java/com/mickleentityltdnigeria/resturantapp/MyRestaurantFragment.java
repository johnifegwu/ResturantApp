package com.mickleentityltdnigeria.resturantapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Country;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRestaurantFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MyRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyRestaurantFragment.
     */
    public static MyRestaurantFragment newInstance(String param1, String param2) {
        MyRestaurantFragment fragment = new MyRestaurantFragment();
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
        return inflater.inflate(R.layout.fragment_my_restaurant, container, false);
    }

    ResturantDalc restaurantDalc;
    ImageView imgMyRestaurant;
    Button btnSave;
    EditText txtName, txtMoneyBack, txtCurrency, txtContact, txtEmail, txtMicklePay, txtCountry, txtIDD, txtPhone, txtaddress, txtCity, txtZipCode, txtState, txtZipCodes, txtWeb, txtDescription;
    Spinner spinnerTypes;
    Resturant resturant;
    ProgressBar progress;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restaurantDalc = new ResturantDalc();
        this.imgMyRestaurant = view.findViewById(R.id.imgMyRestaurant);
        this.btnSave = view.findViewById(R.id.btnSaveMyRestaurant);
        this.txtMoneyBack = view.findViewById(R.id.txtMoneyBackGuarantee);
        this.txtContact = view.findViewById(R.id.txtMyRestauranContact);
        this.txtaddress = view.findViewById(R.id.txtMyRestaurantAddress);
        this.txtCity = view.findViewById(R.id.txtMyRestaurantCity);
        this.txtCountry = view.findViewById(R.id.txtMyRestaurantCountry);
        this.txtDescription = view.findViewById(R.id.txtMyRestaurantDesc);
        this.txtEmail = view.findViewById(R.id.txtMyRestaurantEmail);
        this.txtMicklePay = view.findViewById(R.id.txtMicklePayWalletID);
        this.txtIDD = view.findViewById(R.id.txtMyRestaurantIDD);
        this.txtName = view.findViewById(R.id.txtMyRestauranName);
        this.txtPhone = view.findViewById(R.id.txtMyRestaurantPhone);
        this.txtState = view.findViewById(R.id.txtMyRestaurantState);
        this.txtWeb = view.findViewById(R.id.txtMyRestaurantWebsite);
        this.txtZipCode = view.findViewById(R.id.txtMyRestaurantZipCode);
        this.txtZipCodes = view.findViewById(R.id.txtMyRestaurantZipCodes);
        this.txtCurrency = view.findViewById(R.id.txtMyRestaurantCurrency);
        this.spinnerTypes = view.findViewById(R.id.spinnerMyRestaurantTypes);
        this.progress = view.findViewById(R.id.progressBarMyRestaurant);
        btnSave.setEnabled(false);
        txtCurrency.setKeyListener(null);
        txtIDD.setKeyListener(null);
        txtCountry.setKeyListener(null);
        //
        ResturantUpdatedHandler resturantFetched = new ResturantUpdatedHandler() {
            @Override
            public void invoke(List<Resturant> Resturant) {
                progress.setVisibility(View.GONE);
                resturant = Resturant.get(0);
                imgMyRestaurant.setImageDrawable(ImageHelper.getInstance().imageFromString(resturant.resturantImg));
                txtName.setText(resturant.getResturantName());
                txtMoneyBack.setText(resturant.getMoneyBackGuaranteeInDays());
                txtContact.setText(resturant.getContactPerson());
                txtEmail.setText(resturant.getEmail());
                txtMicklePay.setText(resturant.getMicklePayWalletID());
                txtCountry.setText(resturant.getCountry());
                Country country = new Country();
                for (int i = 0; i < module.myCountries.size(); i++) {
                    Country c = module.myCountries.get(i);
                    if (c.getCountryName().equals(resturant.getCountry().toUpperCase())) {
                        country = c;
                        break;
                    }
                }
                //
                txtIDD.setText(("+" + country.getDialCode()));
                txtPhone.setText(resturant.getPhone().substring((country.dialCode.length() + 1)));
                txtaddress.setText(resturant.getAddress());
                txtCity.setText(resturant.getCity());
                txtZipCode.setText(resturant.getZipCode());
                txtState.setText(resturant.getState());
                txtZipCodes.setText(resturant.getZipCodesX());
                txtWeb.setText(resturant.getWebsiteUrl());
                txtDescription.setText(resturant.getResturantDescription());
                txtCurrency.setText(resturant.getCurrencyCode());
                int i = 0;
                while (i < spinnerTypes.getAdapter().getCount()) {
                    if (spinnerTypes.getItemAtPosition(i).toString().equals(resturant.getResturantType())) {
                        spinnerTypes.setSelection(i);
                        break;
                    }
                    i++;
                }
                btnSave.setEnabled(true);
            }
        };
        restaurantDalc.resturantDataFetched.addListener(resturantFetched);
        //
        imgMyRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });
        //
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                try {
                    module.checkNetwork();
                    if(txtEmail.getText().toString().isEmpty() || txtZipCode.getText().toString().isEmpty() || txtCity.getText().toString().isEmpty() || txtIDD.getText().toString().isEmpty() || txtPhone.getText().toString().isEmpty()){
                        throw new Exception("Country, City, Zip Code, Phone number and Email are required fields.");
                    }
                    if(txtDescription.getText().toString().isEmpty()){
                        throw new Exception("Description is required");
                    }
                    if(txtMicklePay.getText().toString().isEmpty()){
                        throw new Exception("Mickle-Pay Wallet ID is required");
                    }
                    if(!txtZipCodes.getText().toString().isEmpty()){
                        String[] zip = txtZipCodes.getText().toString().split(" ");
                        if(zip[0] == ""){
                            throw new Exception("You must add at least one ZipCode");
                        }
                    }else {
                        throw new Exception("You must add at least one ZipCode");
                    }
                    if(txtMoneyBack.getText().toString().isEmpty() || Integer.parseInt(txtMoneyBack.getText().toString()) < 0){
                        throw new Exception("Money Back Guarantee cannot be less than zero.");
                    }
                    String zip = "";
                    String zipX = txtZipCodes.getText().toString();
                    if(zipX.contains("\n")){
                        String[] line = zipX.split("\n");
                        for(String l:line){
                            for(String s: l.split(" ")){
                                zip += (resturant.getCountry().trim() + "-" + s.trim() + " ");
                            }
                        }
                    }else {
                        for(String s: zipX.split(" ")){
                            zip += (resturant.getCountry().trim() + "-" + s.trim() + " ");
                        }
                    }
                    if(resturant.getResturantImg().isEmpty()){
                        throw new Exception("An image is required for this Restaurant.");
                    }
                    if(txtName.getText().toString().isEmpty() || txtContact.getText().toString().isEmpty()){
                        throw new Exception("Restaurant name and Contact Person are required fields.");
                    }
                    resturant.setResturantName(txtName.getText().toString().trim());
                    resturant.setMoneyBackGuaranteeInDays(Integer.parseInt(txtMoneyBack.getText().toString()));
                    resturant.setResturantType(spinnerTypes.getSelectedItem().toString());
                    resturant.setContactPerson(txtContact.getText().toString().trim());
                    resturant.setUserID(module.userID);
                    resturant.setZipCode(txtZipCode.getText().toString().trim());
                    resturant.setCity(txtCity.getText().toString().trim());
                    resturant.setState(txtState.getText().toString().trim());
                    resturant.setPhone((txtIDD.getText().toString().trim() + txtPhone.getText().toString().trim()));
                    resturant.setAddress(txtaddress.getText().toString());
                    resturant.setEmail(txtEmail.getText().toString().trim());
                    resturant.setMicklePayWalletID(txtMicklePay.getText().toString().trim());
                    //set ZipCodes for updating FoodItems.
                    resturant.setZipCodes(zip);
                    //ZipCodes preserved for future updates.
                    resturant.setZipCodesX(zipX);
                    resturant.setResturantDescription(txtDescription.getText().toString());
                    resturant.setWebsiteUrl(txtWeb.getText().toString().trim());
                    //Save data to the system
                    restaurantDalc.UpdateResturant(resturant);
                    //notify user
                    Toast.makeText(requireContext(),"Update was successful.",Toast.LENGTH_SHORT).show();
                    //
                } catch (Exception e) {
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                progress.setVisibility(View.GONE);
            }
        });
        //
        try{
            module.checkNetwork();
            progress.setVisibility(View.VISIBLE);
            restaurantDalc.getResturantByResturantID(module.userData.resturantID);
        } catch (Exception e) {
            progress.setVisibility(View.GONE);
            Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    try {
                        module.newRestaurantImg = ImageHelper.getInstance().decodeFile(uri);
                        resturant.setResturantImg(ImageHelper.getInstance().byteArrayToString(module.newRestaurantImg));
                        imgMyRestaurant.setImageDrawable(ImageHelper.getInstance().imageFromString(resturant.resturantImg));
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

}