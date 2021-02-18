package com.mickleentityltdnigeria.resturantapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.service.Service;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import static com.mickleentityltdnigeria.resturantapp.utils.PasswordValidator.ValidatePassword;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterUserFragment extends Fragment {


    EditText txtPassword, txtFirstName, txtMiddleName, txtLastName, txtEmail, txtPhone, txtConfirm, txtAddress, txtCity, txtZipCode, txtState, txtCountry;
    ProgressBar progress;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public RegisterUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterUserFragment newInstance(String param1, String param2) {
        RegisterUserFragment fragment = new RegisterUserFragment();
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
        return inflater.inflate(R.layout.fragment_register_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.progressBarRegister);
        this.progress.setVisibility( View.VISIBLE);
        txtPassword = view.findViewById(R.id.txtCustomerPassword);
        txtFirstName = view.findViewById(R.id.txtCustomerFirstName);
        txtMiddleName = view.findViewById(R.id.txtCustomerMiddleName);
        txtLastName = view.findViewById(R.id.txtCustomerLastName);
        txtEmail = view.findViewById(R.id.txtCustomerEmail);
        txtPhone = view.findViewById(R.id.txtCustomerPhone);
        txtConfirm = view.findViewById(R.id.txtConfirmCustomerPassword);
        txtAddress = view.findViewById(R.id.txtCustomerAddress);
        txtCity = view.findViewById(R.id.txtCustomerCity);
        txtZipCode = view.findViewById(R.id.txtCustomerZipCode);
        txtState = view.findViewById(R.id.txtCustomerState);
        txtCountry = view.findViewById(R.id.txtCustomerCountry);
        view.findViewById(R.id.btnRegisterCustomerUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                progress.setVisibility( View.VISIBLE);
               try {
                   if (!ValidatePassword(txtPassword.getText().toString(), 8, 20))
                   {
                       txtPassword.requestFocus();
                      throw new Exception("Password must be between 8 to 20 characters and have at least one Capital Letter and one special character.");
                   }
                   if(!txtPassword.getText().toString().equals(txtConfirm.getText().toString())){
                       txtConfirm.requestFocus();
                       throw new Exception("Password did not match.");
                   }
                   if(txtEmail.getText().toString() == ""){
                       txtEmail.requestFocus();
                       throw new Exception("Fill all required fields.");
                   }
                   if(txtZipCode.getText().toString() == ""){
                       txtZipCode.requestFocus();
                       throw new Exception("Fill all required fields.");
                   }
                   String deviceID = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                   User user = new User("",txtEmail.getText().toString(),txtPassword.getText().toString(),
                           txtFirstName.getText().toString(),txtMiddleName.getText().toString(),txtLastName.getText().toString(),
                           txtEmail.getText().toString(),txtPhone.getText().toString(),txtAddress.getText().toString(), txtCity.getText().toString(),
                           txtZipCode.getText().toString(),txtState.getText().toString(),txtCountry.getText().toString(),deviceID, module.UserTypeCUSTOMER);
                   //Save new User to the system.
                   Service.user().User.AddUser(user);
                   //
                   Snackbar.make(view , "Sign Up successful. \n You can now login.", Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
                   Navigation.findNavController(view)
                           .navigate(R.id.action_registerUserFragment_to_LoginFragment);
               } catch (Exception e) {
                   Snackbar.make(view , e.getMessage(), Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
               }
                progress.setVisibility( View.GONE);
            }
        });
        txtEmail.requestFocus();
        this.progress.setVisibility( View.GONE);
    }
}