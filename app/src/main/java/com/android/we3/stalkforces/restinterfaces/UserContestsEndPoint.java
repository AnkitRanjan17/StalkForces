package com.android.we3.stalkforces.restinterfaces;

import com.android.we3.stalkforces.models.UserContestsResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserContestsEndPoint {

    @GET("user.rating")
    Call<UserContestsResult> getUserContests(
            @Query("handle") String handle
    );

}
