package com.android.we3.stalkforces.adapters;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.models.Submission;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserSubmissionsAdapter extends RecyclerView.Adapter<UserSubmissionsAdapter.UserSubmissionsViewHolder> {

    private ArrayList<Submission> submissionList;
    private OnSubmissionClickListener onSubmissionClickListener;

    public interface OnSubmissionClickListener {
        void onSubmissionClick(int pos);
        void onContestClick(int pos);
    }

    @NonNull
    @Override
    public UserSubmissionsAdapter.UserSubmissionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("aaya",parent.toString()+" yo");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.submission_view,parent,false);
        UserSubmissionsAdapter.UserSubmissionsViewHolder viewHolder = new UserSubmissionsAdapter.UserSubmissionsViewHolder(v, onSubmissionClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserSubmissionsViewHolder holder, int position) {
        Log.i("aaya",position+" "+holder.toString());
        Submission submission = submissionList.get(position);
        holder.text1.setText(submission.getProblem().getContestId() + "-" + submission.getProblem().getIndex());
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
        holder.text7.setText(d.toString());
    }

    @Override
    public int getItemCount() {
        return submissionList.size();
    }

    public  static  class UserSubmissionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView text1, text2, text3, text4, text5, text6, text7;
        OnSubmissionClickListener onSubmissionClickListener;

        public UserSubmissionsViewHolder(View view, OnSubmissionClickListener onSubmissionClickListener) {
            super(view);
            text1 = (TextView)view.findViewById(R.id.problemIdTextView);
            text2 = (TextView)view.findViewById(R.id.problemNameTextView);
            text3 = (TextView)view.findViewById(R.id.submissionIdTextView);
            text4 = (TextView)view.findViewById(R.id.submissionResultTextView);
            text5 = (TextView)view.findViewById(R.id.timeTakenTextView);
            text6 = (TextView)view.findViewById(R.id.memoryTakenTextView);
            text7 = (TextView)view.findViewById(R.id.dateTimeTextView);
            this.onSubmissionClickListener = onSubmissionClickListener;
            text3.setOnClickListener(this);
            text2.setOnClickListener(this);
            /*text3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("clickhua",text3.getText().toString());
                }
            });*/
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.submissionIdTextView)
                onSubmissionClickListener.onSubmissionClick(getAdapterPosition());
            if(v.getId() == R.id.problemNameTextView)
                onSubmissionClickListener.onContestClick(getAdapterPosition());
        }
    }

    public UserSubmissionsAdapter(ArrayList<Submission> list, OnSubmissionClickListener onSubmissionClickListener) {
        submissionList = list;
        this.onSubmissionClickListener = onSubmissionClickListener;
    }

}
