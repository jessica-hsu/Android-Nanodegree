package com.udacity.android.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import com.udacity.android.app.model.Movie;

import java.util.HashMap;

/**
 * Main activity. Set onclick listeners to call events
 */
public class MainActivity extends Activity {

    private GridView movieGrid;
    private TextView test;
    private Button popularBtn, ratingsBtn;
    private ImageAdapter imageAdapter;
    private HashMap<Integer, Movie> movieDetailsHash;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieGrid = (GridView) findViewById(R.id.movie_grid);
        test = (TextView) findViewById(R.id.test);
        popularBtn = (Button) findViewById(R.id.button_popular);
        ratingsBtn = (Button) findViewById(R.id.button_rating);

        imageAdapter = new ImageAdapter(this);
        /*movieGrid.setAdapter(imageAdapter);

        movieGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Send intent to SingleViewActivity
                Intent i = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                // Pass image index
                i.putExtra("id", position);
                // transfer movie details over to new activity
                i.putExtra("movie_id", "1234");
                i.putExtra("movie_title", "test title "+position);
                i.putExtra("movie_poster", "poster link "+position);
                i.putExtra("movie_plot", "plot "+position);
                i.putExtra("movie_ratings", "ratings "+position);
                i.putExtra("movie_date", "date "+position);

                startActivity(i);
            }
        });*/

        /**
         * Give buttons functionality when clicked
         */
        popularBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popularMovies(MainActivity.this);
            }
        });

        ratingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ratingsMovies(MainActivity.this);
            }
        });
    }

    private void setClickAction() {

    }

    private void popularMovies(Context context) {
        Toast.makeText(context, "Get Popular Movies",
                Toast.LENGTH_SHORT).show();
        FetchMoviesTask fetchMovies = new FetchMoviesTask(context, test, movieGrid);
        fetchMovies.execute("popular");
    }

    private void ratingsMovies(Context context) {
        Toast.makeText(context, "Get Highest Ratings Movies",
                Toast.LENGTH_SHORT).show();
        FetchMoviesTask fetchMovies = new FetchMoviesTask(context, test, movieGrid);
        fetchMovies.execute("top_rated");
    }


}
