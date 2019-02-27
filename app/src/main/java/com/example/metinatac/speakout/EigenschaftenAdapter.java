package com.example.metinatac.speakout;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class EigenschaftenAdapter extends RecyclerView.Adapter<EigenschaftenAdapter.EigenschaftenHolder>{

    public ArrayList<Eigenschaft> data;
    FragmentActivity fragmentActivity;

    Dialog eigenschaftbeschreibungsDialog;

    TextView nameeigenschaft_Dialog;
    TextView beschreibungeigenschaft_Dialog;
    ImageView bildEigenschaft_Dialog;
    TextView hotbaromater;
    ImageView deleteBtn;
    ImageView okBtn;


    Eigenschaft currentEigenschaft;


    public EigenschaftenAdapter(ArrayList<Eigenschaft> data, FragmentActivity fragmentActivity){

        this.data = data;
        this.fragmentActivity = fragmentActivity;


    }

    @NonNull
    @Override
    public EigenschaftenHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.eigenschaftenlist_layout,viewGroup , false);

        eigenschaftbeschreibungsDialog = new Dialog(fragmentActivity);
        eigenschaftbeschreibungsDialog.setContentView(R.layout.eigenschaftsbeschreibung_layoutdialog);

        nameeigenschaft_Dialog = eigenschaftbeschreibungsDialog.getWindow().findViewById(R.id.nameeigenschaft_dialog);
        beschreibungeigenschaft_Dialog = eigenschaftbeschreibungsDialog.getWindow().findViewById(R.id.beschreibungeigenschaft_dialog);
        bildEigenschaft_Dialog = eigenschaftbeschreibungsDialog.getWindow().findViewById(R.id.bildeigenschaft);
        hotbaromater = eigenschaftbeschreibungsDialog.getWindow().findViewById(R.id.hot);
        deleteBtn = eigenschaftbeschreibungsDialog.getWindow().findViewById(R.id.deletebtn);
        okBtn = eigenschaftbeschreibungsDialog.getWindow().findViewById(R.id.okbtn);

        eigenschaftbeschreibungsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        return new EigenschaftenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final EigenschaftenHolder eigenschaftenHolder, final int i) {


        eigenschaftenHolder.eigenschaft_name.setText(data.get(i).getName());
        Picasso.get().load(data.get(i).getPhotourl()).transform(new CropCircleTransformation()).fit().centerCrop().into(eigenschaftenHolder.eigenschafte_Bild);
        hotbaromater.setText(String.valueOf(data.get(i).getHotBarometer()));



        //Wenn man auf eine Eigenschaft drückt, soll ein Dialog erscheinen, wo wie besonderheiten der Eigenschaft stehen
        eigenschaftenHolder.eigenschafte_Bild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.popsmall);
                eigenschaftenHolder.eigenschafte_Bild.startAnimation(pop);




                Handler hi = new Handler();
                hi.postDelayed(new Runnable() {


                    @Override
                    public void run() {

                        nameeigenschaft_Dialog.setText(data.get(i).getName());
                        beschreibungeigenschaft_Dialog.setText(data.get(i).getBeschreibung());

                        Picasso.get().load(data.get(i).getPhotourl()).transform(new CropCircleTransformation()).fit().centerCrop().into(bildEigenschaft_Dialog);


                        geaddetCheck(data.get(i));

                        currentEigenschaft = data.get(i);

                        eigenschaftbeschreibungsDialog.show();


                    }
                }, 250);






            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.popsmall);
                deleteBtn.startAnimation(pop);

                Handler hi = new Handler();
                hi.postDelayed(new Runnable() {


                    @Override
                    public void run() {

                        StickerLoeschen(currentEigenschaft);

                        eigenschaftbeschreibungsDialog.dismiss();

                        notifyDataSetChanged();

                    }
                }, 250);






            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.popsmall);
                okBtn.startAnimation(pop);


                Handler hi = new Handler();
                hi.postDelayed(new Runnable() {


                    @Override
                    public void run() {

                        StickerAdden(currentEigenschaft);

                        eigenschaftbeschreibungsDialog.dismiss();

                        notifyDataSetChanged();

                    }
                }, 250);





            }
        });

    }



    @Override
    public int getItemCount() {
        return data.size();
    }


    //----- METHODEN -----


    public void geaddetCheck(Eigenschaft eigenschaft){

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Meine Sticker").child(eigenschaft.getTyp()).child(eigenschaft.getId());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Eigenschaft currentEigenschaft = dataSnapshot.getValue(Eigenschaft.class);


                if(currentEigenschaft == null) {

                    okBtn.setVisibility(View.VISIBLE);
                    deleteBtn.setVisibility(View.INVISIBLE);

                } else {

                    okBtn.setVisibility(View.INVISIBLE);
                    deleteBtn.setVisibility(View.VISIBLE);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void StickerAdden(Eigenschaft eigenschaft) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Meine Sticker").child(eigenschaft.getTyp()).child(eigenschaft.getId());
        myRef.setValue(eigenschaft);

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database2.getReference("Sticker");
        myRef2 = myRef2.child(eigenschaft.getTyp()).child(eigenschaft.getId());

        Eigenschaft ueberarbeiteteEigenschaft = eigenschaft;
        ueberarbeiteteEigenschaft.setHotBarometer(ueberarbeiteteEigenschaft.getHotBarometer() + 1);

        myRef2.setValue(ueberarbeiteteEigenschaft);
    }


    public void StickerLoeschen(Eigenschaft eigenschaft) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Meine Sticker").child(eigenschaft.getTyp()).child(eigenschaft.getId());

        myRef.removeValue();

        //data.remove(eigenschaft);

    }


    public class EigenschaftenHolder extends RecyclerView.ViewHolder {


        ImageView eigenschafte_Bild;
        TextView eigenschaft_name;


        public EigenschaftenHolder(@NonNull View itemView) {
            super(itemView);

            eigenschafte_Bild = itemView.findViewById(R.id.eigenschaftbild);
            eigenschaft_name = itemView.findViewById(R.id.eigenschaftname);


        }
    }


}
