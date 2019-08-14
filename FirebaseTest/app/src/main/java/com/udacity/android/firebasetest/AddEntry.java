package com.udacity.android.firebasetest;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Demo - add item to firebase realtime database. Add item to "information"
 */
public class AddEntry extends AppCompatActivity {

    TextInputEditText user_input;
    TextInputEditText podcast_input;
    Button add_btn;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMessagesDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("information");

        user_input = findViewById(R.id.user_id_input);
        podcast_input = findViewById(R.id.podcast_id_input);
        add_btn = findViewById(R.id.add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg;

                String u = user_input.getText().toString();
                String p = podcast_input.getText().toString();

                try {
                    Info info = new Info(u, p);
                    mMessagesDatabaseReference.push().setValue(info);
                    msg = "Added to DB.";

                    user_input.setText("");
                    podcast_input.setText("");
                } catch (Exception e) {
                    msg = e.getStackTrace().toString();
                }

                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}
