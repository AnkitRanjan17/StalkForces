package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

public class UpcomingContests {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("startTimeSeconds")
    private Integer startTimeSeconds;
    @SerializedName("phase")
    private String phase;
    @SerializedName("durationSeconds")
    private Integer durationSeconds;

    public UpcomingContests(){}

    public UpcomingContests(Integer id, String name, Integer startTimeSeconds, String phase, Integer durationSeconds) {
        this.id = id;
        this.name = name;
        this.startTimeSeconds = startTimeSeconds;
        this.phase = phase;
        this.durationSeconds = durationSeconds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public void setStartTimeSeconds(Integer startTimeSeconds) {
        this.startTimeSeconds = startTimeSeconds;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(Integer durationSeconds) {
        this.durationSeconds = durationSeconds;
    }
}
