package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CodeforcesUserJson {

    @SerializedName("status")
    private String status;
    @SerializedName("comment")
    private String comment;
    @SerializedName("result")
    private List<CodeforcesUser> result;

    public CodeforcesUserJson(){}

    public CodeforcesUserJson(String status, List<CodeforcesUser> codeforcesUser) {
        this.status = status;
        this.result = codeforcesUser;
    }

    public CodeforcesUserJson(String status, String comment, List<CodeforcesUser> codeforcesUser) {
        this.status = status;
        this.comment = comment;
        this.result = codeforcesUser;
    }

    public String getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public List<CodeforcesUser> getResult() {
        return result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setResult(List<CodeforcesUser> codeforcesUser) {
        this.result = codeforcesUser;
    }
}
