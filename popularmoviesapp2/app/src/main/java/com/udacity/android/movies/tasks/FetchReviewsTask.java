package com.udacity.android.movies.tasks;

import android.os.AsyncTask;

import com.udacity.android.movies.model.Review;
import com.udacity.android.movies.utils.JsonUtils;
import com.udacity.android.movies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

/**
 * Async task in background - will grab reviews given a movie.
 * For Room/ViewModel/LiveData implementation
 */
public class FetchReviewsTask extends AsyncTask<String, Void, List<Review>> {
    private String id;

    public FetchReviewsTask(String id) {
        this.id = id;
    }

    @Override
    protected List<Review> doInBackground(String... params) {
        String movie_id = this.id;
        String api = "reviews";
        URL endpoint = NetworkUtils.buildEndpoint(api, movie_id);
        try {
            String httpResponse = NetworkUtils.getHttpResponse(endpoint);
            List<Review> reviews = JsonUtils.parseReviews(httpResponse);
            return reviews;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
