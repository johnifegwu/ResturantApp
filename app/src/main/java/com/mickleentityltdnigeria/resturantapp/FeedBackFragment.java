package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.FeedBackDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FeedBack;
import com.mickleentityltdnigeria.resturantapp.extensions.FeedBackEventHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedBackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedBackFragment extends Fragment {

    EditText txtFullName, txtEmail, txtSubject, txtMsgBody;
    AppCompatSpinner spinnerFeedBackTypes;
    Button btnSendFeedBack;
    ProgressBar progress;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public FeedBackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedBackFragment.
     */

    public static FeedBackFragment newInstance(String param1, String param2) {
        FeedBackFragment fragment = new FeedBackFragment();
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
        return inflater.inflate(R.layout.fragment_feed_back, container, false);
    }

    FeedBackDalc feedBackDalc;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.progressBarFeedBack);
        btnSendFeedBack = view.findViewById(R.id.btnSendFeedBack);
        txtEmail = view.findViewById(R.id.txtSendFeedBackEmail);
        txtFullName = view.findViewById(R.id.txtSendFeedBackFullName);
        txtSubject = view.findViewById(R.id.txtSendFeedBackSubject);
        txtMsgBody = view.findViewById(R.id.txtSendFeedBackMsgBody);
        spinnerFeedBackTypes = view.findViewById(R.id.spinnerFeedBackTypes);
        //
        feedBackDalc = new FeedBackDalc();
        //
        FeedBackEventHandler newFeedBackAdd = new FeedBackEventHandler() {
            @Override
            public void invoke(List<FeedBack> feedBackList) {
                progress.setVisibility(View.GONE);
                Toast.makeText(requireContext(),"Your feedback has been sent successfully.", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(FeedBackFragment.this)
                        .navigate(R.id.action_feedBackFragment_to_FirstFragment);
            }
        };
        feedBackDalc.newFeedBackAdded.addListener(newFeedBackAdd);
        //
        txtFullName.setText((module.userData.getFirstName() + " " + module.userData.getLastName()));
        txtEmail.setText(module.userName);
        //
        btnSendFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                try {
                    module.checkNetwork();
                    if(txtMsgBody.getText().toString().isEmpty()){
                        throw new Exception("Sending an empty feedback is not allowed.");
                    }
                    if(txtEmail.getText().toString().isEmpty() || txtFullName.getText().toString().isEmpty() || txtSubject.getText().toString().isEmpty()){
                        throw new Exception("All fields are required.");
                    }
                    FeedBack feedBack = new FeedBack();
                    feedBack.setUserFullName(txtFullName.getText().toString());
                    feedBack.setUserEmail(txtEmail.getText().toString().trim());
                    feedBack.setFeedBackType(spinnerFeedBackTypes.getSelectedItem().toString());
                    feedBack.setMsgSubject(txtSubject.getText().toString());
                    feedBack.setMsgDate(new Date());
                    feedBack.setMsgBody(txtMsgBody.getText().toString());
                    feedBackDalc.addFeedBack(feedBack);
                }catch (Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}