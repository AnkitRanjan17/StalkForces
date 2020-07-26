package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

public class Contests {
    @SerializedName("contestId")
    private Integer contestId;

    @SerializedName("contestName")
    private String contestName;

    @SerializedName("handle")
    private String handle;

    @SerializedName("rank")
    private Integer rank;

    @SerializedName("oldRating")
    private Integer oldRating;

    @SerializedName("newRating")
    private Integer newRating;
    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getNewRating() {
        return newRating;
    }

    public void setNewRating(Integer newRating) {
        this.newRating = newRating;
    }

    public Integer getOldRating() {
        return oldRating;
    }

    public void setOldRating(Integer oldRating) {
        this.oldRating = oldRating;
    }
    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}
