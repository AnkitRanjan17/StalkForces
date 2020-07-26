package com.android.we3.stalkforces.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.ViewReminderAdapter;
import com.android.we3.stalkforces.databases.ReminderViewModel;
import com.android.we3.stalkforces.databases.Reminders;
import com.android.we3.stalkforces.receivers.Notifier;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewReminderActivity extends AppCompatActivity implements ViewReminderAdapter.OnDeleteClickListener{

    private TextView nameContestTextView;
    private ImageView addReminderImageView;
    private TextView contestDateTextView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ViewReminderAdapter viewReminderAdapter;
    private List<Reminders> remindersList;
    private ReminderViewModel reminderViewModel;
    private static final int SET_REMINDER_ACTIVITY_REQUEST_CODE = 1;
    private String deleteId;
    private Long deleteTime;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        nameContestTextView = findViewById(R.id.contestNameViewRemiderTExtView);
        addReminderImageView = findViewById(R.id.addReminderViewReminderImageView);
        contestDateTextView = findViewById(R.id.contestDateViewReminderTextView);
        nameContestTextView.setText(getIntent().getStringExtra("name"));
        contestDateTextView.setText(getIntent().getStringExtra("scheduleTime"));
        addReminderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ViewReminderActivity.this,SetReminderActivity.class);
                intent.putExtra("contestId",getIntent().getStringExtra("contestId"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                intent.putExtra("content",getIntent().getStringExtra("content"));
                startActivityForResult(intent,SET_REMINDER_ACTIVITY_REQUEST_CODE);
            }
        });
        recyclerView = findViewById(R.id.viewReminderRecyclerView);
        layoutManager = new LinearLayoutManager(ViewReminderActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        remindersList = new ArrayList<>();
        viewReminderAdapter = new ViewReminderAdapter(remindersList,this);
        recyclerView.setAdapter(viewReminderAdapter);
        reminderViewModel = new ViewModelProvider(this).get(ReminderViewModel.class);
        reminderViewModel.getAllReminders(getIntent().getStringExtra("contestId")).observe(this, new Observer<List<Reminders>>() {
            @Override
            public void onChanged(@Nullable List<Reminders> list) {
                remindersList.clear();
               remindersList.addAll(list);
               viewReminderAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDeleteClick(int pos)
    {
        Reminders reminders = remindersList.get(pos);
        reminderViewModel.deleteReminders(reminders.getId());
        deleteTime = Long.parseLong(reminders.getTime());

        Intent intent = new Intent(ViewReminderActivity.this, Notifier.class);
        intent.putExtra("id",deleteId);
        intent.putExtra("name",getIntent().getStringExtra("name"));
        intent.putExtra("content",getIntent().getStringExtra("content"));
        intent.putExtra("contestId",getIntent().getStringExtra("contestId"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewReminderActivity.this, Integer.parseInt(Long.toString((deleteTime/1000)%100007)+getIntent().getStringExtra("contestId")), intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(ViewReminderActivity.this,"Reminder Deleted",Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SET_REMINDER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            String ids = data.getStringExtra("contestId") + data.getStringExtra("time");
            Reminders reminder = new Reminders(ids, Objects.requireNonNull(data.getStringExtra("time")), Objects.requireNonNull(data.getStringExtra("contestId").toString()));

            reminderViewModel.insertReminders(reminder);

            Intent intent = new Intent(ViewReminderActivity.this, Notifier.class);
            intent.putExtra("id",data.getStringExtra("id"));
            intent.putExtra("name",data.getStringExtra("name"));
            intent.putExtra("content",data.getStringExtra("content"));
            intent.putExtra("contestId",data.getStringExtra("contestId"));
            deleteId = data.getStringExtra("id");
            deleteTime = Long.parseLong(data.getStringExtra("time"));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(ViewReminderActivity.this, Integer.parseInt(Long.toString((Long.parseLong(data.getStringExtra("time"))/1000)%100007)+data.getStringExtra("contestId")), intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, Long.parseLong(Objects.requireNonNull(data.getStringExtra("time"))), pendingIntent);
            }
            else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, Long.parseLong(Objects.requireNonNull(data.getStringExtra("time"))), pendingIntent);
            }

        }

    }
}
