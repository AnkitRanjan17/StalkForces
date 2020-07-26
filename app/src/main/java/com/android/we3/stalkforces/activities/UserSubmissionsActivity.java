package com.android.we3.stalkforces.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.UserSubmissionsAdapter;
import com.android.we3.stalkforces.apiservice.APIClient;
import com.android.we3.stalkforces.models.Submission;
import com.android.we3.stalkforces.models.UserSubmissionsResult;
import com.android.we3.stalkforces.restinterfaces.UserSubmissionsEndPoint;

import java.util.ArrayList;
import java.util.List;

public class UserSubmissionsActivity extends AppCompatActivity implements UserSubmissionsAdapter.OnSubmissionClickListener {

    //private TextView tv1;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private UserSubmissionsAdapter submissionsAdapter;
    private ArrayList<Submission> submissionList;
    private EditText pageInput, countInput;
    private String handle;
    private TextView handleName;
    private static final String TAG = "UserSubmissionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_submissions);
        handle = getIntent().getStringExtra("userHandle");
        //handle = "Aditya_13";
        //tv1 = (TextView)findViewById(R.id.tv1);
        Log.i("kyaaaya",handle);

        handleName = (TextView)findViewById(R.id.userNameSubmissionTextView);
        handleName.setText(handle);
        pageInput = (EditText)findViewById(R.id.pageIndexSubmissionEditText);
        countInput = (EditText)findViewById(R.id.countSubmissionEditText);
        recyclerView = (RecyclerView)findViewById(R.id.userSubmissionsRecyclerView);
        layoutManager = new LinearLayoutManager(UserSubmissionsActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        submissionList = new ArrayList<>();
        submissionsAdapter = new UserSubmissionsAdapter(submissionList,this);
        recyclerView.setAdapter(submissionsAdapter);

        loadSubmissions(handle, 1, 10);
    }

    public void fetchSubmissions(View view) {

        int page = Integer.parseInt(pageInput.getText().toString());
        int count = Integer.parseInt(countInput.getText().toString());

        int index = count*(page-1) + 1;
        loadSubmissions(handle, index, count);
    }

    private void loadSubmissions(String handle, int index, int count) {

        final UserSubmissionsEndPoint submissions = APIClient.getClient().create(UserSubmissionsEndPoint.class);

        Call<UserSubmissionsResult> userSubmissionsResultCall = submissions.getUserSubmissions(handle,index,count);
        Log.i("aaya","fun");

        userSubmissionsResultCall.enqueue(new Callback<UserSubmissionsResult>() {
            @Override
            public void onResponse(Call<UserSubmissionsResult> call, Response<UserSubmissionsResult> response) {
                Log.i("aaya", "bahar");
                if (response.isSuccessful() && response.body().getStatus().equals("OK")) {
                    Log.i("aaya", "andar");
                    //response.body().
                    submissionList.clear();
                    //submissionList = response.body().getResult();
                    submissionList.addAll(response.body().getResult());
                    Log.i("aaya", "listMila " + submissionList.size());
                    for (Submission s : submissionList) {
                        Log.i("aaya", s.getVerdict() + " " + s.getProblem().getIndex());
                        //tv1.append(s.getVerdict()+"\n");
                    }
                    submissionsAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<UserSubmissionsResult> call, Throwable t) {

            }
        });

    }

    @Override
    public void onSubmissionClick(int pos) {
        final Submission submission = submissionList.get(pos);
        Log.i("huaa", "onSubmissionClick: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You will be redirected to the submission via your browser.")
                .setTitle("Do you want to view the submission?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Todo
                String id = submission.getId().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Log.i(TAG, "onSubmissionClick: "+id);
                String url = "https://codeforces.com/contest/"+submission.getContestId()+"/submission/"+id;
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
    public void onContestClick(int pos) {
        final Submission submission = submissionList.get(pos);
        Log.i("huaa", "onSubmissionClick: ");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You will be redirected to the problem via your browser.")
                .setTitle("Do you want to view the problem?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Todo
                String id = submission.getProblem().getIndex();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Log.i(TAG, "onSubmissionClick: "+id);
                String url = "https://codeforces.com/contest/"+submission.getContestId()+"/problem/"+id;
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
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
