package com.example.metinatac.speakout;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public void onBindViewHolder(@NonNull KapitelHolder kapitelHolder, int i) {


        kapitelHolder.kapitelNameTxt.setText(data.get(i).getName());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class KapitelHolder extends RecyclerView.ViewHolder {

        TextView kapitelNameTxt;

        public KapitelHolder(@NonNull View itemView) {
            super(itemView);

            kapitelNameTxt = itemView.findViewById(R.id.kapiteltitletxt);

        }
    }


}
