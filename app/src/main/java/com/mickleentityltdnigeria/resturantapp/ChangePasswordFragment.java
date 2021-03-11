package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.mickleentityltdnigeria.resturantapp.utils.PasswordValidator;
import com.mickleentityltdnigeria.resturantapp.utils.module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePasswordFragment extends Fragment {

    EditText txtCurrentPassword, txtNewPassword, txtConfirmPassword;
    Button btnSave;
    ProgressBar progress;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChangePasswordFragment.
     */
    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.txtConfirmPassword = view.findViewById(R.id.txtConfirmNewPassword);
        this.txtCurrentPassword = view.findViewById(R.id.txtCurrentPassword);
        this.txtNewPassword = view.findViewById(R.id.txtNewPassword);
        this.progress = view.findViewById(R.id.progressBarChangePassword);
        this.btnSave = view.findViewById(R.id.btnSaveNewPassword);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                try{
                    module.checkNetwork();
                    if(txtCurrentPassword.getText().toString().isEmpty()){
                        txtCurrentPassword.requestFocus();
                        throw new Exception("Enter your current password.");
                    }
                    if(txtNewPassword.getText().toString().isEmpty()){
                        txtNewPassword.requestFocus();
                        throw new Exception("Enter your new password.");
                    }
                    if(!PasswordValidator.ValidatePassword(txtNewPassword.getText().toString(),8,20)){
                        txtNewPassword.requestFocus();
                        throw new Exception("Enter a valid password. Password must contain between 8 to 20 characters made up of alphabets and numbers.");
                    }
                    if(txtConfirmPassword.getText().toString().isEmpty()){
                        txtConfirmPassword.requestFocus();
                        throw new Exception("Confirm password.");
                    }
                    if(!txtConfirmPassword.getText().toString().equals(txtNewPassword.getText().toString())){
                        txtConfirmPassword.requestFocus();
                        throw new Exception("Password did not match.");
                    }
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(module.userName)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        // do something when mail was sent successfully.
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(view.getContext(), "A password reset link has been sent to " + module.userName,
                                                Toast.LENGTH_SHORT).show();
                                        //
                                        NavHostFragment.findNavController(ChangePasswordFragment.this)
                                                .navigate(R.id.action_ChangePasswordFragment_to_LoginFragment);
                                    } else {
                                        // ...
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(view.getContext(), "Password reset failed. Provide a valid Email address.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }catch (Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(view.getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}