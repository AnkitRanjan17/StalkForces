package com.android.we3.stalkforces.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.databases.ReminderViewModel;
import com.android.we3.stalkforces.databases.Reminders;
import com.android.we3.stalkforces.receivers.Notifier;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class SetReminderActivity extends AppCompatActivity {

    private TextView nameTextView,dateTextView,timeTextView;
    private Button SetReminderButton;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    static final int DATE_DIALOG_ID = 999;
    static final int TIME_DIALOG_ID = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        nameTextView = findViewById(R.id.contestNameSetReminderTextView);
        dateTextView = findViewById(R.id.contestDateSetReniderTextView);
        timeTextView = findViewById(R.id.contestTimeSetReniderTextView);
        nameTextView.setText(getIntent().getStringExtra("name"));

        setCurrentDateAndTimeOnView();
    }

    public void setCurrentDateAndTimeOnView() {

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        // set current time into textview
        timeTextView.setText(new StringBuilder().append(pad(AMorPMint(hour)))
                .append(":").append(pad(minute)).append(" ").append(AMorPM(hour)));

        // set current date into textview
        dateTextView.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(pad(month + 1)).append("-").append(pad(day)).append("-")
                .append(year).append(" "));
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            dateTextView.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener
            = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
            hour = selectedHour;
            minute = selectedMinute;

            // set current time into textview
            timeTextView.setText(new StringBuilder().append(pad(AMorPMint(hour)))
                    .append(":").append(pad(minute)).append(" ").append(AMorPM(hour)));
        }
    };

    //Displays a new dialog for date picker or time picker
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        datePickerListener, year, month,day);
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);
        }
        return null;
    }

    public void showTime(View view) {
        showDialog(TIME_DIALOG_ID);
    }

    public void showDate(View view) {
        showDialog(DATE_DIALOG_ID);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setReminderButtonOnClick(View view) throws ParseException {

        String timeString = pad(month+1)+"/"+pad(day)+"/"+pad(year)+" "+pad(hour)+":"+pad(minute)+":"+"00";
        Long time = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(timeString).getTime();
        /*Intent intent = new Intent(SetReminderActivity.this, Notifier.class);
        intent.putExtra("id",getIntent().getStringExtra("contestId") + Long.toString(time));
        intent.putExtra("name",getIntent().getStringExtra("name"));
        intent.putExtra("content",getIntent().getStringExtra("content"));
        intent.putExtra("contestId",getIntent().getStringExtra("contestId"));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(SetReminderActivity.this, Integer.parseInt(Long.toString((time/1000)%100007)+getIntent().getStringExtra("contestId")), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }*/
        Intent resultIntent = new Intent();
        resultIntent.putExtra("contestId", getIntent().getStringExtra("contestId"));
        resultIntent.putExtra("time",Long.toString(time));
        resultIntent.putExtra("id",getIntent().getStringExtra("contestId") + Long.toString(time));
        resultIntent.putExtra("name",getIntent().getStringExtra("name"));
        resultIntent.putExtra("content",getIntent().getStringExtra("content"));
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private String pad(int x)
    {
        if(x>=10)
            return Integer.toString(x);
        return ("0"+x);
    }


    private String AMorPM(int passedHour){
        if (passedHour > 12){ return "PM"; }
        else{ return "AM"; }
    }

    private int AMorPMint(int passedHour){
        if (passedHour > 12){ return passedHour-12; }
        else{ return passedHour; }
    }
}
