package com.udacity.android.movies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.udacity.android.movies.model.Movie;
import com.udacity.android.movies.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class with methods to convert httpresponse json string
 * to JSON object, then convert to List<Movie>
 */
public class JsonUtils {
    public static List<Movie> parse(String details) throws JSONException {

        List<Movie> movies = new ArrayList<Movie>();
        JSONObject mainObj = new JSONObject(details);
        JSONArray results = mainObj.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            String id = results.getJSONObject(i).getString("id");
            String original_title = results.getJSONObject(i).getString("original_title");
            String poster_path = results.getJSONObject(i).getString("poster_path");
            String overview = results.getJSONObject(i).getString("overview");
            String vote_average = results.getJSONObject(i).getString("vote_average");
            String release_date = results.getJSONObject(i).getString("release_date");
            Movie m = new Movie(id, original_title, poster_path, overview, release_date, vote_average);
            movies.add(m);
        }

        return movies;
    }

    /**
     * parse JSON for httpResponse for getting videos
     * @param details
     * @return
     * @throws JSONException
     */
    public static List<Video> parseVideos(String details) throws JSONException {
        List<Video> videos = new ArrayList<Video>();
        JSONObject mainObj = new JSONObject(details);
        String movie_id = mainObj.getString("id");
        JSONArray results = mainObj.getJSONArray("results");
        for (int i = 0; i < results.length(); i++) {
            String key = results.getJSONObject(i).getString("key");
            String name = results.getJSONObject(i).getString("name");
            Video v = new Video(movie_id, key, name);
            videos.add(v);
        }

        return videos;
    }
}