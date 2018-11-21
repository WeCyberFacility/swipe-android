package com.example.metinatac.speakout;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GoogleUserRegisterActivity extends AppCompatActivity {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Nutzer");


    private GoogleSignInClient mGoogleSingInclient;
    private FirebaseAuth mAuth;
   private Button weiter;
   private EditText uname ;
   FirebaseUser user;
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
        user = mAuth.getCurrentUser();
        weiter = findViewById(R.id.weiterBtn);
        uname = findViewById(R.id.googleUserName);

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


    public void createNewGoogleUser(View V){
        String Name  = user.getDisplayName().trim();
        String Nachname = "";
        String ID =user.getUid();
        String userNAme = uname.getText().toString().trim();
        String pw ="GoogleAuth".trim();
        String email = user.getEmail().trim();

        Nutzer neuerNutzer = new Nutzer(ID, Name, Nachname, userNAme,pw, email, 0);
        myRef.child(mAuth.getCurrentUser().getUid()).child("Daten").setValue(neuerNutzer);

    }








    @Override
    public void onBackPressed() {
        signOut();
        Intent myIntent = new Intent(GoogleUserRegisterActivity.this, LogInActivity.class);
        startActivity(myIntent);
        finish();

    }









}
