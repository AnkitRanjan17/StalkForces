package com.android.we3.stalkforces.restinterfaces;

import com.android.we3.stalkforces.models.UserSubmissionsResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserSubmissionsEndPoint {

    @GET("user.status")
    Call<UserSubmissionsResult> getUserSubmissions(
            @Query("handle") String handle,
            @Query("from") Integer from,
            @Query("count") Integer count
    );
}
