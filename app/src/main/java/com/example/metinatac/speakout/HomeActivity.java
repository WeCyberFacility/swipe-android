package com.example.metinatac.speakout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class HomeActivity extends AppCompatActivity {
private int at =0;
    private GoogleSignInClient mGoogleSingInclient;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private int backButtonTabs=0;
    private static final String TAG = "SignInACTIVITY";
    Toolbar likeActionBar;
    long firstMill;
    long secondMill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        likeActionBar.findViewById(R.id.homeToolBar);


        db = FirebaseFirestore.getInstance();


        setContentView(R.layout.activity_home);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSingInclient = GoogleSignIn.getClient(this, gso);

        Task<Void> voidTask = mGoogleSingInclient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });







    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSingInclient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSingInclient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
    public long actualMilliseconds(){


        return  System.currentTimeMillis();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {






        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backButtonTabs++;
            firstMill = actualMilliseconds();
            long a = actualMilliseconds();
            String b = Long.toString(a);
            Log.d(TAG, b);





            if (backButtonTabs == 2) {
                secondMill = actualMilliseconds();



                backButtonTabs = 0;
                signOut();
                finish();
                startActivity(new Intent(HomeActivity.this, LogInActivity.class));
                return true;
            }

            Toast.makeText(this, "Erneut drücken um abzumelden.", Toast.LENGTH_SHORT).show();


            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    backButtonTabs--;
                }
            }, 2000);


            return true;

        }

        return super.onKeyDown(keyCode, event);



    }

}
