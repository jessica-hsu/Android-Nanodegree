package com.udacity.android.podcastbemine.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.udacity.android.podcastbemine.MainActivity;
import com.udacity.android.podcastbemine.R;
import com.udacity.android.podcastbemine.model.Podcast;
import com.udacity.android.podcastbemine.model.User;
import com.udacity.android.podcastbemine.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    User user;
    TextView greeting_tv;
    Button search_btn;
    Button fav_btn;
    Button logout_btn;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mGoogleSignInClient = MainActivity.getmGoogleSignInClient();

        greeting_tv = findViewById(R.id.main_menu_title);
        search_btn = findViewById(R.id.main_menu_search_btn);
        fav_btn = findViewById(R.id.main_menu_fav_btn);
        logout_btn = findViewById(R.id.main_menu_logout_btn);

        // set greeting with user's name
        user = (User) getIntent().getSerializableExtra(Constant.INTENT_LABEL_USER_INFO);
        String greeting = "Hello, " + user.getName() + ".";
        greeting_tv.setText(greeting);

        // set onclick listeners
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchPodcasts();
            }
        });

        fav_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFavorites();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                googleLogout(v);
            }
        });
    }

    private void searchPodcasts() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void getFavorites() {
        final List<Podcast> favorites = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(Constant.DB_NAME);
        Query query = databaseReference.orderByChild(Constant.DB_SEARCH_FAVORITES).equalTo(user.getId());
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Intent intent = new Intent(getApplicationContext(), PodcastListActivity.class);
                long count = dataSnapshot.getChildrenCount();
                if (count > 0) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        Podcast p = d.getValue(Podcast.class);
                        favorites.add(p);
                    }
                    intent.putExtra(Constant.INTENT_LABEL_PODCAST_LIST, (Serializable) favorites);
                } else {    // nothing in db
                    intent.putExtra(Constant.INTENT_LABEL_ERROR, Constant.NO_RESULTS);
                }

                startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Intent intent = new Intent(getApplicationContext(), PodcastListActivity.class);
                intent.putExtra(Constant.INTENT_LABEL_ERROR, Constant.DATABASE_ERROR);
                startActivity(intent);
            }
        };
        query.addValueEventListener(listener);

    }

    private void googleLogout(View view) {
        try {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // go back to homepage after signing out
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
        } catch (Exception e) {
            Log.e(Constant.LOGOUT_ERROR_TAG, e.getStackTrace().toString());
            Snackbar.make(view, Constant.LOGOUT_ISSUES_MSG, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }


}
