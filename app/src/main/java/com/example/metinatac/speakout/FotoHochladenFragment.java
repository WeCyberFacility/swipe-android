package com.example.metinatac.speakout;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static android.app.Activity.RESULT_OK;

public class FotoHochladenFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;


    private StorageReference mStorageRef;

    private Uri mImageUri;

    private DatabaseReference mDatabaseRef;

    ImageView bildWaehlen;
    Button bildhochladenBtn;
    ImageView fotozeigeniv;
    RecyclerView rvmeineBilder;

    String uploadid;

    ArrayList<Foto> meineBilderListe = new ArrayList<>();

    public int anzahlbilder;

    ProgressBar progressBarBildhochLaden;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fotohochladenfragment_layout, container, false);

        bildWaehlen = view.findViewById(R.id.bildwaehlen);
        bildhochladenBtn = view.findViewById(R.id.hochladenbtn);
        progressBarBildhochLaden = view.findViewById(R.id.progressBarBildhochladen);



        fotozeigeniv = view.findViewById(R.id.bildzeigen);

        rvmeineBilder = view.findViewById(R.id.rvmeinefotosfh);
        rvmeineBilder.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvmeineBilder.setNestedScrollingEnabled(false);


        bildWaehlen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                bildWaehlen.startAnimation(pop);

                openFileChooser();

            }
        });

        bildhochladenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation pop = AnimationUtils.loadAnimation(getContext(), R.anim.pop);
                bildhochladenBtn.startAnimation(pop);


                mStorageRef = FirebaseStorage.getInstance().getReference(HomeActivity.currentNutzer.getId());
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("Nutzer");


                if(HomeActivity.currentNutzer == null){

                    Toast.makeText(getContext(), "kein Team ausgewählt!", Toast.LENGTH_SHORT).show();
                } else {

                    uploadFile();
                }




            }
        });


        anzahlBilderFinden();
        fotosFinden();

        return view;
    }

    private void openFileChooser() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 250ms
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        }, 250);




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            mImageUri = data.getData();





            //Picasso.get().load(mImageUri).into(fotozeigeniv);

            Picasso.get().load(mImageUri).transform(new CropSquareTransformation()).into(fotozeigeniv);

        }
    }

    private String getFileExtension(Uri uri){

        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }

    public void uploadFile(){


        //anzahlBilderFinden();


        if(mImageUri!=null) {


            mDatabaseRef = mDatabaseRef.child(HomeActivity.currentNutzer.getId()).child("Fotos");

            uploadid = mDatabaseRef.push().getKey();

            String id = HomeActivity.currentNutzer.getUsername();
            int neuerIndex = anzahlbilder + 1;
            final StorageReference fileReference = mStorageRef.child(uploadid + "." +  getFileExtension(mImageUri));
            fileReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {

                        throw task.getException();

                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()) {

                        Uri downloadUri = task.getResult();


                        Toast.makeText(getContext(), "Upload erfolgreich", Toast.LENGTH_SHORT).show();



                        Foto upload = new Foto(uploadid, HomeActivity.currentNutzer.getUsername(),
                                downloadUri.toString());


                        mDatabaseRef.child(uploadid).setValue(upload);


                    }

                }
            });

        } else {

            Toast.makeText(getContext(), "Kein Bild gewählt!", Toast.LENGTH_SHORT).show();

        }

    }


    public void fotosFinden() {

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Fotos");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    Foto currentFoto = ds.getValue(Foto.class);

                    meineBilderListe.add(currentFoto);


                }

                rvmeineBilder.setAdapter(new MeineFotosAdapter(meineBilderListe, getActivity()));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }




    public void anzahlBilderFinden() {

        anzahlbilder = 0;

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Nutzer");

        myRef = myRef.child(HomeActivity.currentNutzer.getId()).child("Fotos");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    anzahlbilder++;


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }




}
