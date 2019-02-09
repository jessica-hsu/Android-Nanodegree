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
        movieDetailsHash = fetchMovies.getMovieDetails();
        //test.setText(movieDetailsHash.get(0).getTitle());
        movieGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Send intent to SingleViewActivity
                Intent i = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                // Pass image index
                /*i.putExtra("id", position);
                // transfer movie details over to new activity
                i.putExtra("movie_id", movieDetailsHash.get(position).getId());
                i.putExtra("movie_title", movieDetailsHash.get(position).getTitle());
                i.putExtra("movie_poster", movieDetailsHash.get(position).getPoster());
                i.putExtra("movie_plot", movieDetailsHash.get(position).getPlot());
                i.putExtra("movie_ratings", movieDetailsHash.get(position).getRating());
                i.putExtra("movie_date", movieDetailsHash.get(position).getDate());*/

                i.putExtra("id", position);
                // transfer movie details over to new activity
                i.putExtra("movie_id", "test id");
                i.putExtra("movie_title", "test title");
                i.putExtra("movie_poster", "test poster");
                i.putExtra("movie_plot", "test plot");
                i.putExtra("movie_ratings", "test ratings");
                i.putExtra("movie_date", "test date");

                startActivity(i);
            }
        });
    }
}
