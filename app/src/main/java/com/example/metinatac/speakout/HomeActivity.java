package com.example.metinatac.speakout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;
import com.squareup.picasso.Picasso;

import java.net.URL;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private GoogleSignInClient mGoogleSingInclient;
    private FirebaseAuth mAuth;
    Button logout;
    ImageView drawerPb;
    TextView userName;

    MediaPlayer mediaPlayer = new MediaPlayer();

    static FirebaseUser currentUserAngemeldet;
    static boolean emailLogin;
    static Nutzer currentNutzer;

    ImageView bellBtn;
    ImageView msgBtn;

    View headerView;


    private static final String TAG = "SignInACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        bellBtn = findViewById(R.id.bellbtn);
        msgBtn = findViewById(R.id.msgbtn);
        setSupportActionBar(toolbar);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bellsound);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSingInclient = GoogleSignIn.getClient(this, gso);


        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUserAngemeldet = currentUser;
        NavigationView navigationView = findViewById(R.id.nav_view);


        headerView = navigationView.getHeaderView(0);
        userName = headerView.findViewById(R.id.nametxt);
        drawerPb = headerView.findViewById(R.id.drawerpb);

        bellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.bellsound);

                mediaPlayer.start();

                Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                bellBtn.startAnimation(shake);

            }
        });


        msgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Animation pop = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop);
                msgBtn.startAnimation(pop);

            }
        });



            nutzerFinden();


            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                    new HomeFragment()).commit();

            navigationView.setCheckedItem(R.id.home);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();


            navigationView.setNavigationItemSelectedListener(this);





           /* Uri profilePicture = currentUser.getPhotoUrl();

            Picasso.get().load(profilePicture).transform(new CropCircleTransformation()).into(drawerPb);
*/


    }




    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.seekTo(0);
        mediaPlayer.release();

    }

    private void nutzerFinden() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(currentUserAngemeldet.getUid()).child("Daten");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                currentNutzer = dataSnapshot.getValue(Nutzer.class);
                userName.setText(currentNutzer.getUsername());

                if(currentNutzer.getPhotourl().equals("")) {




                } else {

                    Picasso.get().load(currentNutzer.getPhotourl()).transform(new CropCircleTransformation()).into(drawerPb);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    protected void onStart() {

        super.onStart();

        NavigationView nav = findViewById(R.id.nav_view);

        logout = nav.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                finish();
                startActivity(new Intent(HomeActivity.this, LogInActivity.class));
            }
        });
        /*   String userName = currentUser.getDisplayName();
            Toast.makeText(this, "Willkommen "+userName, Toast.LENGTH_SHORT).show();
            String username = currentUser.getDisplayName();
*/

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profil) {
            //Was passiert wenn man PROFIL im Drawer drückt
            // Handle the camera action


            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                    new ProfilFragment()).commit();


        } else if (id == R.id.suchen) {
            //Was passiert wenn man SUCHEN im Drawer drückt

            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                    new SuchenFragment()).commit();


        } else if (id == R.id.einstellungen) {
            //Was passiert wenn man EINSTELLUNGEN im Drawer drückt

            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                    new EinstellungenFragment()).commit();


        } else if (id == R.id.geschichten) {
            //Was passiert wenn man EINSTELLUNGEN im Drawer drückt

            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                    new GeschichtenFragment()).commit();


        } else if (id == R.id.home) {
            //Was passiert wenn man HOME im Drawer drückt

            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                    new HomeFragment()).commit();


        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


}
