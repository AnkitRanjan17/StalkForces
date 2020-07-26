package com.android.we3.stalkforces.receivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.android.we3.stalkforces.Constants;
import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.activities.ViewReminderActivity;
import com.android.we3.stalkforces.databases.ReminderViewModel;
import com.android.we3.stalkforces.databases.RemindersDao;
import com.android.we3.stalkforces.databases.RemindersDatabase;

import androidx.annotation.ColorRes;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Notifier extends BroadcastReceiver {

    private final static String CHANNEL_ID = "test";
    NotificationChannel channel;
    NotificationManager manager;
    private RemindersDatabase remindersDatabase;

    @Override
    public void onReceive(Context context, Intent intent) {

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID,"Test", NotificationManager.IMPORTANCE_HIGH);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);
        Long unixTime = Long.parseLong(intent.getStringExtra("content"))*1000L;
        Date date = new java.util.Date(unixTime);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("E, dd/MMM/yyyy HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
        String formattedDate = sdf.format(date);

        Notification notification = builder.setSmallIcon(R.drawable.app_icon)
                .setContentTitle(intent.getStringExtra("name"))

                .setContentText("Round Scheduled at" + formattedDate)
                .build();

        manager.notify(Integer.parseInt(intent.getStringExtra("contestId")),notification);
        //Log.i("notificatioss",Long.toString((unixTime*1000)%100007)+intent.getStringExtra("contestId"));

        remindersDatabase = RemindersDatabase.getDatabase(context.getApplicationContext());
        RemindersDao remindersDao = remindersDatabase.remindersDao();

        new DeletesAsyncTask(remindersDao).execute(intent.getStringExtra("id"));


    }

    class DeletesAsyncTask extends AsyncTask<String, Void, Void> {

        RemindersDao mAsyncTaskDao;

        public DeletesAsyncTask(RemindersDao remindersDao) {
            this.mAsyncTaskDao = remindersDao;
        }

        @Override
        protected Void doInBackground(String... id) {
            mAsyncTaskDao.deleteReminders(id[0]);
            return null;
        }
    }
}
