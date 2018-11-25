package com.example.metinatac.speakout;


import android.app.Service;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GoogleUserRegisterActivity extends AppCompatActivity {
// TODO: Wenn man die APP bei dieser Activity komplett schließt, soll der user, der nicht in der Datenbank eingetragen ist ausgeloggt werden.

    private GoogleSignInClient mGoogleSingInclient;
    private FirebaseAuth mAuth;

    private Button weiter;
    private EditText uname;
    FirebaseUser user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Nutzer");
    static GoogleSignInAccount accountRefCopy;

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


    public void createNewGoogleUser(View V) {
        String Name = user.getDisplayName().trim();
        String Nachname = "";
        String ID = user.getUid();
        String userNAme = uname.getText().toString().trim();
        String pw = "GoogleAuth".trim();
        String email = user.getEmail().trim();

        Nutzer neuerNutzer = new Nutzer(ID, Name, Nachname, userNAme, pw, email, 0);
        myRef.child(mAuth.getCurrentUser().getUid()).child("Daten").setValue(neuerNutzer);


        startActivity(new Intent(GoogleUserRegisterActivity.this, HomeActivity.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        signOut();
        Intent myIntent = new Intent(GoogleUserRegisterActivity.this, LogInActivity.class);
        startActivity(myIntent);

    }


    @Override
    public void onPause() {
super.onPause();

        if(!LogInActivity.gefunden)
         signOut();
     }
@Override
public void onResume()
{
super.onResume();
   if(mAuth.getCurrentUser()==null) {
       Intent myIntent = new Intent(GoogleUserRegisterActivity.this, LogInActivity.class);
       startActivity(myIntent);
       super.onResume();
       finish();
   }
}
}