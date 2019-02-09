package com.udacity.android.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.udacity.android.app.model.Movie;
import com.udacity.android.app.utils.JsonUtils;
import com.udacity.android.app.utils.NetworkUtils;

/**
 * Class to run async tasks in background
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    ProgressDialog progressDialog;
    private ImageAdapter imageAdapter;
    private TextView test;
    private GridView movieGrid;
    private Context context;
    private String error;

    // constructor
    public FetchMoviesTask(Context context, View view, View view2) {
        this.imageAdapter = new ImageAdapter(context);
        this.test = (TextView) view;
        this.context = context;
        this.movieGrid = (GridView) view2;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        String api = params[0];
        NetworkUtils network = new NetworkUtils(context);
        URL endpoint = network.buildEndpoint(api);
        try {
            String httpResponse = network.getHttpResponse(endpoint);
            List<Movie> movies = JsonUtils.parse(httpResponse);
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            error = e.toString();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context,
                "Loading",
                "Getting movies from IMDB...");

    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        progressDialog.dismiss();
        List<String> moviePoster = new ArrayList<String>();
        if (movies != null) {
            for (Movie m: movies) {
                moviePoster.add(m.getPoster());
            }
            imageAdapter.setPosterPaths(moviePoster);
            movieGrid.setAdapter(imageAdapter); // well this works
        } else {
            test.setText(error);
        }
    }

}
