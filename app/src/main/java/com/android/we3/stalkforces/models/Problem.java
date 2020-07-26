package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Problem {

    @SerializedName("contestId")
    private Integer contestId;

    @SerializedName("problemsetName")
    private String problemsetName;

    @SerializedName("index")
    private String index;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("rating")
    private Integer rating;

    @SerializedName("tags")
    private List<String> tags;

    public Problem(Integer contestId, String problemsetName, String index, String name, String type, Integer rating, List<String> tags) {
        this.contestId = contestId;
        this.problemsetName = problemsetName;
        this.index = index;
        this.name = name;
        this.type = type;
        this.rating = rating;
        this.tags = tags;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getProblemsetName() {
        return problemsetName;
    }

    public void setProblemsetName(String problemsetName) {
        this.problemsetName = problemsetName;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
