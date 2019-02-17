package com.example.metinatac.speakout;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class LaunchActivity extends AppCompatActivity {
    private GoogleSignInClient mGoogleSingInclient;
    private FirebaseAuth mAuth;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSingInclient = GoogleSignIn.getClient(this, gso);


        setContentView(R.layout.activity_launch);

        handler = new Handler();
        handler.postDelayed(new Runnable() {


            @Override
            public void run() {



                //Toast.makeText(LaunchActivity.this, mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();

                if (mAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(LaunchActivity.this, LogInActivity.class);
                    startActivity(intent);

                    finish();

                } else {
                    Intent intent = new Intent(LaunchActivity.this, HomeActivity.class);
                    startActivity(intent);

                    finish();
                }




            }
        }, 1500);

    }


}



