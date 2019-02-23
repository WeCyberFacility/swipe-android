package com.example.metinatac.speakout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ChapterOeffnenFragment extends Fragment {

    static Kapitel currentKapitel;

    TextView nameOc;
    TextView readingtimeOc;
    TextView breefsummaryOc;
    TextView contenOc;
    ImageView commentsBtn;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapteroeffnenfragment_layout, container, false);

        nameOc = view.findViewById(R.id.nameco);
        readingtimeOc = view.findViewById(R.id.readingtimeco);
        breefsummaryOc = view.findViewById(R.id.breefsummaryco);
        contenOc = view.findViewById(R.id.contentco);
        commentsBtn = view.findViewById(R.id.commentsbtn);


        nameOc.setText(currentKapitel.getName());
        readingtimeOc.setText(currentKapitel.getLesedauer() + " min");
        breefsummaryOc.setText(currentKapitel.getKurzbeschreibung());
        contenOc.setText(currentKapitel.getContent());


        commentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





            }
        });





        return view;
    }
}
