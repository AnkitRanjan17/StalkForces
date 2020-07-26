package com.android.we3.stalkforces.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.we3.stalkforces.R;
import com.android.we3.stalkforces.activities.RegisterActivity;
import com.android.we3.stalkforces.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    final private static String TAG = LoginActivity.class.getSimpleName();

    private EditText inputEmail, inputPassword;
    private TextView toggle;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String uId;
    private ValueEventListener detailsListener;
    private DatabaseReference typeReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inputEmail = (EditText) findViewById(R.id.emailLoginActivityEditText);
        inputPassword = (EditText) findViewById(R.id.passwordLoginActivityEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLoginActivityProgressBar);
        toggle = (TextView)findViewById(R.id.showHidePasswordLoginTextView);
        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
    }

    // verify details amd login user using firebase authentication
    public void loginButtonClicked(View view) {
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);


        // firebase authentication
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.INVISIBLE);
                        if (!task.isSuccessful()) {

                            if (password.length() < 6) {
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {

                            uId = mAuth.getCurrentUser().getUid();
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            typeReference = firebaseDatabase.getReference("/users/" + uId + "/handle");
                            detailsListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String handle = dataSnapshot.getValue(String.class);
                                    Intent intent = new Intent(LoginActivity.this, UserMainActivity.class);
                                    intent.putExtra("handle", handle);
                                    intent.putExtra("FavouritePage","NO");
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            };

                            typeReference.addListenerForSingleValueEvent(detailsListener);
                        }
                    }
                });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(detailsListener!=null)
            typeReference.removeEventListener(detailsListener);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void togglePasswordShow(View view) {
        if(toggle.getText().toString().equals("Show Password")) {
            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            toggle.setText("Hide Password");
        }
        else if(toggle.getText().toString().equals("Hide Password")) {
            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggle.setText("Show Password");
        }
    }

    public void onRegisterClicked(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
