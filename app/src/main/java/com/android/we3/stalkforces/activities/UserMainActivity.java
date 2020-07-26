package com.android.we3.stalkforces.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.transition.CircularPropagation;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.apiservice.APIClient;
import com.android.we3.stalkforces.models.Favourites;
import com.android.we3.stalkforces.models.UpcomingContests;
import com.android.we3.stalkforces.models.User;
import com.android.we3.stalkforces.restinterfaces.CodeforcesUserEndPoints;
import com.android.we3.stalkforces.models.CodeforcesUser;
import com.android.we3.stalkforces.models.CodeforcesUserJson;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserMainActivity extends AppCompatActivity {

    private ImageView userImage;
    private TextView userRank;
    private TextView userName;
    private TextView userHandle;
    private TextView userLocation;
    private TextView userInstitute;
    private TextView userContribution;
    private TextView userRating;
    private TextView userFriends;
    private TextView userEmailId;
    private TextView userLastVisit;
    private TextView userRegisteredDate;
    private LinearLayout searchUserHandleLinearLayout;
    private EditText searchUserHandleEditText;

    private String searchHandleId;

    private FirebaseUser currentUserLoggedIn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String userId;
    private String userHandleId;
    private int checkMenuOption;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        userImage = findViewById(R.id.userImageView);
        userRank = findViewById(R.id.userRankTextView);
        userName = findViewById(R.id.userNameTextView);
        userHandle = findViewById(R.id.userHandleTextView);
        userLocation = findViewById(R.id.userLocationTextView);
        userInstitute = findViewById(R.id.userInstitueTextView);
        userContribution = findViewById(R.id.userContributionTextView);
        userRating = findViewById(R.id.userContestRatingTextView);
        userFriends = findViewById(R.id.userFriendTextView);
        userEmailId = findViewById(R.id.userEmailIdTextView);
        userLastVisit = findViewById(R.id.userLastVisitTextView);
        userRegisteredDate = findViewById(R.id.userRegiteredTimeTextView);
        searchUserHandleLinearLayout = findViewById(R.id.searchHandleLinearLayout);
        searchUserHandleEditText = findViewById(R.id.searchHandleEdtText);

        currentUserLoggedIn = FirebaseAuth.getInstance().getCurrentUser();
        userId = currentUserLoggedIn.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        checkMenuOption = 0;

        //listener to get user handle of logged in user
        firebaseDatabase.getReference("/users/"+userId).orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user==null)
                    Toast.makeText(UserMainActivity.this,"User is null",Toast.LENGTH_SHORT).show();
                userHandleId = user.getHandle().toLowerCase();
                searchHandleId = getIntent().getStringExtra("handle").toLowerCase();
                setMenuOption();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //set menu depending on the current handle on the page
    private void setMenuOption()
    {

        progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        if(searchHandleId.equals(userHandleId))
        {
            checkMenuOption = 0;
            loadUserData();
        }
        else {
            //listenet to check whether the user handle added to favourites or not
            firebaseDatabase.getReference("/users/" + userId + "/favourites/").orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HashMap<String, Favourites> checkUserFavourites = (HashMap<String, Favourites>) snapshot.getValue();
                    if (checkUserFavourites!=null && checkUserFavourites.containsKey(searchHandleId)) {
                        checkMenuOption = 1;
                        loadUserData();
                    } else {
                        checkMenuOption = 2;
                        loadUserData();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                    Toast.makeText(UserMainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //load the data of user from codeforces
    private void loadUserData() {



        final CodeforcesUserEndPoints apiService = APIClient.getClient().create(CodeforcesUserEndPoints.class);

        String handles = "";
        //handles.add(searchHandleId);
        handles += searchHandleId;
        Log.i("aaya", handles);
        Call<CodeforcesUserJson> call = apiService.getUser(handles);
        call.enqueue(new Callback<CodeforcesUserJson>() {
            @Override
            public void onResponse(Call<CodeforcesUserJson> call, Response<CodeforcesUserJson> response) {


                String s;

                if(response.isSuccessful()) {

                    if (response.body().getStatus().equals("FAILED")) {

                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.body().getComment(), Toast.LENGTH_SHORT).show();

                        //startActivity(new Intent(UserMainActivity.this,RegisterActivity.class));
                        return;
                    }
                    List<CodeforcesUser> codeforcesUser = (List<CodeforcesUser>) response.body().getResult();
                    for (CodeforcesUser user : codeforcesUser) {


                        GlideApp.with(UserMainActivity.this)
                                .load("https:/" + user.getAvatar())
                                .apply(RequestOptions.skipMemoryCacheOf(true))
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                .apply(new RequestOptions()
                                        .fitCenter()
                                        .format(DecodeFormat.PREFER_ARGB_8888)
                                        .override(Target.SIZE_ORIGINAL))
                                .into(userImage);


                        if (user.getRank() == null) {
                            s = "Unrated";
                            userRank.setText(s);
                            userRank.setTextColor(Color.BLACK);
                        }
                        else {
                            userRank.setText(user.getRank());
                        }
                        userHandle.setText(user.getHandle());
                        userHandle.setTextColor(Color.BLACK);
                        if (user.getLastName() == null && user.getFirstName() == null) {
                            s = "Name : Not Visible";
                            userName.setText(s);
                        }
                        else {
                            s = "Name : ";
                            if(user.getFirstName()!=null)
                                s+=user.getFirstName()+" ";
                            if(user.getLastName()!=null)
                                s+=user.getLastName();
                            userName.setText(s);
                        }
                        if (user.getCity() == null && user.getCountry() == null) {
                            s = "Place : Not Visible";
                            userLocation.setText(s);
                        }
                        else {
                            s = "Place : ";
                            if(user.getCity()!=null)
                                s+=user.getCity()+", ";
                            if(user.getCountry()!=null)
                                s+=user.getCountry();
                            userLocation.setText(s);
                        }
                        if (user.getOrganization() == null) {
                            s = "Organization : Not Visible";
                            userInstitute.setText(s);
                        }
                        else {
                            s = "Organization : " + user.getOrganization();
                            userInstitute.setText(s);
                        }
                        if (user.getRating() == null) {
                            s = "Contest Rating : Not Rated Yet";
                            userRating.setText(s);
                        }
                        else {
                            s = "Contest Rating : " + user.getRating() + " (max : " + user.getMaxRank() + ", " + user.getMaxRating() + ")";
                            userRating.setText(s);
                            int rating = user.getRating();
                            if (rating <= 1199) {
                                userRank.setTextColor(Color.GRAY);
                                userHandle.setTextColor(Color.GRAY);
                            } else if (rating <= 1399) {
                                userRank.setTextColor(Color.rgb(34, 139, 34));
                                userHandle.setTextColor(Color.rgb(34, 139, 34));
                            } else if (rating <= 1599) {
                                userRank.setTextColor(Color.rgb(102, 205, 170));
                                userHandle.setTextColor(Color.rgb(102, 205, 170));
                            } else if (rating <= 1899) {
                                userRank.setTextColor(Color.BLUE);
                                userHandle.setTextColor(Color.BLUE);
                            } else if (rating <= 2099) {
                                userRank.setTextColor(Color.rgb(234, 33, 200));
                                userHandle.setTextColor(Color.rgb(234, 33, 200));
                            } else if (rating <= 2399) {
                                userRank.setTextColor(Color.rgb(255, 165, 0));
                                userHandle.setTextColor(Color.rgb(255, 165, 0));
                            } else if (rating <= 2999) {
                                userRank.setTextColor(Color.RED);
                                userHandle.setTextColor(Color.RED);
                            } else if (rating >= 3000) {
                                String rank = user.getRank();
                                int l = rank.length();
                                Spannable word = new SpannableString(Character.toString(rank.charAt(0)));

                                word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                userRank.setText(word);
                                Spannable wordTwo = new SpannableString(rank.substring(1, l));

                                wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                userRank.append(wordTwo);
                                rank = user.getHandle();
                                l = rank.length();
                                word = new SpannableString(Character.toString(rank.charAt(0)));

                                word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                                userHandle.setText(word);
                                wordTwo = new SpannableString(rank.substring(1, l));

                                wordTwo.setSpan(new ForegroundColorSpan(Color.RED), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                userHandle.append(wordTwo);
                            }
                        }
                        s = "Contribution : " + user.getContribution().toString();
                        userContribution.setText(s);
                        s = "Friend of : " + user.getFriendOfCount().toString() + " users";
                        userFriends.setText(s);

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                        long time = timestamp.getTime() / 1000 - (long) user.getLastOnlineTimeSeconds();

                        if (time == 0) {
                            s = "Last Visit : Online Now";
                            userLastVisit.setText(s);
                        } else {
                            s = "Last Visit : ";
                            if (time / (long) 63072000 != 0) {
                                s += (time / (long) 31536000) + " years ago";
                            } else if (time / (long) 2592000 != 0) {
                                s += (time / (long) 2592000) + " months ago";
                            } else if (time / (long) 86400 != 0) {
                                s += (time / (long) 86400) + " days ago";
                            } else if (time / (long) 3600 != 0) {
                                s += (time / (long) 3600) + " hours ago";
                            } else if (time / (long) 60 != 0) {
                                s += (time / (long) 60) + " minutes ago";
                            } else {
                                s += "Online Now";
                            }
                            userLastVisit.setText(s);
                        }

                        time = timestamp.getTime() / 1000 - (long) user.getRegistrationTimeSeconds();

                        if (time == 0) {
                            s = "Registered : Just Now";
                            userLastVisit.setText(s);
                        } else {
                            s = "Registered : ";
                            if (time / (long) 63072000 != 0) {
                                s += (time / (long) 31536000) + " years ago";
                            } else if (time / (long) 2592000 != 0) {
                                s += (time / (long) 2592000) + " months ago";
                            } else if (time / (long) 86400 != 0) {
                                s += (time / (long) 86400) + " days ago";
                            } else if (time / (long) 3600 != 0) {
                                s += (time / (long) 3600) + " hours ago";
                            } else if (time / (long) 60 != 0) {
                                s += (time / (long) 60) + " minutes ago";
                            } else {
                                s += "Just Now";
                            }
                            userRegisteredDate.setText(s);
                        }
                        if (user.getEmail() == null) {
                            s = "E-mail id : Not Visible";
                            userEmailId.setText(s);
                        } else {
                            s = "E-mail id : " + user.getEmail();
                            userEmailId.setText(s);
                        }
                    }
                    progressDialog.dismiss();
                }
                else
                {

                    searchHandleId = userHandle.getText().toString();
                    progressDialog.dismiss();
                    Toast.makeText(UserMainActivity.this,"User Not Found",Toast.LENGTH_SHORT).show();
                    setMenuOption();
                }
            }

            @Override
            public void onFailure(Call<CodeforcesUserJson> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //adding menu to activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return true;
    }

    //selecting menu depending on logged in user and searched user
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        MenuInflater inflater = getMenuInflater();

        if (checkMenuOption==0) {
            inflater.inflate(R.menu.menu_user, menu);
        }
        else  if(checkMenuOption==1){
            inflater.inflate(R.menu.menu_otheruser_f, menu);
        }
        else if(checkMenuOption==2){
            inflater.inflate(R.menu.menu_otheruser_uf, menu);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    //action performed on menu onClick
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.searchHandle:
                searchUserHandleLinearLayout.setVisibility(View.VISIBLE);
                return true;
            case R.id.contests:
                Intent intentContest = new Intent(UserMainActivity.this,UserContestsActivity.class);
                intentContest.putExtra("userHandle",searchHandleId);
                startActivity(intentContest);
                return true;
            case R.id.submissions:
                Intent intentSubmissions = new Intent(UserMainActivity.this,UserSubmissionsActivity.class);
                intentSubmissions.putExtra("userHandle",searchHandleId);
                startActivity(intentSubmissions);
                return true;
            case R.id.favourites:
                Intent intentFavourites = new Intent(UserMainActivity.this,UserFavouritesActivity.class);
                intentFavourites.putExtra("userHandleId",userId);
                startActivity(intentFavourites);
                return true;
            case R.id.addFavourites:
                addToFavourites();
                return true;
            case R.id.removeFavourites:
                removeFromFavourites();
                return true;
            case R.id.upcomingContest:
                startActivity(new Intent(UserMainActivity.this, UpcomingContestsActivity.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserMainActivity.this, RegisterActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //get handle of the searched user from textview
    public void searchHandle(View view) {
        searchHandleId = searchUserHandleEditText.getText().toString().toLowerCase();
        setMenuOption();
        searchUserHandleLinearLayout.setVisibility(View.GONE);
        searchUserHandleEditText.setText("");
    }

    //add the current shown handle to favourites of logged in user
    private void addToFavourites() {

        String s;
        int l;
        s = userName.getText().toString();
        l = s.length();
        String name = s.substring(7, l);
        String userHandles = userHandle.getText().toString();
        s = userRating.getText().toString();
        l = s.length();
        String rating = s.substring(17, 21);
        Favourites favouriteUser = new Favourites(name, userHandles, rating);
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId + "/favourites/"+userHandles.toLowerCase(), favouriteUser.toMap());
        firebaseDatabase.getReference().updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Write was successful!
                // ...
                setMenuOption();
                Toast.makeText(UserMainActivity.this, "Added to Favourites", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
                Toast.makeText(UserMainActivity.this, "Problem Occured", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //delete the current shown handle from user favourites
    private  void  removeFromFavourites()
    {
        firebaseDatabase.getReference("/users/"+userId+"/favourites/"+searchHandleId).setValue(null);
        setMenuOption();
        Toast.makeText(UserMainActivity.this,"Removed from Favourite",Toast.LENGTH_SHORT).show();
    }

    //action performed on pressing back button
    @Override
    public void onBackPressed() {

        if(getIntent().getStringExtra("FavouritePage").equals("YES"))
        {
            finish();
        }
        else if(checkMenuOption==0)
        {
            finishAffinity();
        }
        else
        {
            searchHandleId = userHandleId;
            setMenuOption();
        }
    }

}
