package com.example.metinatac.speakout;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class MeineFotosAdapter extends RecyclerView.Adapter<MeineFotosAdapter.MeineFotosHolder> {

    ArrayList<Foto> data;

    FragmentActivity fragmentActivity;

    Dialog fotozeigenDialog;

    //alles was im Dialog ist:
    ImageView bildIv;
    ImageView likeBtn;
    ImageView unlikeBtn;

    Foto currentFoto;

    boolean schongeliked;

    public MeineFotosAdapter (ArrayList<Foto> data, FragmentActivity fragmentActivity) {

        this.data = data;
        this.fragmentActivity = fragmentActivity;

    }



    @NonNull
    @Override
    public MeineFotosHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.meinefotosliste,viewGroup , false);


        fotozeigenDialog = new Dialog(fragmentActivity);
        fotozeigenDialog.setContentView(R.layout.fotozeigendialog_layout);

        bildIv = fotozeigenDialog.getWindow().findViewById(R.id.bildiv);
        likeBtn = fotozeigenDialog.getWindow().findViewById(R.id.likedheart);
        unlikeBtn = fotozeigenDialog.getWindow().findViewById(R.id.unlikedheart);


        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.pop);
                likeBtn.startAnimation(pop);

                bilddisliken(currentFoto);

                likeBtn.setVisibility(View.INVISIBLE);
                unlikeBtn.setVisibility(View.VISIBLE);


            }
        });


        unlikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.pop);
                unlikeBtn.startAnimation(pop);


                bildLiken(currentFoto);

                unlikeBtn.setVisibility(View.INVISIBLE);
                likeBtn.setVisibility(View.VISIBLE);
            }
        });


        return new MeineFotosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeineFotosHolder meineFotosHolder, final int i) {

        Picasso.get().load(data.get(i).getmImageUrl()).fit().centerCrop().into(meineFotosHolder.bildLayout);

        meineFotosHolder.bildLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentFoto = data.get(i);
                schonGeliked(currentFoto);
                Picasso.get().load(data.get(i).getmImageUrl()).fit().centerCrop().into(bildIv);


                fotozeigenDialog.show();



            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    //----- METHODEN -----


    public void bildLiken(Foto foto){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Gelikete Bilder");

        myRef.child(foto.getId()).setValue(foto);


    }


    public void bilddisliken(Foto foto) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Gelikete Bilder");

        myRef.child(foto.getId()).removeValue();


    }




    public void schonGeliked(Foto foto){


        schongeliked = false;

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Gelikete Bilder").child(foto.getId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Foto currentFotoSchonGeliked = dataSnapshot.getValue(Foto.class);

                if(currentFotoSchonGeliked == null) {

                    unlikeBtn.setVisibility(View.VISIBLE);
                    likeBtn.setVisibility(View.INVISIBLE);

                } else {

                    schongeliked = true;
                    unlikeBtn.setVisibility(View.INVISIBLE);
                    likeBtn.setVisibility(View.VISIBLE);


                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




    public class MeineFotosHolder extends RecyclerView.ViewHolder {

        ImageView bildLayout;

        public MeineFotosHolder(@NonNull View itemView) {
            super(itemView);

            bildLayout = itemView.findViewById(R.id.bildlayout);


        }
    }

}
