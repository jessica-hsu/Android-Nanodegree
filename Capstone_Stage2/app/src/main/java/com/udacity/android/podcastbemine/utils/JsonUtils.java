package com.udacity.android.podcastbemine.utils;

import com.udacity.android.podcastbemine.model.Podcast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Podcast> parse(String details) throws JSONException {

        List<Podcast> podcasts = new ArrayList<Podcast>();

        JSONObject mainObj = new JSONObject(details);
        JSONArray results = mainObj.getJSONArray("results");

        for (int i = 0; i < results.length(); i++) {
            String name = results.getJSONObject(i).getString("title_original");
            String author = results.getJSONObject(i).getString("publisher_original");
            int length = results.getJSONObject(i).getInt("audio_length_sec");
            String description = results.getJSONObject(i).getString("description_original");
            String thumbnail = results.getJSONObject(i).getString("thumbnail");
            String url = results.getJSONObject(i).getString("audio");

            Podcast p = new Podcast(name, author, length, description, thumbnail, url);
            podcasts.add(p);
        }

        return podcasts;
    }
}
