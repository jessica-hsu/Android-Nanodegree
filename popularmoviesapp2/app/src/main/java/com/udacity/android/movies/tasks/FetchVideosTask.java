package com.udacity.android.movies.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.android.movies.model.Video;
import com.udacity.android.movies.utils.JsonUtils;
import com.udacity.android.movies.utils.NetworkUtils;


import java.net.URL;
import java.util.List;

/**
 * Async task to get trailers given movie id
 */
public class FetchVideosTask extends AsyncTask<String, Void, List<Video>> {

    private String id;
    private TextView t;


    public FetchVideosTask(TextView t, String id) {
        this.id = id;
        this.t = t;
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
