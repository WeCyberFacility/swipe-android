package com.example.metinatac.speakout;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SignInACTIVITY";
    EditText nameEingabe;
    EditText nachnameEingabe;
    EditText emailEingabe;
    EditText passwortEingabe;
    EditText passwortRepeatEingabe;
    EditText benutzernameEingabe;
    Button registerbtn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEingabe = findViewById(R.id.googleUserName);
        nachnameEingabe = findViewById(R.id.RegisterSurnameIn);
        emailEingabe = findViewById(R.id.RegisterEmailIn);
        passwortEingabe = findViewById(R.id.RegisterPasswordIn);
        passwortRepeatEingabe = findViewById(R.id.REgisterPassRepeat);
        registerbtn = findViewById(R.id.registerBtn);
        benutzernameEingabe = findViewById(R.id.RegisterBenutzername);
        mAuth = FirebaseAuth.getInstance();


        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                registrieren();

            }
        });


    }

    public static String verschluesselPW(String inputPW) {

        int count = 0;

        String hashedPW;

        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü";

        String doubleAllChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü";


        char[] hashedChars = new char[inputPW.length()];


        for (int i = 0; i < inputPW.length(); i++) {

            for (int j = 0; j < allChars.length(); j++) {


                if (inputPW.charAt(i) == allChars.charAt(j)) {

                    if (i % 2 == 0) {
                        hashedChars[i] = doubleAllChars.charAt(i + j + 8);
                        break;
                    } else {
                        hashedChars[i] = doubleAllChars.charAt(i + j + 4);
                        break;
                    }


                } else {
                    count++;


                    if (count == allChars.length()) {
                        hashedChars[i] = inputPW.charAt(i);
                        count = 0;
                    }

                }

            }
        }

        hashedPW = String.valueOf(hashedChars);
        Log.d(TAG, hashedPW);

        return hashedPW;
    }


    public void registrieren() {


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Nutzer");

        if (nameEingabe.getText().toString().equals("") == true ||
                nachnameEingabe.getText().toString().equals("") == true ||
                emailEingabe.getText().toString().equals("") == true ||
                passwortEingabe.getText().toString().equals("") == true ||
                passwortRepeatEingabe.getText().toString().equals("") == true ||
                benutzernameEingabe.getText().toString().equals("") == true) {

            //wenn eins der Felder leer sein sollte, so gebe eine Fehlermeldung aus!

            Toast.makeText(this, "Bitte fülle alle Felder aus!", Toast.LENGTH_SHORT).show();

//
        } else {

            final String nameeingabe = nameEingabe.getText().toString().trim();
            final String nachnameeingabe = nachnameEingabe.getText().toString().trim();
            final String emaileingabe = emailEingabe.getText().toString().trim();
            final String benutzernameeingabe = benutzernameEingabe.getText().toString().trim();
            final String passworteingabe = passwortEingabe.getText().toString().trim();
            String passworteingaberepeat = passwortRepeatEingabe.getText().toString().trim();

            if (passworteingabe.equals(passworteingaberepeat) == false) {

                Toast.makeText(this, "Die eingegebenen Passwörter stimmen nicht überein!", Toast.LENGTH_SHORT).show();

            } else {


                mAuth.createUserWithEmailAndPassword(emaileingabe, passworteingabe).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Intent myIntent = new Intent(RegisterActivity.this, HomeActivity.class);
                            startActivity(myIntent);
                            finish();

                            FirebaseUser user = mAuth.getCurrentUser();


                            Nutzer neuerNutzer = new Nutzer(mAuth.getCurrentUser().getUid(), nameeingabe, nachnameeingabe, benutzernameeingabe, verschluesselPW(passworteingabe), emaileingabe, 0);
                            myRef.child(mAuth.getCurrentUser().getUid()).child("Daten").setValue(neuerNutzer);


                        } else {

                            Toast.makeText(RegisterActivity.this, "Registrierung Fehlgeschlagen!", Toast.LENGTH_SHORT).show();

                        }


                    }
                });


                // String idd = myRef.push().getKey();


            }


        }


    }


    private String entschluesselPW(String PW) {
        int count = 0;

        String hashedPW;

        String allChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü";

        String reversedAll = new StringBuilder(allChars).reverse().toString();

        String doubleAllChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.-öäü";

        String reversedDoubleAll = new StringBuilder(doubleAllChars).reverse().toString();

        char[] hashedChars = new char[PW.length()];


        for (int i = 0; i < PW.length(); i++) {

            for (int j = 0; j < reversedAll.length(); j++) {


                if (PW.charAt(i) == reversedAll.charAt(j)) {

                    if (i % 2 == 0) {
                        hashedChars[i] = reversedDoubleAll.charAt(i + j + 8);
                        break;
                    } else {
                        hashedChars[i] = reversedDoubleAll.charAt(i + j + 4);
                        break;
                    }


                } else {
                    count++;


                    if (count == reversedAll.length()) {
                        hashedChars[i] = PW.charAt(i);
                        count = 0;
                    }

                }

            }
        }

        hashedPW = String.valueOf(hashedChars);
        Log.d(TAG, hashedPW);

        return hashedPW;
    }


}
