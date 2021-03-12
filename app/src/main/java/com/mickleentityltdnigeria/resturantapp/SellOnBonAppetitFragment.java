package com.mickleentityltdnigeria.resturantapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.MyLifecycleObserver;
import com.mickleentityltdnigeria.resturantapp.utils.module;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellOnBonAppetitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellOnBonAppetitFragment extends Fragment {

    Button btnAddImage, btnNext1;
    ImageView imgResturant;
    EditText txtRestaurantName, txtContactPerson;
    AppCompatSpinner spinnerRestaurantTypes;
    ProgressBar progress;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SellOnBonAppetitFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SellOnBonAppetittFragment.
     */
    public static SellOnBonAppetitFragment newInstance(String param1, String param2) {
        SellOnBonAppetitFragment fragment = new SellOnBonAppetitFragment();
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
        return inflater.inflate(R.layout.fragment_sell_on_bon_appetit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //
        btnNext1 = view.findViewById(R.id.btnNext1);
        btnAddImage = view.findViewById(R.id.btnAddImage);
        imgResturant = view.findViewById(R.id.imgResturant);
        spinnerRestaurantTypes = view.findViewById(R.id.spinnerRestaurantTypes);
        txtContactPerson = view.findViewById(R.id.txtContactPerson);
        txtRestaurantName = view.findViewById(R.id.txtRestaurantName);
        progress = view.findViewById(R.id.progressBarNewRestaurant);
        //
        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("image/*");
            }
        });
        imgResturant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
            }
        });
        //
        btnNext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                try{
                    if(module.newRestaurantImg == null){
                        throw new Exception("An image is required for this Restaurant.");
                    }
                    if(txtRestaurantName.getText().toString().isEmpty() || txtContactPerson.getText().toString().isEmpty()){
                        throw new Exception("Fill in all required fields.");
                    }
                    module.newResturant.setResturantName(txtRestaurantName.getText().toString().trim());
                    module.newResturant.setResturantType(spinnerRestaurantTypes.getSelectedItem().toString());
                    module.newResturant.setContactPerson(txtContactPerson.getText().toString().trim());
                    module.newResturant.setUserID(module.userID);
                    progress.setVisibility(View.GONE);
                    NavHostFragment.findNavController(SellOnBonAppetitFragment.this)
                            .navigate(R.id.action_sellOnBonAppetittFragment_to_sellOnBonAppetit2Fragment);
                }catch (Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    try {
                        module.newRestaurantImg =  ImageHelper.getInstant().getCompressedBitmap(uri.getPath(),1002000);
                        module.newResturant.setResturantImg(ImageHelper.getInstant().byteArrayToString(module.newRestaurantImg));
                        imgResturant.setImageDrawable(ImageHelper.getInstant().imageFromByteArray(module.newRestaurantImg));
                    }catch (Exception e){
                        Toast.makeText(requireContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });


}