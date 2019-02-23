package com.example.metinatac.speakout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddChapterFragment extends Fragment {

    EditText namechapterEingabe;
    EditText minchapterEingabe;
    EditText descriptionchapterEingabe;
    EditText maincontentEingabe;
    Button uploadchapterBtn;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addchapterfragment_layout, container, false);

        namechapterEingabe = view.findViewById(R.id.namechaptereingabe);
        minchapterEingabe = view.findViewById(R.id.minchaptereingabe);
        descriptionchapterEingabe = view.findViewById(R.id.descriptioneingabe);
        maincontentEingabe = view.findViewById(R.id.maincontenteingabe);
        uploadchapterBtn = view.findViewById(R.id.chapteruplaodbtn);



        uploadchapterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                uploadchapterBtn.startAnimation(pop);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        if(pruefeObAlleAusgefuellt()) {

                            uploadChapter();

                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                                            new GeschichteLesenFragment()).commit();


                        } else {

                            Toast.makeText(getContext(), "Bitte fülle alle Felder aus!", Toast.LENGTH_SHORT).show();



                        }



                    }
                }, 250);




            }
        });


        return view;
    }



    // ------ METHODEN -----

    public boolean pruefeObAlleAusgefuellt() {

        if(namechapterEingabe.getText().toString().trim().equals("") ||
                minchapterEingabe.getText().toString().trim().equals("") ||
                descriptionchapterEingabe.getText().toString().trim().equals("") ||
                maincontentEingabe.getText().toString().trim().equals("")) {

            return false;


        } else {


            return true;

        }



    }


    public void uploadChapter() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Meine Buecher").child(GeschichteLesenFragment.currentGeschichte.getId()).child("Kapitel");
        String id = myRef.push().getKey();


        String nameK = namechapterEingabe.getText().toString().trim();
        String minK = minchapterEingabe.getText().toString().trim();
        String desK = descriptionchapterEingabe.getText().toString().trim();
        String mainConK = maincontentEingabe.getText().toString().trim();

        Kapitel neuesKapitel = new Kapitel(id, nameK, desK, minK, mainConK);

        myRef.child(id).setValue(neuesKapitel);

        Toast.makeText(getContext(), "Kapitel erstellt!", Toast.LENGTH_SHORT).show();



    }




}
