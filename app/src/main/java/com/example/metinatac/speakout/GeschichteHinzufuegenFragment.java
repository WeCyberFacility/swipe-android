package com.example.metinatac.speakout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GeschichteHinzufuegenFragment extends Fragment {

    ImageView neueGeschichteBtn;

    RecyclerView rvMeineGeschichten;
    ArrayList<Geschichte> geschichtenliste = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.geschichtehinzufuegenfragment_layout, container, false);

        neueGeschichteBtn = view.findViewById(R.id.neuegeschichtebtn);

        rvMeineGeschichten = view.findViewById(R.id.rvmeinegeschichten);
        rvMeineGeschichten.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rvMeineGeschichten.setNestedScrollingEnabled(false);


        neueGeschichteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                neueGeschichteBtn.startAnimation(pop);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                                new GeschichteErstellenFragment()).commit();
                    }
                }, 250);



            }
        });


        geschichtenFinden();

        return view;
    }


    //------ METHODEN ------


    public void geschichtenFinden() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Meine Buecher");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    Geschichte currentGeschichte = ds.child("Daten").getValue(Geschichte.class);

                    geschichtenliste.add(currentGeschichte);

                }

                rvMeineGeschichten.setAdapter(new MeineGeschichtenAdapter(geschichtenliste, getActivity()));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
