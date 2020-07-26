package com.android.we3.stalkforces.databases;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RemindersDao {

    @Insert
    void insertReminders(Reminders reminders);

    @Query("SELECT * FROM reminders WHERE contestId=:contestId")
    LiveData<List<Reminders>> getReminders(String contestId);

    @Query("DELETE FROM reminders WHERE id=:id")
    void deleteReminders(String id);

}
