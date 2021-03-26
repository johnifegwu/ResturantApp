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
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.utils.module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellOnBonAppetit3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellOnBonAppetit3Fragment extends Fragment {

    Button btnBack, btnNext;
    EditText txtZipCodes;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SellOnBonAppetit3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellOnBonAppetit3Fragment.
     */
    public static SellOnBonAppetit3Fragment newInstance(String param1, String param2) {
        SellOnBonAppetit3Fragment fragment = new SellOnBonAppetit3Fragment();
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
        return inflater.inflate(R.layout.fragment_sell_on_bon_appetit3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtZipCodes = view.findViewById(R.id.txtNewRestaurantZipCodes);
        btnBack = view.findViewById(R.id.btnBack2);
        btnNext = view.findViewById(R.id.btnNext3);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SellOnBonAppetit3Fragment.this)
                        .navigate(R.id.action_sellOnBonAppetit3Fragment_to_sellOnBonAppetit2Fragment);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(!txtZipCodes.getText().toString().isEmpty()){
                        String[] zip = txtZipCodes.getText().toString().split(" ");
                        if(zip[0] == ""){
                            throw new Exception("You must add at least one ZipCode");
                        }
                    }else {
                        throw new Exception("You must add at least one ZipCode");
                    }
                    String zip = "";
                    String zipX = txtZipCodes.getText().toString();
                    if(zipX.contains("\n")){
                        String[] line = zipX.split("\n");
                        for(String l:line){
                            for(String s: l.split(" ")){
                                zip += ( module.newResturant.getCountry().trim() + "-" + s.trim() + " ");
                            }
                        }
                    }else {
                        for(String s: zipX.split(" ")){
                            zip += ( module.newResturant.getCountry().trim() + "-" + s.trim() + " ");
                        }
                    }
                    module.newResturant.setZipCodes(zip);
                    //ZipCodes preserved for future updates.
                    module.newResturant.setZipCodesX(zipX);
                    NavHostFragment.findNavController(SellOnBonAppetit3Fragment.this)
                            .navigate(R.id.action_sellOnBonAppetit3Fragment_to_sellOnBonAppetit4Fragment);
                }catch (Exception e){
                    Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}