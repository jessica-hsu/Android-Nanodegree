package com.udacity.android.podcastbemine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.udacity.android.podcastbemine.ui.PodcastListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getSupportActionBar().hide();
        setContentView(R.layout.activity_search);
        Intent intent = new Intent(this, PodcastListActivity.class);
        startActivity(intent);
    }

}
