package com.mickleentityltdnigeria.resturantapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickleentityltdnigeria.resturantapp.dalc.ResturantDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.Resturant;
import com.mickleentityltdnigeria.resturantapp.extensions.ResturantUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.ImageHelper;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mickleentityltdnigeria.resturantapp.AppGlobals.StartActivity;
import static com.mickleentityltdnigeria.resturantapp.AppGlobals.getAppContext;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Resturant> resturants = new ArrayList<>();
    private final Context myContext = ApplicationContextProvider.getContext();
    private ResturantRecyclerViewItemClickListener mItemClickListener;
    ResturantDalc resturantDalc;
    private boolean isApproved, byQueryString, suspended;
    private String country;

    public RestaurantAdapter(List<Resturant> resturants, ResturantRecyclerViewItemClickListener mItemClickListener, boolean isApproved, boolean byQueryString, String country, boolean suspended) {
        this.resturants = resturants;
        this.mItemClickListener = mItemClickListener;
        this.isApproved = isApproved;
        this.byQueryString = byQueryString;
        this.suspended = suspended;
        this.country = country;
    }

    public void updateData(List<Resturant> resturants) {
        this.resturants = resturants;
        notifyDataSetChanged();
    }

    public void getDataByQueryString(String country, boolean suspended){
        this.country = country;
        this.suspended = suspended;
        this.byQueryString = true;
        resturantDalc.getRestaurantByQueryString(Resturant.getQqueryString(country,suspended));
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.a_single_restaurant_row, parent, false);
        RestaurantAdapter.ViewHolder myViewHolder = new RestaurantAdapter.ViewHolder(view, myContext);
        this.resturantDalc = new ResturantDalc();
        //create click listener
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClicked(resturants.get(myViewHolder.getLayoutPosition()));
            }
        });
        //create listener for btnEmailUser.
        myViewHolder.itemView.findViewById(R.id.btnRowRestaurantEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resturant resturant = resturants.get(myViewHolder.getLayoutPosition());
                String em = resturant.getEmail();
                Uri uri = Uri.parse(("mailto:" + em));
                Intent emailIntent = new Intent(Intent.ACTION_VIEW, uri);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Registration");
                emailIntent.putExtra(Intent.EXTRA_TEXT, ("\n Dear " + resturant.getContactPerson() + ",\n\n\n\n\nRegards,\n\n" + module.userData.getLastName() + ", " + module.userData.getFirstName() + "\n\nBON APPETIT TEAM."));
                StartActivity(emailIntent);
            }
        });
        myViewHolder.itemView.findViewById(R.id.btnRowRestaurantPhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resturant resturant = resturants.get(myViewHolder.getLayoutPosition());
                String phone = resturant.getPhone();
                Uri uri = Uri.parse("tel:" + phone);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
                StartActivity(callIntent);
            }
        });
        //create listener for Map.
        myViewHolder.itemView.findViewById(R.id.btnRowRestaurantLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resturant resturant = resturants.get(myViewHolder.getLayoutPosition());
                String ad = resturant.getAddress() + ", " + resturant.getCity() + ", " + resturant.getCountry();
                Uri uri = Uri.parse(("geo:0,0?q=" + ad));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                //
                if (mapIntent.resolveActivity(myContext.getPackageManager()) != null) {
                    StartActivity(mapIntent);
                } else {
                    Toast.makeText(myContext, "Google Map App not installed, please install a Map App and try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //create listener for delete.
        myViewHolder.itemView.findViewById(R.id.btnRowRestaurantDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getAppContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                Resturant rs = resturants.get(myViewHolder.getLayoutPosition());
                               if(!rs.approved){ resturantDalc.DeleteResturant(rs);}
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_baseline_warning_48)
                        .show();

            }
        });
        //
        ProgressBar progress = myViewHolder.itemView.findViewById(R.id.progressBarRowRestaurant);
        //set listeners for restaurant.
        ResturantUpdatedHandler restaurantUpdated = new ResturantUpdatedHandler() {
            @Override
            public void invoke(List<Resturant> resturant) {
                if(!byQueryString){
                    resturantDalc.getRestaurantByIsApproved(isApproved);
                }else{
                    resturantDalc.getRestaurantByQueryString(Resturant.getQqueryString(country,suspended));
                }
            }
        };
        resturantDalc.resturantUpdated.addListener(restaurantUpdated);
        //
        ResturantUpdatedHandler restaurantDeleted = new ResturantUpdatedHandler() {
            @Override
            public void invoke(List<Resturant> resturant) {
                if(!byQueryString){
                    resturantDalc.getRestaurantByIsApproved(isApproved);
                }else{
                    resturantDalc.getRestaurantByQueryString(Resturant.getQqueryString(country,suspended));
                }
            }
        };
        resturantDalc.resturantDeleted.addListener(restaurantDeleted);
        //
        ResturantUpdatedHandler restaurantFetched = new ResturantUpdatedHandler() {
            @Override
            public void invoke(List<Resturant> Resturant) {
                progress.setVisibility(View.GONE);
                resturants = Resturant;
                notifyDataSetChanged();
                Toast.makeText(myContext, "System updated successfully.", Toast.LENGTH_LONG).show();
            }
        };
        resturantDalc.resturantDataFetched.addListener(restaurantFetched);
        //
        ResturantUpdatedHandler restaurantNotFound = new ResturantUpdatedHandler() {
            @Override
            public void invoke(List<Resturant> Resturant) {
                progress.setVisibility(View.GONE);
                resturants = new ArrayList<>();
                notifyDataSetChanged();
                Toast.makeText(myContext, "System updated successfully.", Toast.LENGTH_LONG).show();
            }
        };
        resturantDalc.resturantNotFound.addListener(restaurantNotFound);
        //create listener for btnApprove
        myViewHolder.itemView.findViewById(R.id.btnApprove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                progress.setVisibility(View.VISIBLE);
                try {
                    Button btnApprove = myViewHolder.itemView.findViewById(R.id.btnApprove);
                    Resturant rs = resturants.get(myViewHolder.getLayoutPosition());
                    String approve = myContext.getString(R.string.approve);
                    String suspend = myContext.getString(R.string.suspend);
                    if (btnApprove.getText().toString().equals(approve)) {
                        rs.setApproved(true);
                        rs.setApprovedBy(module.userName);
                        rs.setDateApproved(new Date());
                    } else if (btnApprove.getText().toString().equals(suspend)) {
                        rs.setSuspended(true);
                        rs.setSuspendedBy(module.userName);
                        rs.setDateSuspended(new Date());
                    }
                    resturantDalc.UpdateResturant(rs);
                    //
                } catch (Exception e) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        //
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        holder.bind(resturants.get(position));
    }

    @Override
    public int getItemCount() {
        return resturants.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private Context myContext;

        public ViewHolder(@NonNull View view, Context myContext) {
            super(view);
            this.myContext = myContext;
        }

        void bind(Resturant resturant) {
            ImageView imgResturant = itemView.findViewById(R.id.imgRowRestaurantImage);
            TextView txtResturantName = itemView.findViewById(R.id.txtRowRestaurantName);
            TextView txtContactPerson = itemView.findViewById(R.id.txtRowRestaurantContactPerson);
            TextView txtCountry = itemView.findViewById(R.id.txtRowRestaurantCountry);
            TextView txtCity = itemView.findViewById(R.id.txtRowRestaurantCity);
            TextView txtZipCode = itemView.findViewById(R.id.txtRowRestaurantZipCode);
            Button btnApprove = itemView.findViewById(R.id.btnApprove);
            String tx;
            if (resturant.isApproved()) {
                tx = myContext.getString(R.string.suspend);
                itemView.findViewById(R.id.btnRowRestaurantDelete).setEnabled(false);
            } else {
                tx = myContext.getString(R.string.approve);
                itemView.findViewById(R.id.btnRowRestaurantDelete).setEnabled(true);
            }
            btnApprove.setText(tx);
            //load restaurant image
            imgResturant.setImageDrawable(ImageHelper.getInstance().imageFromString(resturant.resturantImg));
            txtCity.setText(resturant.getCity());
            txtContactPerson.setText(resturant.getContactPerson());
            txtCountry.setText(resturant.getCountry());
            txtZipCode.setText(resturant.getZipCode());
            txtResturantName.setText(resturant.getResturantName());
        }

    }
}

//RecyclerView Click Listener
interface ResturantRecyclerViewItemClickListener {
    void onItemClicked(Resturant resturant);
}
