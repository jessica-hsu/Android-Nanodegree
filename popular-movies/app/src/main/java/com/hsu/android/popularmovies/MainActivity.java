package com.hsu.android.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
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
    private static TextView title_tv;
    private static TextView plot_tv;
    private static TextView ratings_tv;
    private static TextView date_tv;
    private static ImageView poster_tv;


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

        // on page load, show most popular movies
        getMostPopularMovies();
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
         * details: 0 - movie id, 1 - title, 2 - image link, 3 - plot, 4 - rating, 5 - release date, 6 - top_rated/popular/details
         * top_rated/popular: 0-9: [movie_id]~[poster_path]
         * @param {string array}
         */
        @Override
        protected void onPostExecute(String[] info) {

            // last element of array determines which view to populate
            if (info[info.length-1].equals("details")) {

            } else {
                moviePostersGrid.setAdapter(new ImageAdapter(getApplicationContext(), info));
            }


        }
    }

    /**
     * Calls Top 10 Rated Movies from IMDB when 'Highest Ratings' button clicked
     */
    public void getHighestRatingMovies() {
        String searchParam = "ratings";
        new FetchMovies().execute(searchParam);
    }

    /**
     * Calls Top 10 Most Popular Movies from IMDB when 'Most Popular' button clicked
     */
    public void getMostPopularMovies() {
        String searchParam = "popularity";
        new FetchMovies().execute(searchParam);
    }
}
