package com.udacity.android.app.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.udacity.android.app.model.Movie;

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
        JSONObject[] results = (JSONObject[]) mainObj.get("results");
        for (int i = 0; i < results.length; i++) {
            String id = results[i].getString("id");
            String original_title = results[i].getString("original_title");
            String poster_path = results[i].getString("poster_path");
            String overview = results[i].getString("overview");
            String vote_average = results[i].getString("vote_average");
            String release_date = results[i].getString("release_date");
            Movie m = new Movie(id, original_title, poster_path, overview, release_date, vote_average);
            movies.add(m);
        }

        return movies;
    }
}