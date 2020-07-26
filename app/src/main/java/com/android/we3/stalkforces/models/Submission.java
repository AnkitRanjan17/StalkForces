package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

public class Submission {

    @SerializedName("id")
    private Integer id;

    @SerializedName("contestId")
    private Integer contestId;

    @SerializedName("creationTimeSeconds")
    private Long creationTime;

    @SerializedName("problem")
    private Problem problem;

    @SerializedName("programmingLanguage")
    private String language;

    @SerializedName("verdict")
    private String verdict;

    @SerializedName("testset")
    private String testset;

    @SerializedName("passedTestCount")
    private Integer passedTestCount;

    @SerializedName("timeConsumedMillis")
    private Integer timeConsumedMillis;

    @SerializedName("memoryConsumedBytes")
    private Integer memoryConsumedBytes;

    public Submission(Integer id, Integer contestId, Long creationTime, Problem problem, String language, String verdict, String testset, Integer passedTestCount, Integer timeConsumedMillis, Integer memoryConsumedBytes) {
        this.id = id;
        this.contestId = contestId;
        this.creationTime = creationTime;
        this.problem = problem;
        this.language = language;
        this.verdict = verdict;
        this.testset = testset;
        this.passedTestCount = passedTestCount;
        this.timeConsumedMillis = timeConsumedMillis;
        this.memoryConsumedBytes = memoryConsumedBytes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public String getTestset() {
        return testset;
    }

    public void setTestset(String testset) {
        this.testset = testset;
    }

    public Integer getPassedTestCount() {
        return passedTestCount;
    }

    public void setPassedTestCount(Integer passedTestCount) {
        this.passedTestCount = passedTestCount;
    }

    public Integer getTimeConsumedMillis() {
        return timeConsumedMillis;
    }

    public void setTimeConsumedMillis(Integer timeConsumedMillis) {
        this.timeConsumedMillis = timeConsumedMillis;
    }

    public Integer getMemoryConsumedBytes() {
        return memoryConsumedBytes;
    }

    public void setMemoryConsumedBytes(Integer memoryConsumedBytes) {
        this.memoryConsumedBytes = memoryConsumedBytes;
    }
}
