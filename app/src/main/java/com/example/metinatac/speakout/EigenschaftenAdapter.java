package com.example.metinatac.speakout;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class EigenschaftenAdapter extends RecyclerView.Adapter<EigenschaftenAdapter.EigenschaftenHolder>{

    public ArrayList<Eigenschaft> data;
    FragmentActivity fragmentActivity;

    Dialog eigenschaftbeschreibungsDialog;

    TextView nameeigenschaft_Dialog;
    TextView beschreibungeigenschaft_Dialog;


    public EigenschaftenAdapter(ArrayList<Eigenschaft> data, FragmentActivity fragmentActivity){

        this.data = data;
        this.fragmentActivity = fragmentActivity;


    }

    @NonNull
    @Override
    public EigenschaftenHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.eigenschaftenlist_layout,viewGroup , false);

        eigenschaftbeschreibungsDialog = new Dialog(fragmentActivity.getApplicationContext());
        eigenschaftbeschreibungsDialog.setContentView(R.layout.eigenschaftsbeschreibung_layoutdialog);

        nameeigenschaft_Dialog = eigenschaftbeschreibungsDialog.getWindow().findViewById(R.id.nameeigenschaft_dialog);
        beschreibungeigenschaft_Dialog = eigenschaftbeschreibungsDialog.getWindow().findViewById(R.id.beschreibungeigenschaft_dialog);


        return new EigenschaftenHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EigenschaftenHolder eigenschaftenHolder, final int i) {


        eigenschaftenHolder.eigenschaft_name.setText(data.get(i).getName());
        Picasso.get().load(data.get(i).getPhotourl()).transform(new CropCircleTransformation()).into(eigenschaftenHolder.eigenschafte_Bild);



        //Wenn man auf eine Eigenschaft drückt, soll ein Dialog erscheinen, wo wie besonderheiten der Eigenschaft stehen
        eigenschaftenHolder.eigenschafte_Bild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                nameeigenschaft_Dialog.setText(data.get(i).getName());
                beschreibungeigenschaft_Dialog.setText(data.get(i).getBeschreibung());


                eigenschaftbeschreibungsDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
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
