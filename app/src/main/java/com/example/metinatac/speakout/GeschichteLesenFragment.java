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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GeschichteLesenFragment extends Fragment {

    TextView buchTitle;
    TextView kbTv;
    ImageView addChapterBtn;
    ImageView unlikedBtnGe;
    ImageView likedBtnGe;
    RecyclerView rvChapter;
    ArrayList<Kapitel> kapitelliste = new ArrayList<>();

    static Geschichte currentGeschichte;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.geschichtelesenfragment_layout, container, false);

        buchTitle = view.findViewById(R.id.buchtitletv);
        kbTv = view.findViewById(R.id.kbtv);
        addChapterBtn = view.findViewById(R.id.addchapterbtn);
        unlikedBtnGe = view.findViewById(R.id.unlikedge);
        likedBtnGe = view.findViewById(R.id.likedge);
        rvChapter = view.findViewById(R.id.rvkapitel);
        rvChapter.setLayoutManager(new GridLayoutManager(getContext(), 3));

        buchTitle.setText(currentGeschichte.getName());
        kbTv.setText(currentGeschichte.getKurzbeschreibung());

        addChapterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                addChapterBtn.startAnimation(pop);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                                new AddChapterFragment()).commit();
                    }
                }, 250);




            }
        });


        alleKaptelFinden();


        return view;
    }


    //----- METHODEN -----


    public void alleKaptelFinden() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(currentGeschichte.getInhaberid()).child("Meine Buecher").child(currentGeschichte.getId()).child("Kapitel");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Kapitel currentKapitel = ds.getValue(Kapitel.class);

                    kapitelliste.add(currentKapitel);

                }

                rvChapter.setAdapter(new KapitelAdapter(kapitelliste, getActivity()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }



}
