package com.mickleentityltdnigeria.resturantapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import static com.mickleentityltdnigeria.resturantapp.AppGlobals.StartActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowMerchantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowMerchantFragment extends Fragment {


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ShowMerchantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowMerchantFragment.
     */

    public static ShowMerchantFragment newInstance(String param1, String param2) {
        ShowMerchantFragment fragment = new ShowMerchantFragment();
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
        return inflater.inflate(R.layout.fragment_show_merchant, container, false);
    }

    Resturant resturant;
    ImageView imgMerchantImage;
    TextView txtMerchantName, txtMerchantDescription, txtMerchantCountry, txtMerchantCity, txtMerchantZipCode;
    Button btnGoToShowPicture;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.resturant = ShowPictureFragment.resturant;
        imgMerchantImage = view.findViewById(R.id.imgMerchantImage);
        txtMerchantName = view.findViewById(R.id.txtMerchantName);
        txtMerchantDescription = view.findViewById(R.id.txtMerchantDescription);
        txtMerchantCountry = view.findViewById(R.id.txtMerchantCountry);
        txtMerchantCity = view.findViewById(R.id.txtMerchantCity);
        txtMerchantZipCode = view.findViewById(R.id.txtMerchantZipCode);
        btnGoToShowPicture = view.findViewById(R.id.btnGoToShowPicture);
        //add onClick listener
        btnGoToShowPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(ShowMerchantFragment.this)
                        .navigate(R.id.action_showMerchantFragment_to_showPictureFragment);
            }
        });
        //create listener for btnEmailUser.
        view.findViewById(R.id.btnMerchantEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = resturant.getEmail();
                Uri uri = Uri.parse(("mailto:" + em));
                Intent emailIntent = new Intent(Intent.ACTION_VIEW, uri);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Order");
                emailIntent.putExtra(Intent.EXTRA_TEXT, ("\n Dear " + resturant.getContactPerson() + ",\n\n\n\n\nRegards,\n\n" + module.userData.getLastName() + ", " + module.userData.getFirstName()));
                StartActivity(emailIntent);
            }
        });
        view.findViewById(R.id.btnMerchantPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = resturant.getPhone();
                Uri uri = Uri.parse("tel:" + phone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
                StartActivity(callIntent);
            }
        });
        //create listener for Map.
        view.findViewById(R.id.btnMerchantLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = resturant.getAddress() + ", " + resturant.getCity() + ", " + resturant.getCountry();
                Uri uri = Uri.parse(("geo:0,0?q=" + ad));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                //
                if (mapIntent.resolveActivity(requireContext().getPackageManager()) != null) {
                    StartActivity(mapIntent);
                } else {
                    Toast.makeText(requireContext(), "Google Map App not installed, please install a Map App and try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //load restaurant image
        imgMerchantImage.setImageDrawable(ImageHelper.getInstance().imageFromString(resturant.resturantImg));
        txtMerchantCity.setText(resturant.getCity());
        txtMerchantDescription.setText(resturant.getResturantDescription());
        txtMerchantCountry.setText(resturant.getCountry());
        txtMerchantZipCode.setText(resturant.getZipCode());
        txtMerchantName.setText(resturant.getResturantName());

    }
}