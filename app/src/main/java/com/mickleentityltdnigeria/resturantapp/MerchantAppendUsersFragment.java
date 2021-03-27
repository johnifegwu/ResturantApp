package com.mickleentityltdnigeria.resturantapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.dalc.UserDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FeedBack;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MerchantAppendUsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerchantAppendUsersFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public MerchantAppendUsersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppendUsersFragment.
     */
    public static MerchantAppendUsersFragment newInstance(String param1, String param2) {
        MerchantAppendUsersFragment fragment = new MerchantAppendUsersFragment();
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
        return inflater.inflate(R.layout.fragment_merchant_append_users, container, false);
    }

    EditText txtEmail;
    Button btnAppend;
    ProgressBar progress;
    UserDalc userDalc;
    List<User> users = new ArrayList<>();
    AppendedUserAdapter adapter;
    RecyclerView recyclerView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userDalc = new UserDalc();
        this.progress = view.findViewById(R.id.progressBarAppendUsers);
        this.btnAppend = view.findViewById(R.id.btnAppendUser);
        this.txtEmail = view.findViewById(R.id.txtMerchantUserEmail);
        //
        progress.setVisibility(View.VISIBLE);
        //

        //Reference of RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewAppendUsers);

        //Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL,false);

        //Set Layout Manager to RecyclerView
        recyclerView.setLayoutManager(linearLayoutManager);

        //Initialise adapter
        adapter = new AppendedUserAdapter(users, new AppendedUsersRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(User user) {

            }
        });

        //Set adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        UserUpdatedHandler userAppended = new UserUpdatedHandler() {
            @Override
            public void invoke(List<User> users) {
                //get already appended users.
                userDalc.getUsersByRestaurant(module.userData.resturantID);
            }
        };
        userDalc.newUserAppended.addListener(userAppended);
        UserUpdatedHandler usersFetched =  new UserUpdatedHandler() {
            @Override
            public void invoke(List<User> userList) {
                progress.setVisibility(View.GONE);
                txtEmail.setText("");
                users = userList;
                //Update Adapter here.
                adapter.updateData(users);
            }
        };
        userDalc.userDataFetched.addListener(usersFetched);
        UserUpdatedHandler userDataNotFound =  new UserUpdatedHandler() {
            @Override
            public void invoke(List<User> userList) {
                progress.setVisibility(View.GONE);
                txtEmail.setText("");
                users = new ArrayList<>();
                //Update Adapter here.
                adapter.updateData(users);
            }
        };
        userDalc.userNotFound.addListener(userDataNotFound);
        //
        btnAppend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    progress.setVisibility(View.VISIBLE);
                    module.checkNetwork();
                    if(module.userType.equals(module.UserTypeSELLER2)){
                        throw new Exception("You lack the privilege to perform this task.");
                    }
                    if(txtEmail.getText().toString().isEmpty()){
                        throw new Exception("User registered Email is required.");
                    }
                    //save data
                    userDalc.AppendUser(txtEmail.getText().toString().trim(), module.UserTypeSELLER2);
                }catch(Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        //get already appended users.
        userDalc.getUsersByRestaurant(module.userData.resturantID);
    }
}