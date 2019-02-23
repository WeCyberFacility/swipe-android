package com.example.metinatac.speakout;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

public class KapitelAdapter extends RecyclerView.Adapter<KapitelAdapter.KapitelHolder>{

    ArrayList<Kapitel> data;
    FragmentActivity fragmentActivity;

    public KapitelAdapter(ArrayList<Kapitel> data, FragmentActivity fragmentActivity) {

        this.data = data;
        this.fragmentActivity = fragmentActivity;

    }

    @NonNull
    @Override
    public KapitelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.kapitellistlayout,viewGroup , false);



        return new KapitelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final KapitelHolder kapitelHolder, final int i) {


        kapitelHolder.kapitelNameTxt.setText(data.get(i).getName());


        kapitelHolder.kapitelCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.pop);
                kapitelHolder.kapitelCl.startAnimation(pop);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        ChapterOeffnenFragment newFrag = new ChapterOeffnenFragment();
                        newFrag.currentKapitel = data.get(i);


                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                                newFrag).commit();


                    }
                }, 250);



            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class KapitelHolder extends RecyclerView.ViewHolder {

        TextView kapitelNameTxt;
        ConstraintLayout kapitelCl;

        public KapitelHolder(@NonNull View itemView) {
            super(itemView);

            kapitelNameTxt = itemView.findViewById(R.id.kapiteltitletxt);
            kapitelCl = itemView.findViewById(R.id.kapitelcl);

        }
    }


}
