package com.android.we3.stalkforces.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String userId;
    private String handle;
    private HashMap<String,Favourites> favourites;

    public User(){}

    public User(String userId, String handle) {
        this.userId = userId;
        this.handle = handle;
        this.favourites = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public HashMap<String, Favourites> getFavourites() {
        return favourites;
    }

    public void setFavourites(HashMap<String, Favourites> favourites) {
        this.favourites = favourites;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId",userId);
        result.put("handle",handle);
        result.put("favourites",favourites);
        return result;
    }
}
