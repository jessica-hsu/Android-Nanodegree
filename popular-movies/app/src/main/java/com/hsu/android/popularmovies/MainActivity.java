package com.hsu.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

/**
 * Main page with all the movie posters
 * Will call IMDB API for most popular or highest rating
 */
public class MainActivity extends AppCompatActivity {

    private static GridView moviePostersGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // on page load, show most popular movies
    }

    /**
     * Call Top 10 Rated Movies
     * https://api.themoviedb.org/3/movie/top_rated?api_key=73c51e9ec420508456d018916725686d&language=en-US&page=10
     */
    public void getHighestRatingMovies(View view) {

    }

    /**
     * Call Top 10 Most Popular Movies
     * https://api.themoviedb.org/3/movie/popular?api_key=73c51e9ec420508456d018916725686d&language=en-US&page=10
     */
    public void getMostPopularMovies(View view) {

    }
}
