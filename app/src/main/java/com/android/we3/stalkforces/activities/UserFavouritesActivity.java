package com.android.we3.stalkforces.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.adapters.UserFavouritesAdapter;
import com.android.we3.stalkforces.apiservice.APIClient;
import com.android.we3.stalkforces.models.CodeforcesUser;
import com.android.we3.stalkforces.models.CodeforcesUserJson;
import com.android.we3.stalkforces.models.Favourites;
import com.android.we3.stalkforces.restinterfaces.CodeforcesUserEndPoints;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFavouritesActivity extends AppCompatActivity {
    private DatabaseReference favRef;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference nameRef;
    private ValueEventListener nameListener;
    private ChildEventListener favListener;
    private Query allFavourites;
    private Map<String,String> favouriteRatings;

    private ArrayList<Favourites> listOfFavourites;
    private String favouriteHandles;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private UserFavouritesAdapter userFavouritesAdapter;
    private String inputUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favourites);
        final FirebaseUser currentUserLoggedIn = FirebaseAuth.getInstance().getCurrentUser();

        favouriteRatings = new HashMap<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
//        inputUserId = currentUserLoggedIn.getUid();
//        inputUserId="5o5psK4k0TUCbbLTStDb5G5rjbn1";
//        Toast.makeText(this,inputUserId,Toast.LENGTH_SHORT).show();
        inputUserId = getIntent().getStringExtra("userHandleId");
        listOfFavourites = new ArrayList<Favourites>();
        favouriteHandles = "";
        recyclerView = (RecyclerView) findViewById(R.id.userFavouritesRecyclerView);
        linearLayoutManager = new LinearLayoutManager(UserFavouritesActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        userFavouritesAdapter = new UserFavouritesAdapter(listOfFavourites);
        recyclerView.setAdapter(userFavouritesAdapter);
        favRef = firebaseDatabase.getReference("/users/" + inputUserId + "/favourites");
        loadFavourites();
    }

    private void loadFavourites() {
        allFavourites = favRef.orderByChild("handle");
        favListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                final Favourites myFavourites=dataSnapshot.getValue(Favourites.class);
                listOfFavourites.add(myFavourites);
                if(favouriteHandles.length()>0) {
                    favouriteHandles += ";";
                }
                favouriteHandles += myFavourites.getHandle();
                favouriteRatings.put(myFavourites.getHandle(),myFavourites.getRating());
                recyclerView.setAdapter(new UserFavouritesAdapter(listOfFavourites, new UserFavouritesAdapter.OnItemClickListener() {
                    @Override public void onItemClick(Favourites favourites) {

                        String handle=favourites.getHandle();
                        Intent intent=new Intent(UserFavouritesActivity.this,UserMainActivity.class);
                        intent.putExtra("handle",handle);
                        intent.putExtra("FavouritePage","YES");
                        startActivity(intent);
                    }
                }));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };

        allFavourites.addChildEventListener(favListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_favourites, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refreshFavouritesMenu:
                refreshFavourites();
                return true;
            case R.id.logoutFavouritesMenu:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserFavouritesActivity.this, RegisterActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void refreshFavourites() {
        final CodeforcesUserEndPoints userEndPoints = APIClient.getClient().create(CodeforcesUserEndPoints.class);
        final Map<String, Object> updates = new HashMap<>();
        Call<CodeforcesUserJson> call = userEndPoints.getUser(favouriteHandles);
        call.enqueue(new Callback<CodeforcesUserJson>() {
            @Override
            public void onResponse(Call<CodeforcesUserJson> call, Response<CodeforcesUserJson> response) {
                if(response.isSuccessful() && response.body().getStatus().equals("OK")) {
                    List<CodeforcesUser> newUserRatings = response.body().getResult();
                    for(CodeforcesUser user : newUserRatings) {
                        Log.i("aaya",favouriteRatings.get(user.getHandle()) + " " + user.getRating().toString());
                        if(!favouriteRatings.get(user.getHandle()).equals(user.getRating().toString())) {
                            favouriteRatings.put(user.getHandle(),user.getRating().toString());
                            //favRef.child("/"+user.getHandle()+"/rating").setValue(user.getRating().toString());
                            updates.put("/"+user.getHandle()+"/rating",user.getRating().toString());
                        }
                    }
                    Log.i("aaya", updates.toString());
                    favRef.updateChildren(updates).addOnSuccessListener(
                            new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    listOfFavourites.clear();
                                    favouriteHandles = "";
                                    loadFavourites();
                                    Toast.makeText(UserFavouritesActivity.this, "Refreshed", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserFavouritesActivity.this, "Failure", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CodeforcesUserJson> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        allFavourites.removeEventListener(favListener);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
