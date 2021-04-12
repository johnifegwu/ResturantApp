package com.mickleentityltdnigeria.resturantapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickleentityltdnigeria.resturantapp.dalc.UserDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.User;
import com.mickleentityltdnigeria.resturantapp.extensions.UserUpdatedHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.List;

import static com.mickleentityltdnigeria.resturantapp.AppGlobals.getAppContext;

public class AppendedUserAdapter extends RecyclerView.Adapter<AppendedUserAdapter.ViewHolder> {

    private List<User> users;
    private final Context myContext = ApplicationContextProvider.getContext();
    private AppendedUsersRecyclerViewItemClickListener mItemClickListener;
    private UserDalc userDalc;

    public void updateData(List<User> userList) {
        this.users = userList;
        notifyDataSetChanged();
    }

    public AppendedUserAdapter(List<User> users, AppendedUsersRecyclerViewItemClickListener mItemClickListener) {
        this.users = users;
        this.mItemClickListener = mItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.merchant_users_single_row, parent, false);
        AppendedUserAdapter.ViewHolder myViewHolder = new AppendedUserAdapter.ViewHolder(view, myContext);
        this.userDalc = new UserDalc();
        ProgressBar progress = myViewHolder.itemView.findViewById(R.id.progressBarMerchantUserRow);
        //create click listener
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClicked(users.get(myViewHolder.getLayoutPosition()));
            }
        });
        //create listener for un append
        UserUpdatedHandler userUnAppended = new UserUpdatedHandler() {
            @Override
            public void invoke(List<User> users) {
                userDalc.getUsersByRestaurant(module.userData.getResturantID());
            }
        };
        userDalc.userUnAppended.addListener(userUnAppended);
        //set listener for users fetched
        UserUpdatedHandler userDataFetched = new UserUpdatedHandler() {
            @Override
            public void invoke(List<User> usersList) {
                progress.setVisibility(View.GONE);
                users = usersList;
                notifyDataSetChanged();
            }
        };
        userDalc.userDataFetched.addListener(userDataFetched);
        //set listener for no data fetched
        UserUpdatedHandler userDataNotFound = new UserUpdatedHandler() {
            @Override
            public void invoke(List<User> usersList) {
                progress.setVisibility(View.GONE);
                users = new ArrayList<>();
                notifyDataSetChanged();
            }
        };
        userDalc.userNotFound.addListener(userDataNotFound);
        //Create listener for un append.
        myViewHolder.itemView.findViewById(R.id.btnUnAppendUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getAppContext())
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to un append this user?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                progress.setVisibility(View.VISIBLE);
                                try {
                                    module.checkNetwork();
                                    if (module.userType.equals(module.UserTypeSELLER2) || module.userType.equals(module.UserTypeSUPPER2)) {
                                        throw new Exception("You lack the privilege to perform this task.");
                                    }
                                    userDalc.unAppendUser(users.get(myViewHolder.getLayoutPosition()).getUserName());
                                } catch (Exception e) {
                                    progress.setVisibility(View.GONE);
                                    Toast.makeText(myContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(R.drawable.ic_baseline_warning_48)
                        .show();
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppendedUserAdapter.ViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private Context myContext;

        public ViewHolder(@NonNull View view, Context myContext) {
            super(view);
            this.myContext = myContext;
        }

        void bind(User user) {
            TextView txtEmail = itemView.findViewById(R.id.txtAppendedUserEmail);
            TextView txtFullName = itemView.findViewById(R.id.txtAppendedUserFullName);
            ImageView btnUnAppendUser = itemView.findViewById(R.id.btnUnAppendUser);
            if (user.getUserType().equals(module.UserTypeSELLER) || user.getUserType().equals(module.UserTypeSUPPER)) {
                btnUnAppendUser.setEnabled(false);
            }
            txtEmail.setText(user.getUserName());
            txtFullName.setText((user.getFirstName() + " " + user.getLastName()));
        }

    }

}

interface AppendedUsersRecyclerViewItemClickListener {
    void onItemClicked(User user);
}
