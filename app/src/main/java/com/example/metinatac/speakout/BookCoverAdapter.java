package com.example.metinatac.speakout;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookCoverAdapter extends RecyclerView.Adapter<BookCoverAdapter.BookCoverHolder> {

    ArrayList<BookCover> data;
    FragmentActivity fragmentActivity;
    SparseBooleanArray itemStateArray = new SparseBooleanArray();


    public BookCoverAdapter(ArrayList<BookCover> data, FragmentActivity fragmentActivity) {

        this.data = data;
        this.fragmentActivity = fragmentActivity;

    }

    @NonNull
    @Override
    public BookCoverHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.bookcoverlist_layout, viewGroup, false);


        return new BookCoverHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookCoverHolder bookCoverHolder, final int i) {


        bookCoverHolder.bookcoverName.setText(data.get(i).getName());

        bookCoverHolder.bookcoverCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int adapterPosition = i;
                if (!itemStateArray.get(adapterPosition, false)) {
                    bookCoverHolder.bookcoverCb.setChecked(true);
                    itemStateArray.put(adapterPosition, true);

                    GeschichteErstellenFragment.auswahlliste.add(data.get(adapterPosition));
                } else {
                    bookCoverHolder.bookcoverCb.setChecked(false);
                    itemStateArray.put(adapterPosition, false);
                    GeschichteErstellenFragment.auswahlliste.remove(data.get(adapterPosition));
                }


            }
        });


        Glide.with(fragmentActivity).load(data.get(i).getUrl()).centerCrop().into(bookCoverHolder.bookcoverImg);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class BookCoverHolder extends RecyclerView.ViewHolder {


        ImageView bookcoverImg;
        TextView bookcoverName;
        CheckBox bookcoverCb;


        public BookCoverHolder(@NonNull View itemView) {
            super(itemView);

            bookcoverImg = itemView.findViewById(R.id.bookcoverimg);
            bookcoverName = itemView.findViewById(R.id.boocovername);
            bookcoverCb = itemView.findViewById(R.id.bookcovercb);
        }


        void bind(int position) {
            // use the sparse boolean array to check
            if (!itemStateArray.get(position, false)) {
                bookcoverCb.setChecked(false);
            } else {
                bookcoverCb.setChecked(true);
            }
        }


    }
}
