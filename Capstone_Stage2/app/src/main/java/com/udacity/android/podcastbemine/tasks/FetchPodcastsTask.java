package com.udacity.android.podcastbemine.tasks;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.content.Context;
import android.util.Log;

import com.udacity.android.podcastbemine.model.Podcast;
import com.udacity.android.podcastbemine.utils.JsonUtils;
import com.udacity.android.podcastbemine.utils.NetworkUtils;

import java.net.URL;
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
        String keyword = strings[0];
        String type = strings[1];
        URL endpoint = NetworkUtils.buildEndpoint(keyword, type);
        Log.i("MyEndpoint", endpoint.toString());
        try {
            String httpResponse = NetworkUtils.getHttpResponse(endpoint);
            List<Podcast> podcasts = JsonUtils.parse(httpResponse);
            return podcasts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Podcast> podcasts) {
        progressDialog.dismiss();
        super.onPostExecute(podcasts);
    }
}
