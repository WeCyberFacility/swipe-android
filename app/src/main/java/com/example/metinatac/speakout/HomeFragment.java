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
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView rvmostpopularTags;
    ArrayList<Eigenschaft> mostpopulartagsliste = new ArrayList<>();
    int a = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment_layout, container, false);

        rvmostpopularTags = view.findViewById(R.id.rvmostpopulartags);
        rvmostpopularTags.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        mostPopularTagsFinden();

        return view;
    }


    //----- METHODEN -----

    public void mostPopularTagsFinden () {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Sticker");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    for(DataSnapshot dt : ds.getChildren()) {

                        Eigenschaft currentEigenschaft = dt.getValue(Eigenschaft.class);

                        mostpopulartagsliste.add(currentEigenschaft);


                    }


                }

                rvmostpopularTags.setAdapter(new EigenschaftenAdapter(mostpopulartagsliste, getActivity()));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
