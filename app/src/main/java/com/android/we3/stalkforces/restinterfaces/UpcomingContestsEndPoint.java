package com.android.we3.stalkforces.restinterfaces;

import com.android.we3.stalkforces.models.UpcomingContestsResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UpcomingContestsEndPoint {

    @GET("contest.list")
    Call<UpcomingContestsResult> getUpcmoingContest(@Query("gym") String trueORFalse);
}
