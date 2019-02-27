package com.udacity.android.movies.tasks;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.android.movies.model.Video;
import com.udacity.android.movies.utils.JsonUtils;
import com.udacity.android.movies.utils.NetworkUtils;


import java.net.URL;
import java.util.List;

/**
 * Async task to get trailers given movie id
 */
public class FetchVideosTask extends AsyncTask<String, Void, List<Video>> {

    private Context context;
    private LinearLayout trailerLayout;
    private TextView noVideosTv;

    public FetchVideosTask(Context c, LinearLayout layout, TextView tv) {
        this.context = c;
        this.trailerLayout = layout;
        this.noVideosTv = tv;
    }

    @Override
    protected List<Video> doInBackground(String... params) {
        String movie_id = params[0];
        String api = "videos";
        NetworkUtils network = new NetworkUtils(context);
        URL endpoint = network.buildEndpoint(api, movie_id);
        try {
            String httpResponse = network.getHttpResponse(endpoint);
            List<Video> videos = JsonUtils.parseVideos(httpResponse);
            return videos;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Video> videos) {
        if (videos != null) {
            if (videos.size() > 0) {
                noVideosTv.setText("Trailers found: " + videos.size());
                /*LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                for (Video v: videos) {
                    Button btn = new Button(context);
                    final String youtubeLink = "https://www.youtube.com/watch?v="+v.getKey();
                    btn.setLayoutParams(layoutParams);
                    btn.setText(v.getName());
                    btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    btn.setTypeface(Typeface.SERIF);
                    btn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Toast.makeText(context, youtubeLink, Toast.LENGTH_SHORT);
                        }
                    });
                }*/
            } else {
                noVideosTv.setVisibility(View.VISIBLE);
            }
        }
    }
}
