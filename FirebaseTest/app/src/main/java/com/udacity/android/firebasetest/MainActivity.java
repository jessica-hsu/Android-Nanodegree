package com.udacity.android.firebasetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Read from firebase realtime database. Return number of items in "information"
 */
public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button btn;
    Button search_btn;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMessagesDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.test);
        btn = findViewById(R.id.add);
        search_btn = findViewById(R.id.search);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEntry.class);
                startActivity(intent);
            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchEntry.class);
                startActivity(intent);
            }
        });
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("information");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               long count = dataSnapshot.getChildrenCount();
               textView.setText("Children: " + count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.e("BAD_ERROR", databaseError.toString());
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Failed to read DB.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        mMessagesDatabaseReference.addValueEventListener(listener);
    }
}
