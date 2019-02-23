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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeschichteErstellenFragment extends Fragment{


    EditText nameEingabe;
    EditText kurzbeschreibungEingabe;
    Button buchhochladenBtn;
    Spinner genreSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.geschichteerstellenfragment_layout, container, false);


        nameEingabe = view.findViewById(R.id.nameeingabe);
        kurzbeschreibungEingabe = view.findViewById(R.id.kurzbeschreibungeingabe);
        buchhochladenBtn = view.findViewById(R.id.gehochladenbtn);
        genreSpinner = view.findViewById(R.id.spinnergenres);



        buchhochladenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                buchhochladenBtn.startAnimation(pop);

                BuchHochladen();


            }
        });



        return view;
    }



    // ------ METHODEN -------


    public void BuchHochladen() {

        if(checkAllesAusgefuellt()) {

            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Nutzer");
            myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Meine Buecher");

            String id = myRef.push().getKey();
            String genreselected = genreSpinner.getSelectedItem().toString();
            String namedesbuches = nameEingabe.getText().toString().trim();
            String kurzbeschreibungdesbuches = kurzbeschreibungEingabe.getText().toString().trim();

            Geschichte neueGeschichte = new Geschichte(id, HomeActivity.currentNutzer.getId() , genreselected, namedesbuches, "", kurzbeschreibungdesbuches, 0, 0);

            myRef.child(id).child("Daten").setValue(neueGeschichte);

            Toast.makeText(getContext(), "Buch: " + "'" + neueGeschichte.getName() + "' gespeichert", Toast.LENGTH_SHORT).show();


            nameEingabe.setText("");
            kurzbeschreibungEingabe.setText("");

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                    new GeschichteHinzufuegenFragment()).commit();




        } else {

            Toast.makeText(getContext(), "Bitte fülle alle Felder aus!", Toast.LENGTH_SHORT).show();


        }



    }

    public boolean checkAllesAusgefuellt(){

        Boolean gefunden = false;

        if(nameEingabe.getText().toString().trim().equals("") || kurzbeschreibungEingabe.getText().toString().trim().equals("")) {

            return false;


        } else {

            return true;
        }

    }


}
