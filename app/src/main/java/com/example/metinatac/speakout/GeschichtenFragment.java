package com.example.metinatac.speakout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class GeschichtenFragment extends Fragment {

    ConstraintLayout c_linksOben;
    ConstraintLayout c_rechtOben;
    ConstraintLayout c_linksUnten;
    ConstraintLayout c_rechtsUnten;

    ImageView linksobenBtn;
    ImageView rechtsobenBtn;
    ImageView linksuntenBtn;
    ImageView rechtsuntenBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.geschichtenfragment_layout, container, false);

        c_linksOben = view.findViewById(R.id.c_linksoben);
        c_rechtOben = view.findViewById(R.id.c_rechtsoben);
        c_linksUnten = view.findViewById(R.id.c_linksunten);
        c_rechtsUnten = view.findViewById(R.id.c_rechtsunten);

        linksobenBtn = view.findViewById(R.id.linksobenlogo);
        rechtsobenBtn = view.findViewById(R.id.rechtsobenlogo);
        linksuntenBtn = view.findViewById(R.id.linksuntenlogo);
        rechtsuntenBtn = view.findViewById(R.id.rechtsuntenlogo);


        c_linksOben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                linksobenBtn.startAnimation(pop);



            }
        });


        c_rechtOben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                rechtsobenBtn.startAnimation(pop);


            }
        });

        c_linksUnten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                linksuntenBtn.startAnimation(pop);

            }
        });

        c_rechtsUnten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                rechtsuntenBtn.startAnimation(pop);


            }
        });

        return view;
    }
}
