package com.example.metinatac.speakout;

import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminFragment extends Fragment {


    EditText neuerstickerName;
    EditText neuerstickerBeschreibung;
    EditText neuerstickerUrl;

    ProgressBar progressBarspeichern;

    RecyclerView rvStickerVorschau;
    ArrayList<Eigenschaft> vorschauliste = new ArrayList<>();

    Button vorschauzeigenBtn;
    Button addstickerBtn;

    Spinner typSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adminfragment_layout, container, false);

        neuerstickerName = view.findViewById(R.id.neustickername);
        neuerstickerBeschreibung = view.findViewById(R.id.neustickerbeschreibung);
        neuerstickerUrl = view.findViewById(R.id.neustickerurl);

        progressBarspeichern = view.findViewById(R.id.progressBarSpeichern);

        rvStickerVorschau = view.findViewById(R.id.rvneuerstickerdemo);
        rvStickerVorschau.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        vorschauzeigenBtn = view.findViewById(R.id.stickerdemobtn);
        addstickerBtn = view.findViewById(R.id.addstickerbtn);

        typSpinner = view.findViewById(R.id.typspinner);


        vorschauzeigenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                vorschauzeigenBtn.startAnimation(pop);

                vorschaustickerLaden();



            }
        });


        addstickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                addstickerBtn.startAnimation(pop);

                StickerSpeichern();


            }
        });


        return view;
    }

    //------ METHODEN ------


    public void vorschaustickerLaden() {

            if(checkAlleFelder()) {

                vorschauliste = new ArrayList<>();

                String nameNew = neuerstickerName.getText().toString().trim();
                String beschreibungNew = neuerstickerBeschreibung.getText().toString().trim();
                String urlNew = neuerstickerUrl.getText().toString().trim();
                String typ = typSpinner.getSelectedItem().toString();

                Eigenschaft neueEigenschaft = new Eigenschaft("", nameNew, typ, beschreibungNew, urlNew, 0);

                vorschauliste.add(neueEigenschaft);

                rvStickerVorschau.setAdapter(new EigenschaftenAdapter(vorschauliste, getActivity()));


            } else {

                Toast.makeText(getContext(), "Fülle alle Felder aus!", Toast.LENGTH_SHORT).show();


            }



    }


    public void StickerSpeichern() {

        String nameNew = neuerstickerName.getText().toString().trim();
        String beschreibungNew = neuerstickerBeschreibung.getText().toString().trim();
        String urlNew = neuerstickerUrl.getText().toString().trim();
        String typ = typSpinner.getSelectedItem().toString();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Sticker");

        myRef = myRef.child(typ);
        String id = myRef.push().getKey();

        Eigenschaft neueEigenschaft = new Eigenschaft(id, nameNew, typ, beschreibungNew, urlNew, 0);

        myRef.child(id).setValue(neueEigenschaft).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                progressBarspeichern.setProgress(100);


            }
        });

       /* int i = 0;


        while(i != 100) {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    progressBarspeichern.setProgress(progressBarspeichern.getProgress() + 1);
                }
            }, 100);


        }
*/


        Toast.makeText(getContext(), "Sticker gespeichert!", Toast.LENGTH_SHORT).show();






    }


    public boolean checkAlleFelder() {

        if(neuerstickerName.getText().toString().trim().equals("") ||
                neuerstickerBeschreibung.getText().toString().trim().equals("") ||
                neuerstickerUrl.getText().toString().trim().equals("") ) {

            return false;

        } else {

            return true;
        }



    }


}
