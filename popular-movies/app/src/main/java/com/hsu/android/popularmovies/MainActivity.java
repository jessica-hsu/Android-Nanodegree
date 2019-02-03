package com.hsu.android.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main page with all the movie posters
 * Will call IMDB API for most popular or highest rating
 */
public class MainActivity extends AppCompatActivity {

    private static final String NA = "Not Available";

    private static GridView moviePostersGrid;
    private static TextView title_tv;
    private static TextView plot_tv;
    private static TextView ratings_tv;
    private static TextView date_tv;
    private static ImageView poster_tv;
    private static Button popular_btn;
    private static Button ratings_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set all the textviews, gridview, and imageview
        moviePostersGrid = (GridView) findViewById(R.id.movies_grid);
        title_tv = (TextView) findViewById(R.id.title_tv);
        plot_tv = (TextView) findViewById(R.id.plot_tv);
        ratings_tv = (TextView) findViewById(R.id.ratings_tv);
        date_tv = (TextView) findViewById(R.id.date_tv);
        poster_tv = (ImageView) findViewById(R.id.image_iv);
        ratings_btn = (Button) findViewById(R.id.button_rating);
        popular_btn = (Button) findViewById(R.id.button_popular);

        // on page load, show most popular movies
        //getMostPopularMovies();

        // bind action to button
        ratings_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getHighestRatingMovies();
            }
        });

        popular_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getMostPopularMovies();
            }
        });

        moviePostersGrid.setAdapter(new ImageAdapter(this));
        moviePostersGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(MainActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * Calls Top 10 Rated Movies from IMDB when 'Highest Ratings' button clicked
     */
    public void getHighestRatingMovies() {
        String searchParam = "ratings";
        Toast.makeText(this, searchParam, Toast.LENGTH_SHORT).show();
        //new FetchMovies().execute(searchParam);
    }

    /**
     * Calls Top 10 Most Popular Movies from IMDB when 'Most Popular' button clicked
     */
    public void getMostPopularMovies() {
        String searchParam = "popularity";
        Toast.makeText(this, searchParam, Toast.LENGTH_SHORT).show();
        //new FetchMovies().execute(searchParam);


    }
}
