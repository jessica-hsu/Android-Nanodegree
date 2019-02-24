package com.udacity.android.app.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.net.URL;
import java.util.List;

import com.udacity.android.app.ImageAdapter;
import com.udacity.android.app.model.Movie;
import com.udacity.android.app.utils.JsonUtils;
import com.udacity.android.app.utils.NetworkUtils;

/**
 * Class to run async tasks in background
 */
public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>>  {

    private ProgressDialog progressDialog;
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
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting movies from IMDB...");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        progressDialog.dismiss();
        if (movies != null) {
            imageAdapter.setMovieDetails(movies);
            movieGrid.setAdapter(imageAdapter);
        } else {
            test.setText(error);
        }
    }


}
