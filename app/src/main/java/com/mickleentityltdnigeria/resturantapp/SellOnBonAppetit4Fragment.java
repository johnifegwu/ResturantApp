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

import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc;
import com.mickleentityltdnigeria.resturantapp.dalc.UserDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellOnBonAppetit4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellOnBonAppetit4Fragment extends Fragment {

    Button btnSave, btnBack;
    EditText txtWebsite, txtDesc;
    ProgressBar progress;
    ResturantDalc resturantDalc;
    UserDalc userDalc;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public SellOnBonAppetit4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellOnBonAppetit4Fragment.
     */
    public static SellOnBonAppetit4Fragment newInstance(String param1, String param2) {
        SellOnBonAppetit4Fragment fragment = new SellOnBonAppetit4Fragment();
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
        return inflater.inflate(R.layout.fragment_sell_on_bon_appetit4, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = view.findViewById(R.id.progressBarNewRestaurant2);
        txtDesc = view.findViewById(R.id.txtNewRestaurantDesc);
        txtWebsite = view.findViewById(R.id.txtNewRestaurantWebsite);
        btnBack = view.findViewById(R.id.btnBack3);
        btnSave = view.findViewById(R.id.btnSaveNewRestaurant);
        //
        userDalc = new UserDalc();
        resturantDalc = new ResturantDalc();
        //
        UserUpdatedHandler userUpdatedEvent = new UserUpdatedHandler() {
            @Override
            public void invoke(List<User> users) {
                progress.setVisibility(View.GONE);
                module.newResturant = new Resturant();
                NavHostFragment.findNavController(SellOnBonAppetit4Fragment.this)
                        .navigate(R.id.action_sellOnBonAppetit4Fragment_to_sellOnBonAppetit5Fragment);
            }
        };
        userDalc.userDataUpdated.addListener(userUpdatedEvent);
        //
        ResturantUpdatedHandler restaurantAddedd = new ResturantUpdatedHandler() {
            @Override
            public void invoke(List<Resturant> Resturant) {
                module.userData.setResturantID(Resturant.get(0).getResturantID());
                userDalc.UpdateUser( module.userData);
            }
        };
        resturantDalc.newResturantAdded.addListener(restaurantAddedd);
        //
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(SellOnBonAppetit4Fragment.this)
                        .navigate(R.id.action_sellOnBonAppetit4Fragment_to_sellOnBonAppetit3Fragment);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    progress.setVisibility(View.VISIBLE);
                    module.checkNetwork();
                    if(txtDesc.getText().toString().isEmpty()){
                        throw new Exception("Description is required");
                    }
                    module.newResturant.setResturantDescription(txtDesc.getText().toString());
                    module.newResturant.setWebsiteUrl(txtWebsite.getText().toString().trim());
                    resturantDalc.AddResturant(module.newResturant);
                }catch (Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}