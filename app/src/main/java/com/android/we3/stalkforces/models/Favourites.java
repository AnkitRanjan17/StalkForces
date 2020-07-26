package com.android.we3.stalkforces.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Favourites {

    private String name;
    private String handle;
    private String rating;

    public Favourites(){}

    public Favourites(String name, String handle, String rating) {
        this.name = name;
        this.handle = handle;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getHandle() {
        return handle;
    }

    public String getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        result.put("handle",handle);
        result.put("rating",rating);
        return result;
    }
}
