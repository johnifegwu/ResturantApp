package com.mickleentityltdnigeria.resturantapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickleentityltdnigeria.resturantapp.dalc.FeedBackDalc;
import com.mickleentityltdnigeria.resturantapp.data.model.FeedBack;
import com.mickleentityltdnigeria.resturantapp.extensions.FeedBackEventHandler;
import com.mickleentityltdnigeria.resturantapp.utils.module;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mickleentityltdnigeria.resturantapp.AppGlobals.StartActivity;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.ViewHolder> {

    private List<FeedBack> feedBack = new ArrayList<>();
    private final Context myContext = ApplicationContextProvider.getContext();
    private FeedBackRecyclerViewItemClickListener mItemClickListener;

    public FeedBackAdapter(List<FeedBack> feedBack, FeedBackRecyclerViewItemClickListener itemClickListener) {
        this.feedBack = feedBack;
        this.mItemClickListener = itemClickListener;
    }

    public void updateData(List<FeedBack> feedBack){
        this.feedBack = feedBack;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.a_single_feedback_row, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view,myContext);
        //create click listener
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClicked(feedBack.get(myViewHolder.getLayoutPosition()));
            }
        });
        //create listener for btnEmailUser.
        myViewHolder.itemView.findViewById(R.id.btnEmailUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBack fd = feedBack.get(myViewHolder.getLayoutPosition());
                String em = fd.getUserEmail();
                Uri uri = Uri.parse(("mailto:$em"));
                Intent emailIntent = new Intent(Intent.ACTION_VIEW,uri);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "RE: " + fd.getMsgSubject());
                emailIntent.putExtra(Intent.EXTRA_TEXT,("\n\n\nRE:"+ fd.getMsgSubject()+"\n\n-----------------------"+
                       "\n\n" +fd.msgBody));
                StartActivity(emailIntent);
            }
        });
        //
        ProgressBar progress = myViewHolder.itemView.findViewById(R.id.progressBarFeedBackRow);
        //
        FeedBackDalc feedBackDalc = new FeedBackDalc();
        FeedBackEventHandler feedBackUpdated = new FeedBackEventHandler() {
            @Override
            public void invoke(List<FeedBack> feedBackList) {
                feedBackDalc.getFeedBack(false);
            }
        };
        feedBackDalc.feedBackUpdated.addListener(feedBackUpdated);
        //
        FeedBackEventHandler feedBackFetched = new FeedBackEventHandler() {
            @Override
            public void invoke(List<FeedBack> feedBackList) {
                progress.setVisibility(View.GONE);
                feedBack = feedBackList;
                notifyDataSetChanged();
                Toast.makeText(myContext,"System updated successfully.",Toast.LENGTH_LONG).show();
            }
        };
        feedBackDalc.feedBackFetched.addListener(feedBackFetched);
        //
        //create listener for btnResolved.
        myViewHolder.itemView.findViewById(R.id.btnResolved).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //
                progress.setVisibility(View.VISIBLE);
                try{
                    FeedBack fb = feedBack.get(myViewHolder.getLayoutPosition());
                    fb.setRead(true);
                    fb.setResolved(true);
                    fb.setDateResolved(new Date());
                    fb.setResolvedBy(module.userName);
                    feedBackDalc.updateFeedBack(fb);
                    //
                }catch (Exception e){
                    progress.setVisibility(View.GONE);
                    Toast.makeText(myContext,e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(feedBack.get(position));
    }

    @Override
    public int getItemCount() {
        return feedBack.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private Context myContext;

        public ViewHolder(@NonNull View view,Context myContext) {
            super(view);
            this.myContext = myContext;
        }

        void bind(FeedBack feedBack){
            TextView txtDate = (TextView)itemView.findViewById(R.id.txtFeedBackDate);
            TextView txtFullName = (TextView)itemView.findViewById(R.id.txtFeedBackFullName);
            TextView txtEmail = (TextView)itemView.findViewById(R.id.txtFeedBackEmail);
            TextView txtFeedBackType = (TextView)itemView.findViewById(R.id.txtFeedBackType);
            TextView txtSubject = (TextView)itemView.findViewById(R.id.txtFeedBackSubject);
            TextView txtMsgBody = (TextView)itemView.findViewById(R.id.txtFeedBackMsgBody);
            txtDate.setText(feedBack.getMsgDate().toString());
            txtEmail.setText(feedBack.getUserEmail());
            txtFeedBackType.setText(feedBack.getFeedBackType());
            txtFullName.setText(feedBack.getUserFullName());
            txtMsgBody.setText(feedBack.getMsgBody());
            txtSubject.setText(feedBack.getMsgSubject());
        }

    }

}
//RecyclerView Click Listener
interface FeedBackRecyclerViewItemClickListener
{
    void onItemClicked(FeedBack feedBack);
}
