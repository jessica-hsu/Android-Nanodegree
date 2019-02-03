package com.hsu.android.popularmovies.utils;
/**
 * Parse json string and convert to string array
 * Indices:
 * 0 - movie id, 1 - title, 2 - image link, 3 - plot, 4 - rating, 5 - release date
 */
public class JsonParse {


    //public static List<Movie> parseThatJson(String details, String api) throws JSONException {

     /*   List<Movie> movies = new ArrayList<Movie>();
        JSONObject mainObj = new JSONObject(details);

        if (api.equals("top_rated") || api.equals("popular")) {
            movies = parsePopularityAndRatings(mainObj, api);
        } else {
            movies = parseDetails(mainObj);
        }

        return movies;
    }

    private List<Movie> void parsePopularityAndRatings(JSONObject obj, String api) throws JSONException {
        JSONObject[] arr = (JSONObject[]) obj.get("results");
        MOVIE_DETAILS = new String[10];

        for (int i = 0; i < arr.length; i++) {
            MOVIE_DETAILS[i] = arr[i].getString("id") + "~" + arr[i].getString("poster_path");
        }

    }

    private List<Movie> void parseDetails(JSONObject movie) throws JSONException {
        MOVIE_DETAILS = new String[7];

        MOVIE_DETAILS[0] = movie.getString("id");
        MOVIE_DETAILS[1] = movie.getString("original_title");
        MOVIE_DETAILS[2] = movie.getString("poster_path");
        MOVIE_DETAILS[3] = movie.getString("overview");
        MOVIE_DETAILS[4] = movie.getString("vote_average");
        MOVIE_DETAILS[5] = movie.getString("release_date");
        MOVIE_DETAILS[6] = "details";
    }*/
}
