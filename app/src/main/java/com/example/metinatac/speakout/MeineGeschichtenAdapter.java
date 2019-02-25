package com.example.metinatac.speakout;

import android.app.Dialog;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MeineGeschichtenAdapter extends RecyclerView.Adapter<MeineGeschichtenAdapter.MeineGeschichtenHolder> {

    ArrayList<Geschichte> data;
    FragmentActivity fragmentActivity;

    Dialog buchzeigenDialog;
    //Dialog XML Sachen:

    TextView booknameTv;
    TextView kurzbeschreibungTv;
    TextView genreTv;

    ImageView readBtn;
    ImageView coberbookzeigen;

    Geschichte currentGeschichte;
    int position;

    public MeineGeschichtenAdapter(ArrayList<Geschichte> data, FragmentActivity fragmentActivity) {

        this.data = data;
        this.fragmentActivity = fragmentActivity;

    }

    @NonNull
    @Override
    public MeineGeschichtenHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.geschichtenlistlayout,viewGroup , false);


        buchzeigenDialog = new Dialog(fragmentActivity);
        buchzeigenDialog.setContentView(R.layout.bookanzeigendialog_layout);

        booknameTv = buchzeigenDialog.getWindow().findViewById(R.id.booknametv);
        kurzbeschreibungTv = buchzeigenDialog.getWindow().findViewById(R.id.kurzbeschreibungtv);
        genreTv = buchzeigenDialog.getWindow().findViewById(R.id.genretv);
        readBtn = buchzeigenDialog.getWindow().findViewById(R.id.readbtn);
        coberbookzeigen = buchzeigenDialog.getWindow().findViewById(R.id.coverbookanzeigen);

        buchzeigenDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);





        return new MeineGeschichtenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MeineGeschichtenHolder meineGeschichtenHolder, final int i) {


        Glide.with(fragmentActivity).load(data.get(i).getBookcoverurl()).centerCrop().into(meineGeschichtenHolder.bookcoverBtn);


        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                buchzeigenDialog.dismiss();

                Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.pop);
                readBtn.startAnimation(pop);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        GeschichteLesenFragment newFrag = new GeschichteLesenFragment();
                        newFrag.currentGeschichte = currentGeschichte;

                        fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,
                                newFrag).commit();
                    }
                }, 250);



            }
        });


        currentGeschichte = data.get(i);
        position = i;


        meineGeschichtenHolder.bookName.setText(data.get(i).getName());

        meineGeschichtenHolder.buchCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(data.get(i).getId().equals("preview")) {


                } else {


                    meineGeschichtenHolder.deltegeschichteBtn.setVisibility(View.INVISIBLE);

                    Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.pop);
                    meineGeschichtenHolder.buchCl.startAnimation(pop);

                    booknameTv.setText(data.get(i).getName());
                    kurzbeschreibungTv.setText(data.get(i).getKurzbeschreibung());
                    genreTv.setText(data.get(i).getGenre());

                    //Picasso.get().load(data.get(i).getBookcoverurl()).fit().centerCrop().into(coberbookzeigen);

                    Glide.with(fragmentActivity).load(data.get(i).getBookcoverurl()).centerCrop().into(coberbookzeigen);

                    currentGeschichte = data.get(i);

                    buchzeigenDialog.show();

                }

            }
        });


        meineGeschichtenHolder.buchCl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if(data.get(i).getId().equals("preview")) {

                } else {


                    Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.shakeloop);
                    pop.setRepeatCount(Animation.INFINITE);
                    meineGeschichtenHolder.buchCl.startAnimation(pop);

                    meineGeschichtenHolder.deltegeschichteBtn.setVisibility(View.VISIBLE);
                }



                return true;
            }
        });


        meineGeschichtenHolder.deltegeschichteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(fragmentActivity, R.anim.pop);
                meineGeschichtenHolder.deltegeschichteBtn.startAnimation(pop);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms

                        // Write a message to the database
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Nutzer");
                        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Meine Buecher").child(data.get(i).getId());

                        myRef.removeValue();


                        data.remove(i);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(i, data.size());



                    }
                }, 250);



            }
        });




    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void deleteBook(Geschichte geschichte, int position) {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");
        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Meine Buecher").child(geschichte.getId());

        myRef.removeValue();



    }





    public class MeineGeschichtenHolder extends RecyclerView.ViewHolder {

        ImageView bookcoverBtn;
        TextView bookName;
        ConstraintLayout buchCl;
        ImageView deltegeschichteBtn;

        public MeineGeschichtenHolder(@NonNull View itemView) {
            super(itemView);

            bookcoverBtn = itemView.findViewById(R.id.bookcoverbtn);
            bookName = itemView.findViewById(R.id.bookname);
            buchCl = itemView.findViewById(R.id.buchcl);
            deltegeschichteBtn = itemView.findViewById(R.id.deletegeschichtebtn);
        }
    }

}
