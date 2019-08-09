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

            JSONObject podcastObj = results.getJSONObject(i);

            String name = podcastObj.has("title_original") ? podcastObj.getString("title_original") : null;
            String author = podcastObj.has("publisher_original") ? podcastObj.getString("publisher_original") : null;
            int length = podcastObj.has("audio_length_sec") ? podcastObj.getInt("audio_length_sec") : -1;
            String description = podcastObj.has("description_original") ? podcastObj.getString("description_original") : null;
            String thumbnail = podcastObj.has("thumbnail") ? podcastObj.getString("thumbnail") : null;
            String url = podcastObj.has("audio") ? podcastObj.getString("audio") : null;

            Podcast p = new Podcast(name, author, length, description, thumbnail, url);
            podcasts.add(p);
        }

        return podcasts;
    }
}
