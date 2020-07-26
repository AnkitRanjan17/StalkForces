package com.android.we3.stalkforces.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Reminders.class, version = 2)
public abstract class RemindersDatabase extends RoomDatabase {

    public abstract RemindersDao remindersDao();

    private static volatile RemindersDatabase remindersDatabaseInstance;

    static public RemindersDatabase getDatabase(final Context context) {
        if (remindersDatabaseInstance == null) {
            synchronized (RemindersDatabase.class) {
                if (remindersDatabaseInstance == null) {
                    remindersDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            RemindersDatabase.class, "reminders")
                            .build();
                }
            }
        }
        return remindersDatabaseInstance;
    }
}
