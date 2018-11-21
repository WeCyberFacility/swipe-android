package com.example.metinatac.speakout;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GoogleUserRegisterActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSingInclient;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_user_register);


        mAuth = FirebaseAuth.getInstance();

        //SignInButton signInButton = findViewById(R.id.googleBtn);
        //signInButton.setSize(SignInButton.SIZE_WIDE);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSingInclient = GoogleSignIn.getClient(this, gso);







    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        HomeActivity.emailLogin = false;

        // Google sign out
        mGoogleSingInclient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }



    @Override
    public void onBackPressed() {
        signOut();
        Intent myIntent = new Intent(GoogleUserRegisterActivity.this, LogInActivity.class);
        startActivity(myIntent);
        finish();

    }






}
