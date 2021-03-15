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

import com.mickleentityltdnigeria.resturantapp.dalc.FeedBackDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FeedBack;
import com.mickleentityltdnigeria.resturantapp.extensions.FeedBackEventHandler;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedBackListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedBackListFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public FeedBackListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedBackListFragment.
     */

    public static FeedBackListFragment newInstance(String param1, String param2) {
        FeedBackListFragment fragment = new FeedBackListFragment();
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
        return inflater.inflate(R.layout.fragment_feed_back_list, container, false);
    }

    FeedBackAdapter adapter;
    FeedBackDalc feedBackDalc;
    RecyclerView recyclerView;
    List<FeedBack> feedBackList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedBackDalc = new FeedBackDalc();
        FeedBackEventHandler feedBackFetched = new FeedBackEventHandler() {
            @Override
            public void invoke(List<FeedBack> feedBackList) {
                adapter.updateData(feedBackList);
            }
        };
        feedBackDalc.feedBackFetched.addListener(feedBackFetched);
        //call get feedback after initialising other controls.

        //Reference of RecyclerView
        recyclerView = view.findViewById(R.id.feedBackListRecyclerView);

        //Linear Layout Manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false);

        //Set Layout Manager to RecyclerView
        recyclerView.setLayoutManager(linearLayoutManager);

        //Initialise adapter
        adapter = new FeedBackAdapter(feedBackList, new FeedBackRecyclerViewItemClickListener() {
            @Override
            public void onItemClicked(FeedBack feedBack) {

            }
        });

        //Set adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        //call get feedback
        feedBackDalc.getFeedBack(false);
    }
}