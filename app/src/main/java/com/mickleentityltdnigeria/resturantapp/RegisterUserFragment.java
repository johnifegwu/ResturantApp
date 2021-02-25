package com.mickleentityltdnigeria.resturantapp;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mickleentityltdnigeria.resturantapp.dalc.UserDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;
import static com.mickleentityltdnigeria.resturantapp.utils.PasswordValidator.ValidatePassword;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterUserFragment extends Fragment {


    private FirebaseAuth mAuth;
    UserDalc userData;
    EditText txtPassword, txtFirstName, txtMiddleName, txtLastName, txtEmail, txtPhone, txtConfirm, txtAddress, txtCity, txtZipCode, txtState, txtCountry;
    ProgressBar progress;
    /*//
    NavHostFragment navHostFragment;
    //
    NavController navController;
*/
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
    public static RegisterUserFragment newInstance(String param1, String param2) {
        RegisterUserFragment fragment = new RegisterUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        FirebaseApp.initializeApp(requireContext());
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
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
        userData = new UserDalc();
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
        /*//
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        //
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        //*/
        view.findViewById(R.id.btnRegisterCustomerUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                progress.setVisibility( View.VISIBLE);
               try {
                   module.checkNetwork();
                   if (!ValidatePassword(txtPassword.getText().toString(), 8, 20))
                   {
                       txtPassword.requestFocus();
                       throw new Exception("Password must be between 8 to 20 characters and have at least one Capital Letter and one numeric character.");
                   }
                   if(!txtPassword.getText().toString().trim().equals(txtConfirm.getText().toString().trim())){
                       txtConfirm.requestFocus();
                       throw new Exception("Password did not match.");
                   }
                   if(TextUtils.isEmpty(txtEmail.getText().toString())){
                       txtEmail.requestFocus();
                       throw new Exception("Fill all required fields.");
                   }
                   if(TextUtils.isEmpty(txtZipCode.getText().toString())){
                       txtZipCode.requestFocus();
                       throw new Exception("Fill all required fields.");
                   }
                   String deviceID = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                   deviceID = ImageHelper.getInstant().byteArrayToString(deviceID.getBytes());
                   User user = new User("",txtEmail.getText().toString().trim(),"xxxxxxxx",
                           txtFirstName.getText().toString().trim(),txtMiddleName.getText().toString().trim(),txtLastName.getText().toString().trim(),
                           txtEmail.getText().toString().trim(),txtPhone.getText().toString().trim(),txtAddress.getText().toString().trim(), txtCity.getText().toString().trim(),
                           txtZipCode.getText().toString().trim(),txtState.getText().toString().trim(),txtCountry.getText().toString().trim(),deviceID.trim(), module.UserTypeCUSTOMER);
                   //Try to create new user login credentials first and Save the new User to the system.
                   mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString().trim(), txtPassword.getText().toString())
                           .addOnFailureListener(((MainActivity) requireActivity()), new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   //
                                   Snackbar.make(view, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG)
                                           .setAction("Action", null).show();
                               }
                           })
                           .addOnCompleteListener(((MainActivity) requireActivity()), new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       //
                                       Toast.makeText(view.getContext(), "First level authentication successful.", Toast.LENGTH_SHORT).show();
                                       //Try to save new user data.
                                       userData.AddUser(user);
                                       //
                                       // Sign in success, update UI with the signed-in user's information
                                      Log.d(TAG, "createUserWithEmail:success");
                                       FirebaseUser user = mAuth.getCurrentUser();
                                       updateUI(user);
                                   } else {
                                       // If sign in fails, display a message to the user.
                                       Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                       Toast.makeText(view.getContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                       updateUI(null);
                                       //
                                       userData.newUserAdded.removeListener("RegnewUserAdded");
                                       userData.duplicateUserEvent.removeListener("RegduplicateUserEvent");
                                       progress.setVisibility( View.GONE);
                                       //
                                   }
                                   // ...
                               }
                           });
               } catch (Exception e) {
                   //
                   userData.newUserAdded.removeListener("RegnewUserAdded");
                   progress.setVisibility( View.GONE);
                   //
                   Snackbar.make(view , Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG)
                           .setAction("Action", null).show();
               }
            }
        });
        UserUpdatedHandler newUserAdded = new UserUpdatedHandler() {
            public void invoke(List<User> users) {
                progress.setVisibility( View.GONE);
                User u = users.get(0);
                module.userID = u.getUserID().toString();
                module.userName = u.getUserName().toString();
                module.userType = u.getUserType().toString();
                module.zipCode = u.getZipCode().toString();
                module.country = u.getCountry().toString();
                module.state = u.getState().toString();
                module.isLoggedIn = true;
                //
                userData.newUserAdded.removeListener("RegnewUserAdded");
                //
                NavHostFragment.findNavController(RegisterUserFragment.this)
                        .navigate(R.id.action_registerUserFragment_to_FirstFragment);
            }
        };
        //
        userData.newUserAdded.addListener("RegnewUserAdded",newUserAdded);
        //
        txtEmail.requestFocus();
        this.progress.setVisibility( View.GONE);
    }


    private void updateUI(@Nullable FirebaseUser user) {
    }


}