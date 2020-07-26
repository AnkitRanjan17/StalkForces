package com.android.we3.stalkforces.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.databases.Reminders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewReminderAdapter extends RecyclerView.Adapter<ViewReminderAdapter.ViewReminderViewHolder> {

    private List<Reminders> upcomingRemindersList;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int pos);
    }

    @NonNull
    @Override
    public ViewReminderAdapter.ViewReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminders_view,parent,false);
        ViewReminderAdapter.ViewReminderViewHolder viewHolder = new ViewReminderAdapter.ViewReminderViewHolder(v, onDeleteClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewReminderViewHolder holder, int position) {
        Reminders reminders = upcomingRemindersList.get(position);

        Long unixTime = Long.parseLong(reminders.getTime());
        Date date = new java.util.Date(unixTime);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("E, dd/MMM/yyyy HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
        String formattedDate = sdf.format(date);
        holder.timeDateTExtView.setText(formattedDate);

    }

    @Override
    public int getItemCount() {
        return upcomingRemindersList.size();
    }

    public  static  class ViewReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView timeDateTExtView;
        public ImageView imageViewDelete;
        OnDeleteClickListener onDeleteClickListener;

        public ViewReminderViewHolder(View view, OnDeleteClickListener onDeleteClickListener) {
            super(view);

            timeDateTExtView = view.findViewById(R.id.dateTimetextView);
            imageViewDelete = view.findViewById(R.id.deleteImageView);
            this.onDeleteClickListener = onDeleteClickListener;
            imageViewDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onDeleteClickListener.onDeleteClick(getAdapterPosition());
        }
    }

    public ViewReminderAdapter(List<Reminders> list, OnDeleteClickListener onDeleteClickListener) {
        upcomingRemindersList = list;
        this.onDeleteClickListener = onDeleteClickListener;
    }

}
