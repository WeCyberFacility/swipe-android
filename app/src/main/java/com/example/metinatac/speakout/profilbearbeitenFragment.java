package com.example.metinatac.speakout;

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
import android.widget.ImageView;

public class profilbearbeitenFragment extends Fragment {


    ImageView mermalebearbeitenBtn;
    Button usernameaendernBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profilbearbeitenfragment_layout, container, false);


        mermalebearbeitenBtn = view.findViewById(R.id.merkmaleaendernbtn);
        usernameaendernBtn = view.findViewById(R.id.usernameaendernbtn);



        mermalebearbeitenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                mermalebearbeitenBtn.startAnimation(pop);



            }
        });


        usernameaendernBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                usernameaendernBtn.startAnimation(pop);


            }
        });


        return view;

    }
}
