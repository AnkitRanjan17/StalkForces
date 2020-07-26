package com.android.we3.stalkforces.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.UserContestsAdapter;

import com.android.we3.stalkforces.apiservice.APIClient;
import com.android.we3.stalkforces.models.Contests;

import com.android.we3.stalkforces.models.UserContestsResult;

import com.android.we3.stalkforces.restinterfaces.UserContestsEndPoint;
import com.google.firebase.auth.FirebaseAuth;


import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserContestsActivity extends AppCompatActivity implements UserContestsAdapter.OnContestsClickListener{
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UserContestsAdapter contestsAdapter;
    private ArrayList<Contests> contestsList;
    private TextView user_id;
    private String searchHandle;
    public static int cSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_contests);
        recyclerView = (RecyclerView)findViewById(R.id.userContestsRecyclerView);
        layoutManager = new LinearLayoutManager(UserContestsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        contestsList = new ArrayList<>();
        contestsAdapter = new UserContestsAdapter(contestsList,this);
        recyclerView.setAdapter(contestsAdapter);
        user_id=(TextView)findViewById(R.id.contestUserHandleTextView);
        searchHandle = getIntent().getStringExtra("userHandle");
        loadContests();
    }
    private void loadContests()
    {
        final UserContestsEndPoint submissions = APIClient.getClient().create(UserContestsEndPoint.class);
        Call<UserContestsResult> userContestsResultCall = submissions.getUserContests(searchHandle);
        user_id.setText(searchHandle);
        userContestsResultCall.enqueue(new Callback<UserContestsResult>() {
            @Override
            public void onResponse(Call<UserContestsResult> call, Response<UserContestsResult> response) {
                if(response.isSuccessful() && response.body().getStatus().equals("OK")) {
                    contestsList.clear();
                    contestsList.addAll(response.body().getContestResult());
                    cSize=contestsList.size();
                    Collections.reverse(contestsList);
                    Log.i("TagIt", String.valueOf(cSize));
                    contestsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<UserContestsResult> call, Throwable t) {

            }
        });

    }
    @Override
    public void onContestNameClick(int pos) {
        final Contests contests = contestsList.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You will be redirected to the Contest via your browser.")
                .setTitle("Do you want to view the Contest?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Todo
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String url = "https://codeforces.com/contest/"+contests.getContestId();
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
