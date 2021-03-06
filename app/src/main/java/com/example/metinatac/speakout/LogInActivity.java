package com.example.metinatac.speakout;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
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

public class LogInActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSingInclient;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;

    EditText emailtxt;
    EditText passwordtxt;
    Button loginBtn;
    TextView ErrorEmpty;
    TextView neuerAccountErstellenTXT;

    private boolean authGoogle = false;
    public static Nutzer eingeloggterNutzer;

    private FirebaseAuth mAuth;
    static boolean gefunden;

    private AlertDialog.Builder a;
    private Context mCtx;

    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "SignInACTIVITY";


    //hi
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        //Deklarationen der einzelnen XML Objekte

        neuerAccountErstellenTXT = findViewById(R.id.neuerAccountTxt);
        emailtxt = findViewById(R.id.emailEingabetxt);
        passwordtxt = findViewById(R.id.passwortEingabeTxt);
        loginBtn = findViewById(R.id.loginBtnEmail);
        ErrorEmpty = findViewById(R.id.ErrorEmptyFields);


        //Buttons:


        //
        neuerAccountErstellenTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LogInActivity.this, RegisterActivity.class));


            }
        });


        //Login Button für die Email Anmeldung!
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop);
                loginBtn.startAnimation(pop);
                anmelden();


            }
        });

        ImageButton signInButton = findViewById(R.id.googleBtn);


        mAuth = FirebaseAuth.getInstance();

        //SignInButton signInButton = findViewById(R.id.googleBtn);
        //signInButton.setSize(SignInButton.SIZE_WIDE);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        mGoogleSingInclient = GoogleSignIn.getClient(this, gso);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });


        Task<Void> voidTask = mGoogleSingInclient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }


    @Override
    protected void onStart() {
        GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);

        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // updateUI(currentUser);
        //  mGoogleApiClient.connect(); // <- Verursacht FEHLER (Crash)


        //startActivity(getIntent())


    }


    private void signIn() {

        Intent signInIntent = mGoogleSingInclient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                GoogleUserRegisterActivity.accountRefCopy = account;

                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }

        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebasAuthWithGoogle" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential: success");
                            // updateUI(user);

                            //   startActivity(getIntent());
                            authGoogle = true;
                            checkObExistiert();


                            Toast.makeText(getApplicationContext(), "Willkommen", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Auth failed!", Toast.LENGTH_SHORT).show();

                            Log.w(TAG, "signInWithCredential: failed", task.getException());


                            // updateUI(null);
                        }
                    }
                });

        Handler handler;
        handler = new Handler();
        handler.postDelayed(new Runnable() {


            @Override
            public void run() {

                if (authGoogle == false) {
                    Toast.makeText(getApplicationContext(), "Auth failed!", Toast.LENGTH_SHORT).show();
                    getAlert();

                }
            }
        }, 2000);


    }


    public void getAlert() {

        AlertDialog.Builder fehler = new AlertDialog.Builder(LogInActivity.this);
        fehler.setMessage("Benutzerkonto ist gesperrt!")


                .setNegativeButton("Schließen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        signOut();
                    }
                }).setCancelable(false);


        AlertDialog dialog = fehler.create();

        dialog.show();

    }


    public void checkObExistiert() {

        gefunden = false;

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Nutzer currentUser = ds.child("Daten").getValue(Nutzer.class);

                    if (currentUser.getId().equals(mAuth.getCurrentUser().getUid())) {


                        gefunden = true;

                        break;


                    } else {

                        continue;

                    }


                }

                if (gefunden == true) {


                    Intent myIntent = new Intent(LogInActivity.this, HomeActivity.class);
                    finish();
                    startActivity(myIntent);

                } else {

                    Intent myIntent = new Intent(LogInActivity.this, GoogleUserRegisterActivity.class);
                    finish();
                    startActivity(myIntent);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void handleSignInresult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            Intent myIntent = new Intent(LogInActivity.this, HomeActivity.class);
            startActivity(myIntent);
            finish();

        } else {

        }
    }


    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.d(TAG, "OnConnectionFailed:" + connectionResult);

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


    public void anmelden() {

        //Hi
        final String emailEingabe = emailtxt.getText().toString().trim();
        String passwortEingabe = passwordtxt.getText().toString().trim();

        Log.d(TAG, passwortEingabe);
        if (emailEingabe.equals("") || passwortEingabe.equals("")) {

            //   Toast.makeText(mCtx, "Bitte fülle alle Felder aus!", Toast.LENGTH_SHORT).show();

            ErrorEmpty.setText("*Bitte Felder ausfüllen!");
        } else {


            mAuth.signInWithEmailAndPassword(emailEingabe, passwortEingabe)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                //            Toast.makeText(mCtx, "Login Erfolgreich", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();

                                HomeActivity.emailLogin = true;
                                HomeActivity.currentUserAngemeldet = mAuth.getCurrentUser();

                                Intent myIntent = new Intent(LogInActivity.this, HomeActivity.class);
                                startActivity(myIntent);
                                finish();


                            } else {

                                ErrorEmpty.setText("*E-Mail oder Passwort falsch!");
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                //     Toast.makeText(mCtx, "Email oder Password falsch!",
                                //           Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });

        }


    }

}




