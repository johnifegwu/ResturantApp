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

import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewRegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewRegistrationFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NewRegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewRegistrationFragment.
     */
    public static NewRegistrationFragment newInstance(String param1, String param2) {
        NewRegistrationFragment fragment = new NewRegistrationFragment();
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
        return inflater.inflate(R.layout.fragment_new_registration, container, false);
    }

    ResturantDalc resturantDalc;
    List<Resturant> resturants = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.resturantDalc = new ResturantDalc();
        //
        //Reference of RecyclerView
        RecyclerView mRecyclerView = view.findViewById(R.id.newRegistrationRecyclerView);

        //Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppGlobals.getAppContext(), RecyclerView.VERTICAL, false);

        //Set Layout Manager to RecyclerView
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //Create adapter
        RestaurantAdapter adapter = new RestaurantAdapter(resturants, new ResturantRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(Resturant resturant) {

            }
        },false);
        //Set adapter to RecyclerView
        mRecyclerView.setAdapter(adapter);
        //
        ResturantUpdatedHandler restaurantFetched =  new ResturantUpdatedHandler() {
            @Override
            public void invoke(List<Resturant> Resturant) {
                resturants = Resturant;
                adapter.updateData(Resturant);
            }
        };
        resturantDalc.resturantDataFetched.addListener(restaurantFetched);
        resturantDalc.getRestaurantByIsApproved(false);
        //
    }
}