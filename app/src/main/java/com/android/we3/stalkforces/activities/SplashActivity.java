package com.android.we3.stalkforces.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.models.Submission;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private ValueEventListener handleListener;
    private String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null) {
            uId = auth.getCurrentUser().getUid();
            firebaseDatabase= FirebaseDatabase.getInstance();
            reference = firebaseDatabase.getReference("/users/"+uId+"/handle");
            handleListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String handle = snapshot.getValue(String.class);
                    Intent intent = new Intent(SplashActivity.this, UserMainActivity.class);
                    intent.putExtra("handle",handle);
                    intent.putExtra("FavouritePage","NO");
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            reference.addListenerForSingleValueEvent(handleListener);
        }
        else {
            startActivity(new Intent(SplashActivity.this,RegisterActivity.class));
            finish();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
