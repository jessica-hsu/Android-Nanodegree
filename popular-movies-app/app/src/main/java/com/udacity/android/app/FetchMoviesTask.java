package com.udacity.android.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Network;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.udacity.android.app.model.Movie;
import com.udacity.android.app.utils.JsonUtils;
import com.udacity.android.app.utils.NetworkUtils;

/**
 * Class to run async tasks in background
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    private ImageAdapter imageAdapter;
    private GridView movieGrid;
    private Context context;

    // constructor
    public FetchMoviesTask(Context context, View view) {
        this.imageAdapter = new ImageAdapter(context);
        this.movieGrid = (GridView) view;
        this.context = context;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        String api = params[0];
        URL endpoint = NetworkUtils.buildEndpoint(api);
        try {
            String httpResponse = NetworkUtils.getHttpResponse(endpoint);
            List<Movie> movies = new ArrayList<Movie>();
            movies = JsonUtils.parse(httpResponse);
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            Toast.makeText(context, "API CALL LIST SIZE: " + movies.size(),
                    Toast.LENGTH_SHORT).show();
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
        }
    }
}
