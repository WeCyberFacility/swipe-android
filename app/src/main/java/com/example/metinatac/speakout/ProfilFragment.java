package com.example.metinatac.speakout;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ProfilFragment extends Fragment {


    ImageView profilbild_PF;
    TextView name_PF;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilfragment_layout, container, false);

        profilbild_PF = view.findViewById(R.id.profilbild_pf);
        name_PF = view.findViewById(R.id.name_pf);




            name_PF.setText(HomeActivity.currentNutzer.getUsername());


            if(HomeActivity.currentNutzer.getPhotourl().equals("")) {

                //kein Photo als Profilbild ausgewählt

            } else {

                Picasso.get().load(HomeActivity.currentNutzer.getPhotourl()).transform(new CropCircleTransformation()).into(profilbild_PF);
                Toast.makeText(getContext(), "Email Profil geladen!", Toast.LENGTH_SHORT).show();

            }





           /* Uri profilePicture = HomeActivity.currentUserAngemeldet.getPhotoUrl();

            Picasso.get().load(profilePicture).transform(new CropCircleTransformation()).into(profilbild_PF);

            Toast.makeText(getContext(), "Google Profil geladen!", Toast.LENGTH_SHORT).show();

*/


        return view;
    }
}
