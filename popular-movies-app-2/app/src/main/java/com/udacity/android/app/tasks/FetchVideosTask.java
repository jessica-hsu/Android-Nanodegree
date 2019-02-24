package com.udacity.android.app.tasks;

import android.os.AsyncTask;

import com.udacity.android.app.model.Movie;

import java.util.List;

public class FetchVideosTask extends AsyncTask<String, Void, List<Movie>> {

    @Override
    protected List<Movie> doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
    }
}
