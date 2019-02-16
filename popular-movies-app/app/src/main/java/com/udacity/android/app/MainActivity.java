package com.udacity.android.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


/**
 * Main activity. Set onclick listeners to call events
 */
public class MainActivity extends Activity {

    private GridView movieGrid;
    private TextView test;
    private Button popularBtn, ratingsBtn;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieGrid = (GridView) findViewById(R.id.movie_grid);
        popularBtn = (Button) findViewById(R.id.button_popular);
        ratingsBtn = (Button) findViewById(R.id.button_rating);

        // by default when loading, show most popular movies
        getMovies(MainActivity.this, "popular");

        /**
         * Give buttons functionality when clicked
         */
        popularBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getMovies(MainActivity.this, "popular");

            }
        });

        ratingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getMovies(MainActivity.this, "top_rated");
            }
        });
    }


    private void getMovies(Context context, String api) {
        FetchMoviesTask fetchMovies = new FetchMoviesTask(context, test, movieGrid);
        fetchMovies.execute(api);
    }
}
