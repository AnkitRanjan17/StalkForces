package com.android.we3.stalkforces.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.models.UpcomingContests;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpcomingContestsAdapter extends RecyclerView.Adapter<UpcomingContestsAdapter.UpcomingContestsViewHolder> {

    private ArrayList<UpcomingContests> upcomingContestsArrayList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    @NonNull
    @Override
    public UpcomingContestsAdapter.UpcomingContestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("aaya",parent.toString()+" yo");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_contest_view,parent,false);
        UpcomingContestsAdapter.UpcomingContestsViewHolder viewHolder = new UpcomingContestsAdapter.UpcomingContestsViewHolder(v, onItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingContestsViewHolder holder, int position) {
        Log.i("aaya",position+" "+holder.toString());
        UpcomingContests upcomingContests = upcomingContestsArrayList.get(position);
        holder.nameTextView.setText(upcomingContests.getName());

        Long unixTime = upcomingContests.getStartTimeSeconds()*1000L;
        Date date = new java.util.Date(unixTime);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("E, dd/MMM/yyyy HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
        String formattedDate = sdf.format(date);
        holder.timeTextView.setText(formattedDate);

        Integer time = upcomingContests.getDurationSeconds();
        String duration = "";
        if(time/86400!=0)
        {
            duration += Integer.toString(time/86400)+" days ";
            time = time%86400;
        }
        if(time/3600!=0)
        {
            duration += Integer.toString(time/3600)+":";
            time = time%3600;
        }
        if(time/60!=0)
        {
            duration += Integer.toString(time/60)+" hrs";
        }
        else
        {
            duration += "00 hrs";
        }
        holder.durationTextView.setText(duration);

        /*holder.text1.setText(submission.getProblem().getContestId() + "-" + submission.getProblem().getIndex());
        holder.text2.setText(Html.fromHtml("<u>"+submission.getProblem().getName() + "</u>"));
        holder.text3.setText(Html.fromHtml("<u>#" + submission.getId().toString() + "</u>"));
        String verdict = submission.getVerdict();
        if(verdict.equals("OK")) {
            holder.text4.setTextColor(Color.parseColor("#09f45c"));
            holder.text4.setTypeface(holder.text4.getTypeface(), Typeface.BOLD);
            holder.text4.setText("ACCEPTED");
        }
        else {
            holder.text4.setTextColor(Color.parseColor("#000000"));
            holder.text4.setTypeface(null, Typeface.NORMAL);
            holder.text4.setText(submission.getVerdict() + " on Test Case " + String.valueOf(submission.getPassedTestCount()+1));
        }
        holder.text5.setText(submission.getTimeConsumedMillis() + " ms");
        holder.text6.setText(submission.getMemoryConsumedBytes()/1024 + " KB");
        Long time = submission.getCreationTime();
        Date d = new Date(time*1000);
        holder.text7.setText(d.toString());*/
    }

    @Override
    public int getItemCount() {
        return upcomingContestsArrayList.size();
    }

    public  static  class UpcomingContestsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nameTextView,timeTextView,durationTextView;
        public ImageView imageView;
        OnItemClickListener onItemClickListener;

        public UpcomingContestsViewHolder(View view, OnItemClickListener onItemClickListener) {
            super(view);

            nameTextView = view.findViewById(R.id.contestNameUpcomngContestTextView);
            timeTextView = view.findViewById(R.id.contestTimeUpcomngContestTextView);
            durationTextView = view.findViewById(R.id.contestDurationUpcomngContestTextView);
            imageView = view.findViewById(R.id.addReminderUpcomingContestImageView);
            this.onItemClickListener = onItemClickListener;
            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public UpcomingContestsAdapter(ArrayList<UpcomingContests> list, OnItemClickListener onItemClickListener) {
        upcomingContestsArrayList = list;
        this.onItemClickListener = onItemClickListener;
    }

}

