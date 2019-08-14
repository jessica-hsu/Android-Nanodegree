package com.udacity.android.firebasetest;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Demo class to search db for a matching key
 * DB structure:
 * information: {
 *     { podcastId: "aaaa", userId: "1111" },
 *     { podcastId: "bbbb", userId: "2222" },
 *     { podcastId: "cccc", userId: "2222" }
 * }
 *
 * Task: Count items in "information" with userId = "2222"
 */
public class SearchEntry extends AppCompatActivity {

    TextInputEditText user_input;
    TextInputEditText podcast_input;
    TextView results_tv;
    Button search_btn;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMessagesDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_entry);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("information");

        user_input = findViewById(R.id.find_user);
        podcast_input = findViewById(R.id.find_podcast);
        search_btn = findViewById(R.id.search_btn);
        results_tv = findViewById(R.id.results);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String u = user_input.getText().toString();
                final String p = podcast_input.getText().toString();

                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        results_tv.setText("Looking for user id: " + u + "\nChildren: " + dataSnapshot.getChildrenCount());
                        user_input.setText("");
                        podcast_input.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.e("BAD_ERROR", databaseError.toString());
                        results_tv.setText("Error searching DB.");
                    }
                };

                mMessagesDatabaseReference.orderByChild("userId")
                        .equalTo(u)
                        .addListenerForSingleValueEvent(listener);
            }
        });
    }
}
