package com.mickleentityltdnigeria.resturantapp;

import android.app.Activity;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mickleentityltdnigeria.resturantapp.dalc.UserDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import static android.content.ContentValues.TAG;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    FirebaseAuth mAuth;
    UserDalc userData;
    Button btnLogin, btnRegister;
    EditText txtUserName, txtPassword;
    ProgressBar progress;
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
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userData = new UserDalc();
        progress = view.findViewById(R.id.loading);
        txtUserName = view.findViewById(R.id.username);
        txtPassword = view.findViewById(R.id.password);
        btnLogin = view.findViewById(R.id.login);
        btnLogin.setEnabled(true);
        btnRegister = view.findViewById(R.id.btnLoginRegister);
        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                progress.setVisibility(View.VISIBLE);
                try {
                    module.checkNetwork();
                    // Register interest in the user.
                    UserUpdatedHandler userNotFound = new UserUpdatedHandler() {
                        public void invoke(List<User> users) {
                            progress.setVisibility(View.GONE);
                            Snackbar.make(view, "Invalid username and password.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            txtUserName.requestFocus();
                        }
                    };
                    UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
                        public void invoke(List<User> users) {
                            User u = users.get(0);
                            progress.setVisibility(View.GONE);
                            module.userID = u.getUserID().toString();
                            module.userName = u.getUserName().toString();
                            module.userType = u.getUserType().toString();
                            module.isLoggedIn = true;

                            Snackbar.make(view, "Login successful.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            //
                            userData.newUserAdded.removeListener("LoginuserNotFound");
                            userData.userDataFetched.removeListener("LoginuserDataFetched");
                            //
                            NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                            NavController navController = navHostFragment.getNavController();
                            navController.navigate(R.id.action_LoginFragment_to_FirstFragment);
                           //
                        }
                    };
                    //
                    userData.newUserAdded.addListener("LoginuserNotFound", userNotFound);
                    userData.userDataFetched.addListener("LoginuserDataFetched", userDataFetched);
                    //
                    mAuth.signInWithEmailAndPassword(txtUserName.getText().toString().trim(), txtPassword.getText().toString().trim())
                            .addOnCompleteListener((Activity) view.getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //Try to save new user data.
                                        userData.getUserByName(txtUserName.getText().toString().trim());
                                        //
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        progress.setVisibility(View.GONE);
                                        //
                                        userData.newUserAdded.removeListener("LoginuserNotFound");
                                        userData.userDataFetched.removeListener("LoginuserDataFetched");
                                        //
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(view.getContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
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
                    userData.newUserAdded.removeListener("LoginuserNotFound");
                    userData.userDataFetched.removeListener("LoginuserDataFetched");
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
                //
                NavHostFragment navHostFragment = (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                NavController navController = navHostFragment.getNavController();
                navController.navigate(R.id.action_LoginFragment_to_registerUserFragment);
                //
            }
        });
    }

    private void updateUI(FirebaseUser user) {
    }
}