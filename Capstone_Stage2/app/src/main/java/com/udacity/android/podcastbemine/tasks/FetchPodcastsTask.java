package com.udacity.android.podcastbemine.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;

import com.udacity.android.podcastbemine.model.Podcast;
import com.udacity.android.podcastbemine.utils.Constant;
import com.udacity.android.podcastbemine.utils.JsonUtils;
import com.udacity.android.podcastbemine.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchPodcastsTask extends AsyncTask<String, Void, List<Podcast>> {

    private ProgressDialog progressDialog;
    private Context context;

    public FetchPodcastsTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting podcasts ...");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected List<Podcast> doInBackground(String... strings) {
        List<Podcast> podcasts = new ArrayList<>();
        if (strings.length == 2) {
            String keyword = strings[0] != null ? strings[0] : "";
            String type = strings[1] != null ? strings[1] : "episode";
            URL endpoint = NetworkUtils.buildEndpoint(keyword, type);
            if (endpoint != null) {
                try {
                    String httpResponse = NetworkUtils.getHttpResponse(endpoint);
                    podcasts = JsonUtils.parse(httpResponse);
                } catch (Exception e) {
                    Log.e(Constant.FETCH_TASKS_ERROR_TAG, e.getStackTrace().toString());
                    e.printStackTrace();
                    podcasts = null;
                }
            }
        } else {
            podcasts = null;
        }
        return podcasts;
    }

    @Override
    protected void onPostExecute(List<Podcast> podcasts) {
        progressDialog.dismiss();
        super.onPostExecute(podcasts);
    }
}
