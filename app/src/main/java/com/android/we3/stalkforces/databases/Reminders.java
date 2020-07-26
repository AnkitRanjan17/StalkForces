package com.android.we3.stalkforces.databases;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "reminders")
public class Reminders {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String time;

    @NonNull
    private String contestId;

    public Reminders(@NonNull String id, @NonNull String time, @NonNull String contestId) {
        this.id = id;
        this.time = time;
        this.contestId = contestId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    @NonNull
    public String getContestId() {
        return contestId;
    }

    public void setContestId(@NonNull String contestId) {
        this.contestId = contestId;
    }
}
