package com.hsu.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.net.URL;

import com.hsu.android.popularmovies.utils.JsonParse;
import com.hsu.android.popularmovies.utils.NetworkUtils;

/**
 * Main page with all the movie posters
 * Will call IMDB API for most popular or highest rating
 */
public class MainActivity extends AppCompatActivity {

    private static final String NA = "Not Available";

    private static GridView moviePostersGrid;
    private static TextView


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

        /**
         * Build endpoint, send http request, parse json request to string array
         * @param params
         * @return String[]
         */

        @Override
        protected String[] doInBackground(String... params) {

            // determine which API to call: ratings or popularity
            String api = null;
            if (params[0].equals("ratings")) {
                api = "top_rated";
            } else if (params[0].equals("popularity")) {
                api = "popular";
            } else {
                api = "details";
            }

            // send request
            URL endpoint = NetworkUtils.buildEndpoint(api);
            try {
                String jsonString = NetworkUtils.getHttpResponse(endpoint);
                String[] detailsArray = JsonParse.parseThatJson(jsonString, api);
                return detailsArray;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        /**
         * Read from array and display movie info
         * 0 - movie id, 1 - title, 2 - image link, 3 - plot, 4 - rating, 5 - release date, 6 - top_rated/popular/details
         * @param strings
         */
        @Override
        protected void onPostExecute(String[] info) {

            // last element of array determines which view to populate
            if (info[info.length-1].equals("top_rated") || info[info.length-1].equals("popular")) {

            } else {
            }


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
