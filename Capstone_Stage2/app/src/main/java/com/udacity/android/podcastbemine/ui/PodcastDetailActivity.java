package com.udacity.android.podcastbemine.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.Query;

import com.udacity.android.podcastbemine.MainActivity;
import com.udacity.android.podcastbemine.R;
import com.udacity.android.podcastbemine.model.Podcast;
import com.udacity.android.podcastbemine.utils.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * An activity representing a single Podcast detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link PodcastListActivity}.
 */
public class PodcastDetailActivity extends AppCompatActivity {

    Podcast podcast;
    List<Podcast> podcastList;
    String combinedId;
    String userId;
    FloatingActionButton fab;
    String fab_msg;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    boolean inDatabase;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // get stuff from intent
        podcast = (Podcast) getIntent().getSerializableExtra(Constant.INTENT_KEY_PODCAST);
        podcastList = (List<Podcast>) getIntent().getSerializableExtra(Constant.INTENT_LABEL_PODCAST_LIST);
        fab = findViewById(R.id.fab);
        userId = MainActivity.getUserId();
        combinedId = userId + "~" + podcast.getId();
        setUpDatabase();


        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // if count > 0, podcast already in db. mark as true
                // set button image and snackbar message
                long count = dataSnapshot.getChildrenCount();
                if (count > 0) {
                    inDatabase = true;
                    fab_msg = Constant.REMOVE_DATABASE_MSG;
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.baseline_remove_circle_outline_white_36));
                } else {
                    inDatabase = false;
                    fab_msg = Constant.ADD_DATABASE_MSG;
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.baseline_add_circle_outline_white_36));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // if db read failed, assume not in DB
                fab_msg = Constant.ADD_DATABASE_MSG;
                fab.setImageDrawable(getResources().getDrawable(R.drawable.baseline_add_circle_outline_white_36));
            }
        };
        // attach read db listener to find item based on combinedId
        databaseReference.orderByChild(Constant.DB_SEARCH_ID)
                .equalTo(combinedId)
                .addValueEventListener(listener);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v = view;
                if (inDatabase) {   // remove
                    Query query = databaseReference.orderByChild(Constant.DB_SEARCH_ID).equalTo(combinedId);
                    ValueEventListener listener1 = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot d : dataSnapshot.getChildren()) {
                                d.getRef().removeValue();
                            }
                            Snackbar.make(v, fab_msg, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Snackbar.make(v, databaseError.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    };
                    query.addListenerForSingleValueEvent(listener1);
                } else {    // add

                    try {
                        podcast.setUserId(MainActivity.getUserId());
                        databaseReference.push().setValue(podcast);
                    } catch (Exception e) {
                        fab_msg = e.getStackTrace().toString();
                    }
                    Snackbar.make(view, fab_msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {

            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable(Constant.INTENT_KEY_PODCAST, podcast);
            PodcastDetailFragment fragment = new PodcastDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.podcast_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            Intent intent = new Intent(this, PodcastListActivity.class);
            intent.putExtra(Constant.INTENT_LABEL_PODCAST_LIST, (Serializable) podcastList);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpDatabase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(Constant.DB_NAME);
    }

}
