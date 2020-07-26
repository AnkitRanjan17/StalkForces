package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpcomingContestsResult {

    @SerializedName("status")
    private String status;
    @SerializedName("result")
    private List<UpcomingContests> upcomingContestsList;

    public UpcomingContestsResult(){}

    public UpcomingContestsResult(String status, List<UpcomingContests> upcomingContestsList) {
        this.status = status;
        this.upcomingContestsList = upcomingContestsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<UpcomingContests> getUpcomingContestsList() {
        return upcomingContestsList;
    }

    public void setUpcomingContestsList(List<UpcomingContests> upcomingContestsList) {
        this.upcomingContestsList = upcomingContestsList;
    }
}
