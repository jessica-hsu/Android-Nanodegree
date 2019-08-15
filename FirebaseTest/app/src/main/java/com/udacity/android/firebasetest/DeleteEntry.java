package com.udacity.android.firebasetest;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

/**
 * Delete entry from firebase realtime database
 * Delete from information with userId = something
 */
public class DeleteEntry extends AppCompatActivity {
    TextInputEditText user_input;
    Button delete_btn;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mMessagesDatabaseReference;
    Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_entry);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("information");

        user_input = findViewById(R.id.delete_user);
        delete_btn = findViewById(R.id.delete_now);

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final View v = view;

                final String u = user_input.getText().toString();

                ValueEventListener listener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot d: dataSnapshot.getChildren()) {
                            d.getRef().removeValue();
                        }
                        Snackbar.make(v, "Removed", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        Log.e("BAD_ERROR", databaseError.toString());
                        Snackbar.make(v, "Error", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                };
                query = mMessagesDatabaseReference.orderByChild("userId").equalTo(u);
                query.addListenerForSingleValueEvent(listener);
            }
        });
    }
}
