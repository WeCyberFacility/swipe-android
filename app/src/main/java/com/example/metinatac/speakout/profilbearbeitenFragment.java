package com.example.metinatac.speakout;

import android.animation.TimeAnimator;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profilbearbeitenFragment extends Fragment {


    ImageView mermalebearbeitenBtn;
    Button usernameaendernBtn;
    Button profilbildaendernBtn;
    EditText neuesprofilbildUrltxt;
    EditText neuerusernametxt;
    
    Boolean usernameVergeben = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilbearbeitenfragment_layout, container, false);


        mermalebearbeitenBtn = view.findViewById(R.id.merkmaleaendernbtn);
        usernameaendernBtn = view.findViewById(R.id.usernameaendernbtn);
        profilbildaendernBtn = view.findViewById(R.id.profilbildaendernbtn);
        neuerusernametxt = view.findViewById(R.id.usernameneutxt);
        neuesprofilbildUrltxt = view.findViewById(R.id.profilbildurl);


        mermalebearbeitenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                mermalebearbeitenBtn.startAnimation(pop);


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                        new MerkmaleBearbeitenFragment()).commit();



            }
        });


        usernameaendernBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                usernameaendernBtn.startAnimation(pop);
                
                String neuerUsername = neuerusernametxt.getText().toString().trim();
                
                if(neuerUsername.equals("")) {


                    Toast.makeText(getContext(), "Bitte gib einen Nutzernamen ein!", Toast.LENGTH_SHORT).show();
                } else {

                    checkeNutzernamenUndAendereIhnWennMoeglich(neuerUsername);

                    neuerusernametxt.setText("");
                    
                }


            }
        });


        profilbildaendernBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                profilbildaendernBtn.startAnimation(pop);


                String neuerProfilbildUrl = neuesprofilbildUrltxt.getText().toString().trim();

                if(neuerProfilbildUrl.equals("")) {

                    Toast.makeText(getContext(), "Bitte gib eine Foto URL ein!", Toast.LENGTH_SHORT).show();

                } else {

                    profilbildaendern(neuerProfilbildUrl);
                    neuesprofilbildUrltxt.setText("");

                }


            }
        });

        return view;

    }



    //-------- METHODEN -------------


    public void usernamenAendern(String neuerusername){

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Daten");

        Nutzer neuerNutzer = HomeActivity.currentNutzer;
        
        neuerNutzer.setUsername(neuerusername);
        
        myRef.setValue(neuerNutzer);
        HomeActivity.currentNutzer = neuerNutzer;


    }


    public void profilbildaendern(String photourl){

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Daten");

        Nutzer neuerNutzer = HomeActivity.currentNutzer;

        neuerNutzer.setPhotourl(photourl);

        myRef.setValue(neuerNutzer);
        HomeActivity.currentNutzer = neuerNutzer;


    }
    
    
    public void checkeNutzernamenUndAendereIhnWennMoeglich(final String zucheckenderNutzername) {

        
        usernameVergeben = false;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");
        
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    
                    Nutzer currentNutzer = ds.child("Daten").getValue(Nutzer.class);
                    
                    if(currentNutzer.getUsername().equals(zucheckenderNutzername)) {
                        
                        
                        usernameVergeben = true;
                        break;
                        
                    } else {
                        
                        
                        continue;
                    }
                    
                    
                }


                if(usernameVergeben == true) {

                    Toast.makeText(getContext(), "Dieser Nutzername ist bereits vergeben", Toast.LENGTH_SHORT).show();



                } else {

                    usernamenAendern(zucheckenderNutzername);

                    Toast.makeText(getContext(), "Username geändert!", Toast.LENGTH_SHORT).show();


                }
                
                
                
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        

        
    }
}
