package com.android.we3.stalkforces.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.android.we3.stalkforces.apiservice.APIClient;
import com.android.we3.stalkforces.models.CodeforcesUserJson;
import com.android.we3.stalkforces.models.User;
import com.android.we3.stalkforces.restinterfaces.CodeforcesUserEndPoints;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private EditText inputEmail,inputPassword,inputHandle, inputCPassword;
    private TextView toggle;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mFirebaseDatabaseInstance;
    private DatabaseReference mFirebaseDatabase;
    private String userId;
    private String emailInput, password, email, handle, cPassword;
    private String uId;
    private ValueEventListener detailsListener;
    private DatabaseReference typeReference;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();

        setContentView(R.layout.activity_register);
        inputEmail=(EditText)findViewById(R.id.emailRegisterEditText);
        inputPassword=(EditText)findViewById(R.id.passwordRegisterEditText);
        inputHandle=(EditText)findViewById(R.id.nameRegisterEditText);
        inputCPassword = (EditText)findViewById(R.id.confirmPasswordRegisterEditText);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        toggle = (TextView)findViewById(R.id.showHidePasswordTextView);

        mFirebaseDatabaseInstance= FirebaseDatabase.getInstance();
    }

    // register user to firebase authentication
    public void onRegisterClicked(View view) {
        emailInput = inputEmail.getText().toString().trim();
        password = inputPassword.getText().toString().trim();
        handle = inputHandle.getText().toString();
        cPassword = inputCPassword.getText().toString();
        final String name = inputHandle.getText().toString().trim();
        if (TextUtils.isEmpty(emailInput)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter Password!", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short,enter minimum 6 characters!", Toast.LENGTH_LONG).show();
            return;
        }

        if (!cPassword.equals(password)) {
            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }
        final CodeforcesUserEndPoints cfUser = APIClient.getClient().create(CodeforcesUserEndPoints.class);

        String handles = ""+handle;
        Call<CodeforcesUserJson> call = cfUser.getUser(handles);

        call.enqueue(new Callback<CodeforcesUserJson>() {
            @Override
            public void onResponse(Call<CodeforcesUserJson> call, Response<CodeforcesUserJson> response) {
                if(response.isSuccessful() && response.body().getStatus().equals("OK")) {
                    register();
                    return;
                }
                else {
                    Toast.makeText(RegisterActivity.this, "No such handle found.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<CodeforcesUserJson> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Please Try again after some time.", Toast.LENGTH_LONG).show();
            }
        });
    }

    // register user to firebase auth
    private void register() {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailInput, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE  );
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            userId = user.getUid();
                            email = user.getEmail();
                            Map<String, Object> userUpdates = new HashMap<>();
                            final String handle = inputHandle.getText().toString();
                            User user1 = new User(userId,handle);
                            Map<String, Object> newUserValues = user1.toMap();
                            userUpdates.put("/users/"+userId,newUserValues);

                            mFirebaseDatabaseInstance.getReference().updateChildren(userUpdates).addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "Profile Created Successfully.", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(RegisterActivity.this, UserMainActivity.class);
                                            intent.putExtra("handle",handle);
                                            intent.putExtra("FavouritePage","NO");
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                            ).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "Failure, Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                });
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void onLoginClicked(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void togglePasswordShow(View view) {
        if(toggle.getText().toString().equals("Show Password")) {
            inputCPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            toggle.setText("Hide Password");
        }
        else if(toggle.getText().toString().equals("Hide Password")) {
            inputCPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            toggle.setText("Show Password");
        }
    }
}
