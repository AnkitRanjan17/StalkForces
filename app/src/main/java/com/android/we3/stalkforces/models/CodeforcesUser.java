package com.android.we3.stalkforces.models;

import com.google.gson.annotations.SerializedName;

public class CodeforcesUser {

    @SerializedName("handle")
    private String handle;
    @SerializedName("email")
    private String email;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("country")
    private String country;
    @SerializedName("city")
    private String city;
    @SerializedName("organization")
    private String organization;
    @SerializedName("contribution")
    private Integer contribution;
    @SerializedName("rank")
    private String rank;
    @SerializedName("rating")
    private Integer rating;
    @SerializedName("maxRank")
    private String maxRank;
    @SerializedName("maxRating")
    private Integer maxRating;
    @SerializedName("lastOnlineTimeSeconds")
    private Integer lastOnlineTimeSeconds;
    @SerializedName("registrationTimeSeconds")
    private Integer registrationTimeSeconds;
    @SerializedName("friendOfCount")
    private Integer friendOfCount;
    @SerializedName("avatar")
    private String avatar;

    public CodeforcesUser(){}

    public CodeforcesUser(String handle, String email, String firstName, String lastName, String country, String city,
                          String organization, Integer contribution, String rank, Integer rating, String maxRank,
                          Integer maxRating, Integer lastOnlineTimeSeconds, Integer registrationTimeSeconds, Integer friendOfCount,
                          String avatar) {
        this.handle = handle;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.organization = organization;
        this.contribution = contribution;
        this.rank = rank;
        this.rating = rating;
        this.maxRank = maxRank;
        this.maxRating = maxRating;
        this.lastOnlineTimeSeconds = lastOnlineTimeSeconds;
        this.registrationTimeSeconds = registrationTimeSeconds;
        this.friendOfCount = friendOfCount;
        this.avatar = avatar;
    }

    public String getHandle() {
        return handle;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getOrganization() {
        return organization;
    }

    public Integer getContribution() {
        return contribution;
    }

    public String getRank() {
        return rank;
    }

    public Integer getRating() {
        return rating;
    }

    public String getMaxRank() {
        return maxRank;
    }

    public Integer getMaxRating() {
        return maxRating;
    }

    public Integer getLastOnlineTimeSeconds() {
        return lastOnlineTimeSeconds;
    }

    public Integer getRegistrationTimeSeconds() {
        return registrationTimeSeconds;
    }

    public Integer getFriendOfCount() {
        return friendOfCount;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setContribution(Integer contribution) {
        this.contribution = contribution;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public void setMaxRank(String maxRank) {
        this.maxRank = maxRank;
    }

    public void setMaxRating(Integer maxRating) {
        this.maxRating = maxRating;
    }

    public void setLastOnlineTimeSeconds(Integer lastOnlineTimeSeconds) {
        this.lastOnlineTimeSeconds = lastOnlineTimeSeconds;
    }

    public void setRegistrationTimeSeconds(Integer registrationTimeSeconds) {
        this.registrationTimeSeconds = registrationTimeSeconds;
    }

    public void setFriendOfCount(Integer friendOfCount) {
        this.friendOfCount = friendOfCount;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
