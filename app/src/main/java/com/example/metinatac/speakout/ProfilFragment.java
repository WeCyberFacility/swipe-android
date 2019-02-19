package com.example.metinatac.speakout;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfilFragment extends Fragment {


    ImageView profilbild_PF;
    TextView name_PF;
    TextView follower_Zahl;
    ImageView profilbearbeitenLogo_PF;
    ImageView followeranzeigenBtn_PF;

    RecyclerView rvEigenschaften;
    ArrayList<Eigenschaft> eigenschaftenliste = new ArrayList<>();

    Dialog profilbildzeigenDialog;
    ImageView profilbildDialog;

    public static Nutzer profilNutzer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilfragment_layout, container, false);

        profilbild_PF = view.findViewById(R.id.profilbild_pf);
        name_PF = view.findViewById(R.id.name_pf);
        follower_Zahl = view.findViewById(R.id.followerzahl);
        profilbearbeitenLogo_PF = view.findViewById(R.id.profilbearbeitenlogo);
        followeranzeigenBtn_PF = view.findViewById(R.id.followeranzeigenbtn);
        rvEigenschaften = view.findViewById(R.id.rvmerkmale);
        rvEigenschaften.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        profilbildzeigenDialog = new Dialog(getContext());
        profilbildzeigenDialog.setContentView(R.layout.profilbildzeigen_layoutdialog);

        profilbildDialog = profilbildzeigenDialog.getWindow().findViewById(R.id.profilbilddialog);

        profilbildzeigenDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        if(profilNutzer.getId().equals(HomeActivity.currentNutzer.getId())) {

            profilbearbeitenLogo_PF.setVisibility(View.VISIBLE);


        } else {

            profilbearbeitenLogo_PF.setVisibility(View.INVISIBLE);

        }



        name_PF.setText(profilNutzer.getUsername());

        eigenschaftenSuchen(profilNutzer);



        //Noch umändern sodass es immer aktualisiert wird: vielleich mit onChangeListener()

            String anzahlFollower = String.valueOf(profilNutzer.getFollower());
            follower_Zahl.setText(anzahlFollower);


            // ---------------


            if(profilNutzer.getPhotourl().equals("")) {

                //kein Photo als Profilbild ausgewählt

            } else {

                Picasso.get().load(profilNutzer.getPhotourl()).transform(new CropCircleTransformation()).into(profilbild_PF);
               // Toast.makeText(getContext(), "Email Profil geladen!", Toast.LENGTH_SHORT).show();

            }

            // Buttons und deren onClickListener():


        profilbild_PF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                profilbild_PF.startAnimation(pop);

                profilbildLaden(profilNutzer);

                profilbildzeigenDialog.show();


            }
        });


        profilbildDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profilbildzeigenDialog.dismiss();


            }
        });

        profilbearbeitenLogo_PF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                profilbearbeitenLogo_PF.startAnimation(pop);




                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                        new profilbearbeitenFragment()).commit();





            }
        });


            followeranzeigenBtn_PF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                    followeranzeigenBtn_PF.startAnimation(pop);


                }
            });

           /* Uri profilePicture = HomeActivity.currentUserAngemeldet.getPhotoUrl();

            Picasso.get().load(profilePicture).transform(new CropCircleTransformation()).into(profilbild_PF);

            Toast.makeText(getContext(), "Google Profil geladen!", Toast.LENGTH_SHORT).show();

*/


        return view;
    }


    //------- METHODEN ------


    public void profilbildLaden(Nutzer nutzer) {

        Picasso.get().load(nutzer.getPhotourl()).transform(new CropCircleTransformation()).into(profilbildDialog);
    }

    public void eigenschaftenSuchen(Nutzer nutzer){

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(nutzer.getId()).child("Meine Sticker");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    for(DataSnapshot dt : ds.getChildren()) {

                        Eigenschaft currentEigenschaft = dt.getValue(Eigenschaft.class);

                        eigenschaftenliste.add(currentEigenschaft);


                    }


                }


                rvEigenschaften.setAdapter(new EigenschaftenAdapter(eigenschaftenliste, getActivity()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

}
