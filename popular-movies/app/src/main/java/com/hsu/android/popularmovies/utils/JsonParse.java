package com.hsu.android.popularmovies.utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Parse json string and convert to string array
 * Indices:
 * 0 - movie id, 1 - title, 2 - image link, 3 - plot, 4 - rating, 5 - release date
 */
public class JsonParse {

    private static String[] MOVIE_DETAILS = new String[6];

    public static String[] parseThatJson(String details, String api) throws JSONException {

        JSONObject mainObj = new JSONObject(details);
        if (api.equals("top_rated") || api.equals("popular")) {
            parsePopularityAndRatings(mainObj, api);
        } else {
            parseDetails(mainObj);
        }

        return MOVIE_DETAILS;
    }

    private static void parsePopularityAndRatings(JSONObject obj, String api) throws JSONException {
        JSONObject[] arr = (JSONObject[]) obj.get("results");

        for (JSONObject movie : arr) {
            MOVIE_DETAILS[0] = movie.getString("id");
            MOVIE_DETAILS[1] = movie.getString("original_title");
            MOVIE_DETAILS[2] = movie.getString("poster_path");
            MOVIE_DETAILS[3] = movie.getString("overview");
            MOVIE_DETAILS[4] = movie.getString("vote_average");
            MOVIE_DETAILS[5] = movie.getString("release_date");
            MOVIE_DETAILS[6] = api;
        }
    }

    private static void parseDetails(JSONObject movie) throws JSONException {
        MOVIE_DETAILS[0] = movie.getString("id");
        MOVIE_DETAILS[1] = movie.getString("original_title");
        MOVIE_DETAILS[2] = movie.getString("poster_path");
        MOVIE_DETAILS[3] = movie.getString("overview");
        MOVIE_DETAILS[4] = movie.getString("vote_average");
        MOVIE_DETAILS[5] = movie.getString("release_date");
        MOVIE_DETAILS[6] = "details";
    }
}
