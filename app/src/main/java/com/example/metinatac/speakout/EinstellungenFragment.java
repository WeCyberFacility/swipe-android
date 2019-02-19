package com.example.metinatac.speakout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class EinstellungenFragment extends Fragment{


    ImageView adminLock;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.einstellungenfragment_layout, container, false);

        adminLock = view.findViewById(R.id.adminlock);

        adminLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                        new AdminFragment()).commit();


            }
        });


        adminLock.setVisibility(View.INVISIBLE);

        checkObAdmin();




        return view;
    }



    //------- METHODEN --------

    public void checkObAdmin(){

        if(HomeActivity.currentNutzer.getUsername().equals("alpay6767")) {

            adminLock.setVisibility(View.VISIBLE);


        } else {



        }


    }
}
