package com.example.metinatac.speakout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MerkmaleBearbeitenFragment extends Fragment{


    RecyclerView rvMerkmale;
    RecyclerView rvArtists;
    RecyclerView rvGames;
    RecyclerView rvSerien;

    ArrayList<Eigenschaft> merkmaleListe = new ArrayList<>();
    ArrayList<Eigenschaft> artistsListe = new ArrayList<>();
    ArrayList<Eigenschaft> gamesListe = new ArrayList<>();
    ArrayList<Eigenschaft> serienListe = new ArrayList<>();





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mermalebearbeitenfragment_layout, container, false );

        rvMerkmale = view.findViewById(R.id.rvneuMerkmale);
        rvArtists = view.findViewById(R.id.rvneuArtists);
        rvGames = view.findViewById(R.id.rvneuGames);
        rvSerien = view.findViewById(R.id.rvneuSerien);

        rvMerkmale.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvArtists.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvGames.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvSerien.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        alleStickerFinden();


        return view;
    }



    //-------- METHODEN ---------


    public void alleStickerFinden(){

        alleMerkmaleFinden();
        alleArtistsFinden();
        alleGamesFinden();
        alleSerienFinden();



    }

    public void alleMerkmaleFinden() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Sticker");

        myRef = myRef.child("Merkmale");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    Eigenschaft currentEigenschaft = ds.getValue(Eigenschaft.class);

                    merkmaleListe.add(currentEigenschaft);

                }

                rvMerkmale.setAdapter(new EigenschaftenAdapter(merkmaleListe, getActivity()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void alleArtistsFinden() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Sticker");

        myRef = myRef.child("Artists");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    Eigenschaft currentEigenschaft = ds.getValue(Eigenschaft.class);

                    artistsListe.add(currentEigenschaft);

                }

                rvArtists.setAdapter(new EigenschaftenAdapter(artistsListe, getActivity()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void alleGamesFinden() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Sticker");

        myRef = myRef.child("Games");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    Eigenschaft currentEigenschaft = ds.getValue(Eigenschaft.class);

                    gamesListe.add(currentEigenschaft);

                }

                rvGames.setAdapter(new EigenschaftenAdapter(gamesListe, getActivity()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void alleSerienFinden() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Sticker");

        myRef = myRef.child("Serien");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    Eigenschaft currentEigenschaft = ds.getValue(Eigenschaft.class);

                    serienListe.add(currentEigenschaft);

                }

                rvSerien.setAdapter(new EigenschaftenAdapter(serienListe, getActivity()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }



}
