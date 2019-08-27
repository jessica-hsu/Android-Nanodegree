package com.udacity.android.podcastbemine.utils;

import android.util.Log;

import com.udacity.android.podcastbemine.model.Podcast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static List<Podcast> parse(String details) {

        List<Podcast> podcasts = new ArrayList<Podcast>();
        try {
            JSONObject mainObj = new JSONObject(details);
            JSONArray results = mainObj.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {

                JSONObject podcastObj = results.getJSONObject(i);

                String id = podcastObj.has("id") ? podcastObj.getString("id") : null;
                String name = podcastObj.has("title_original") ? podcastObj.getString("title_original") : null;
                String author = podcastObj.has("publisher_original") ? podcastObj.getString("publisher_original") : null;
                int length = podcastObj.has("audio_length_sec") ? podcastObj.getInt("audio_length_sec") : -1;
                String description = podcastObj.has("description_original") ? podcastObj.getString("description_original") : null;
                String thumbnail = podcastObj.has("thumbnail") ? podcastObj.getString("thumbnail") : null;
                String url = podcastObj.has("audio") ? podcastObj.getString("audio") : null;

                Podcast p = new Podcast(id, name, author, length, description, thumbnail, url);
                podcasts.add(p);
            }
        } catch (Exception e) {
            Log.e(Constant.JSON_ERROR_TAG, e.getStackTrace().toString());
        }



        return podcasts;
    }
}
