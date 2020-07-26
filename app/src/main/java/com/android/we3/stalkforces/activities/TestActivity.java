package com.android.we3.stalkforces.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.receivers.Notifier;

import java.util.Date;

public class TestActivity extends AppCompatActivity {

    private Button button;
    private long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edi = findViewById(R.id.notifEditText);
                time = Long.parseLong(edi.getText().toString());
                Date date = new Date(time);
                Log.i("kyaaaya",date.toString());
                Intent intent = new Intent(TestActivity.this, Notifier.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(TestActivity.this, 13, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
                else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                }
            }
        });
    }
}
