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
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordResetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordResetFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PasswordResetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PasswordResetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PasswordResetFragment newInstance(String param1, String param2) {
        PasswordResetFragment fragment = new PasswordResetFragment();
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
        return inflater.inflate(R.layout.fragment_password_reset, container, false);
    }

    EditText txtEmail;
    Button btnResetPassword;
    ProgressBar progress;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtEmail = view.findViewById(R.id.txtEmailPasswordReset);
        btnResetPassword = view.findViewById(R.id.btnResetPassword);
        progress = view.findViewById(R.id.resetPasswordProgress);
        view.findViewById(R.id.btnResetPassword).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                try{
                    module.checkNetwork();
                    if(txtEmail.getText().toString().isEmpty()){
                        txtEmail.requestFocus();
                        throw new Exception("Provide a valid Email address, the exact one registered with us.");
                    }
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    auth.sendPasswordResetEmail(txtEmail.getText().toString().trim())
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if (task.isSuccessful()) {
                                        // do something when mail was sent successfully.
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(view.getContext(), "A password reset link has been sent to " + txtEmail.getText().toString(),
                                                Toast.LENGTH_SHORT).show();
                                        //
                                        NavHostFragment.findNavController(PasswordResetFragment.this)
                                                .navigate(R.id.action_passwordResetFragment_to_LoginFragment);
                                    } else {
                                        // ...
                                        progress.setVisibility(View.GONE);
                                        Toast.makeText(view.getContext(), "Password reset failed. Provide a valid Email address.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }catch (Exception e){
                    //
                    progress.setVisibility(View.GONE);
                    Toast.makeText(view.getContext(), Objects.requireNonNull(e.getMessage()),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}