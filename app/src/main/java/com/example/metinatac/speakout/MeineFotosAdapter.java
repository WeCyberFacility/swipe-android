package com.example.metinatac.speakout;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class MeineFotosAdapter extends RecyclerView.Adapter<MeineFotosAdapter.MeineFotosHolder> {

    ArrayList<Foto> data;

    FragmentActivity fragmentActivity;

    public MeineFotosAdapter (ArrayList<Foto> data, FragmentActivity fragmentActivity) {

        this.data = data;
        this.fragmentActivity = fragmentActivity;

    }



    @NonNull
    @Override
    public MeineFotosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.meinefotosliste,viewGroup , false);



        return new MeineFotosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeineFotosHolder meineFotosHolder, int i) {

        Picasso.get().load(data.get(i).getmImageUrl()).fit().centerCrop().into(meineFotosHolder.bildLayout);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //----- METHODEN -----





    public class MeineFotosHolder extends RecyclerView.ViewHolder {

        ImageView bildLayout;

        public MeineFotosHolder(@NonNull View itemView) {
            super(itemView);

            bildLayout = itemView.findViewById(R.id.bildlayout);


        }
    }

}
