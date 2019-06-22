package com.udacity.android.movies.tasks;

import android.os.AsyncTask;

import com.udacity.android.movies.model.Video;
import com.udacity.android.movies.utils.JsonUtils;
import com.udacity.android.movies.utils.NetworkUtils;


import java.net.URL;
import java.util.List;

/**
 * Async task to get trailers given movie id. With Room/LiveData/ViewModel implementation
 */
public class FetchVideosTask extends AsyncTask<String, Void, List<Video>> {

    private String id;

    public FetchVideosTask(String id) {
        this.id = id;
    }

    @Override
    protected List<Video> doInBackground(String... params) {
        String movie_id = this.id;
        String api = "videos";
        URL endpoint = NetworkUtils.buildEndpoint(api, movie_id);
        try {
            String httpResponse = NetworkUtils.getHttpResponse(endpoint);
            List<Video> videos = JsonUtils.parseVideos(httpResponse);
            return videos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
