package com.udacity.android.jokelibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);

        textview = (TextView) findViewById(R.id.joke_text);

        Intent intent = getIntent();
        String joke = intent.getStringExtra("joke");

        if (joke != null) {
            textview.setText(joke);
        } else {
            textview.setText("Dig deeped, we gotta find the joke!");
        }

    }
}
