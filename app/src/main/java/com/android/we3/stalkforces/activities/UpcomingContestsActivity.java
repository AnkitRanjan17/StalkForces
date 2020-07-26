package com.android.we3.stalkforces.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.UpcomingContestsAdapter;
import com.android.we3.stalkforces.adapters.UserFavouritesAdapter;
import com.android.we3.stalkforces.apiservice.APIClient;
import com.android.we3.stalkforces.models.UpcomingContests;
import com.android.we3.stalkforces.models.UpcomingContestsResult;
import com.android.we3.stalkforces.restinterfaces.UpcomingContestsEndPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpcomingContestsActivity extends AppCompatActivity implements UpcomingContestsAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private UpcomingContestsAdapter upcomingContestsAdapter;
    private ArrayList<UpcomingContests> upcomingContestsList;
    private ArrayList<UpcomingContests> upcomingContestsListFUll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_contests);

        recyclerView = findViewById(R.id.upcomingContestRecyclerView);
        layoutManager = new LinearLayoutManager(UpcomingContestsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        upcomingContestsList = new ArrayList<>();
        upcomingContestsListFUll = new ArrayList<>();
        upcomingContestsAdapter = new UpcomingContestsAdapter(upcomingContestsList,this);
        recyclerView.setAdapter(upcomingContestsAdapter);

        loadUpcomingContests();
    }

    private void loadUpcomingContests()
    {
        final UpcomingContestsEndPoint upcomingContestsEndPoint = APIClient.getClient().create(UpcomingContestsEndPoint.class);

        Call<UpcomingContestsResult> upcomingContestsResultCall = upcomingContestsEndPoint.getUpcmoingContest("false");

        upcomingContestsResultCall.enqueue(new Callback<UpcomingContestsResult>() {
            @Override
            public void onResponse(Call<UpcomingContestsResult> call, Response<UpcomingContestsResult> response) {

                if(response.isSuccessful() && response.body().getStatus().equals("OK"))
                {
                    upcomingContestsList.clear();
                    upcomingContestsListFUll.clear();
                    UpcomingContestsResult upcomingContestsResult = response.body();
                    upcomingContestsListFUll = (ArrayList<UpcomingContests>) upcomingContestsResult.getUpcomingContestsList();
                    for(UpcomingContests upcomingContests : upcomingContestsListFUll)
                    {
                        if(upcomingContests.getPhase().equals("BEFORE"))
                        {
                            upcomingContestsList.add(upcomingContests);
                        }
                        else
                        {
                            break;
                        }
                    }
                    Collections.reverse(upcomingContestsList);
                    upcomingContestsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UpcomingContestsResult> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(int pos)
    {
        //Toast.makeText(UpcomingContestsActivity.this,Integer.toString(pos),Toast.LENGTH_SHORT).show();
        UpcomingContests upcomingContests = upcomingContestsList.get(pos);
        Intent intent = new Intent(UpcomingContestsActivity.this,ViewReminderActivity.class);
        intent.putExtra("contestId",upcomingContests.getId().toString());
        intent.putExtra("name",upcomingContests.getName());
        intent.putExtra("content",upcomingContests.getStartTimeSeconds().toString());
        String time = ((TextView) recyclerView.findViewHolderForAdapterPosition(pos).itemView.findViewById(R.id.contestTimeUpcomngContestTextView)).getText().toString();
        intent.putExtra("scheduleTime",time);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
