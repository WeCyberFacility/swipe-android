package com.example.metinatac.speakout;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfilFragment extends Fragment {


    ImageView profilbild_PF;
    TextView name_PF;
    TextView follower_Zahl;
    ImageView profilbearbeitenLogo_PF;
    ImageView followeranzeigenBtn_PF;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilfragment_layout, container, false);

        profilbild_PF = view.findViewById(R.id.profilbild_pf);
        name_PF = view.findViewById(R.id.name_pf);
        follower_Zahl = view.findViewById(R.id.followerzahl);
        profilbearbeitenLogo_PF = view.findViewById(R.id.profilbearbeitenlogo);
        followeranzeigenBtn_PF = view.findViewById(R.id.followeranzeigenbtn);

        name_PF.setText(HomeActivity.currentNutzer.getUsername());


        //Noch umändern sodass es immer aktualisiert wird: vielleich mit onChangeListener()

            String anzahlFollower = String.valueOf(HomeActivity.currentNutzer.getFollower());
            follower_Zahl.setText(anzahlFollower);


            // ---------------


            if(HomeActivity.currentNutzer.getPhotourl().equals("")) {

                //kein Photo als Profilbild ausgewählt

            } else {

                Picasso.get().load(HomeActivity.currentNutzer.getPhotourl()).transform(new CropCircleTransformation()).into(profilbild_PF);
               // Toast.makeText(getContext(), "Email Profil geladen!", Toast.LENGTH_SHORT).show();

            }

            // Buttons und deren onClickListener():

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



    public void eigenschaftenSuchen(){






    }

}
