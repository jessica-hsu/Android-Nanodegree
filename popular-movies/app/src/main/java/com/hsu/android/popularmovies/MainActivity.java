package com.hsu.android.popularmovies;

import android.os.AsyncTask;
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
     * Create network requests in the background to connect to IMDB
     */
    public class FetchMovies extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            // determine which API to call: ratings or popularity
            String api = null;
            if (params[0].equals("ratings")) {
                api = "top_rated";
            } else {
                api = "popular";
            }

            // send request
            return null;

        }

        @Override
        protected void onPostExecute(String[] strings) {
            super.onPostExecute(strings);
        }
    }

    /**
     * Calls Top 10 Rated Movies from IMDB when 'Highest Ratings' button clicked
     * endpoint: https://api.themoviedb.org/3/movie/top_rated?api_key=73c51e9ec420508456d018916725686d&language=en-US&page=10
     */
    public void getHighestRatingMovies(View view) {
        String searchParam = "ratings";
        new FetchMovies().execute(searchParam);
    }

    /**
     * Calls Top 10 Most Popular Movies from IMDB when 'Most Popular' button clicked
     * endpoint: https://api.themoviedb.org/3/movie/popular?api_key=73c51e9ec420508456d018916725686d&language=en-US&page=10
     */
    public void getMostPopularMovies(View view) {
        String searchParam = "popularity";
        new FetchMovies().execute(searchParam);
    }
}
