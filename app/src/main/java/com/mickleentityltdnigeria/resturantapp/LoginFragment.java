package com.mickleentityltdnigeria.resturantapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mickleentityltdnigeria.resturantapp.dalc.UserDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.Event;
import com.mickleentityltdnigeria.resturantapp.extensions.LoginSuccessHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import static android.content.ContentValues.TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    FirebaseAuth mAuth;
    Button btnLogin, btnRegister;
    EditText txtUserName, txtPassword;
    ProgressBar progress;
    UserDalc userData;
    public Event<LoginSuccessHandler> loginSuccessHandlerEvent = new Event<LoginSuccessHandler>();
   /* //
    NavHostFragment navHostFragment;
    //
    NavController navController;
*/
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login2Fragment.
     */

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        if (currentUser != null) {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ((MainActivity) getActivity()).getCartTotal();
        return view;
    }*/

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.loading);
        txtUserName = view.findViewById(R.id.username);
        txtPassword = view.findViewById(R.id.password);
        btnLogin = view.findViewById(R.id.login);
        btnLogin.setEnabled(true);
        btnRegister = view.findViewById(R.id.btnLoginRegister);
        //
        setGetUserData();
        // Register interest in the user.
        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                progress.setVisibility(View.VISIBLE);
                try {
                    module.checkNetwork();
                   //
                   /* Toast.makeText(view.getContext(), "About to Login. with: " + txtUserName.getText().toString(),
                            Toast.LENGTH_SHORT).show();*/
                    module.userName = txtUserName.getText().toString();
                    mAuth.signInWithEmailAndPassword(txtUserName.getText().toString().trim(), txtPassword.getText().toString())
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
                                        progress.setVisibility(View.GONE);
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(view.getContext(), "Sign in successful.",
                                                Toast.LENGTH_SHORT).show();
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progress.setVisibility(View.GONE);
                                        //
                                        Toast.makeText(view.getContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        updateUI(null);
                                        // ...
                                    }
                                    // ...
                                }
                            });
                    //
                } catch (Exception e) {
                    progress.setVisibility(View.GONE);
                   //
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
        view.findViewById(R.id.btnLoginRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.GONE);
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_LoginFragment_to_registerUserFragment);
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null){
            progress.setVisibility(View.VISIBLE);
            userData.getUserByName(user.getEmail());
        }
    }

    private void setGetUserData(){
        userData = new UserDalc();
        //Try to fetch user data.
        UserUpdatedHandler userNotFound = new UserUpdatedHandler() {
                public void invoke(List<User> users) {
                    progress.setVisibility(View.GONE);
                    //
                    module.userID = "";
                    module.userName = "";
                    module.userType = "";
                    module.zipCode = "";
                    module.isLoggedIn = false;
                    Snackbar.make(requireView(), "Invalid username and password.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
        };
        //
        UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
                public void invoke(List<User> users) {
                    progress.setVisibility( View.GONE);
                    try {
                    User u = users.get(0);
                    module.userID = u.getUserID();
                    module.userName = u.getUserName();
                    module.userType = u.getUserType();
                    module.zipCode = u.getZipCode();
                    module.country = u.getCountry();
                    module.state = u.getState();
                    module.city = u.getCity();
                    module.isLoggedIn = true;
                    //
                    userData.newUserAdded.removeListener("LoginuserNotFound");
                    userData.userDataFetched.removeListener("LoginuserDataFetched");
                   /* NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_LoginFragment_to_FirstFragment);*/
                    Navigation.findNavController(requireView())
                            .navigate(R.id.action_LoginFragment_to_FirstFragment);
                        //
                    } catch (Exception e) {
                        Toast.makeText(requireContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            };
            //
            userData.newUserAdded.addListener("LoginuserNotFound", userNotFound);
            userData.userDataFetched.addListener("LoginuserDataFetched", userDataFetched);
    }
}