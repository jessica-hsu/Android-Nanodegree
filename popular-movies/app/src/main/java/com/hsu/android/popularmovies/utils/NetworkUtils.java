package com.hsu.android.popularmovies.utils;
import android.net.Uri;

import java.net.URL;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Helper class to send get request to IMDB API and return JSON response
 */
public final class NetworkUtils {

    private static final String IMDB_URL = "https://api.themoviedb.org/3/movie/";
    private static final String LANGUAGE = "language=en-US";
    private static final String LIMIT = "page=10";
    private static String KEY = null;

    /**
     * Build endpoint with given params
     * @param api
     * @return
     */
    public static URL buildEndpoint(String api) {

        // read config rile for API KEY

        // create uri
        Uri uri = null;
        if (api.equals("top_rated") || api.equals("popular") ) {
            uri = Uri.parse(IMDB_URL+api).buildUpon()
                    .appendQueryParameter("key", KEY)
                    .appendQueryParameter("language", LANGUAGE)
                    .appendQueryParameter("page", LIMIT)
                    .build();
        } else {
            uri = Uri.parse(IMDB_URL+api).buildUpon()
                    .appendQueryParameter("key", KEY)
                    .appendQueryParameter("language", LANGUAGE)
                    .build();
        }

        URL endpoint = null;
        try {
            endpoint = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return endpoint;

    }

    /**
     * Get Http Response from IMDB
     */
    public static String getHttpResponse(URL url) throws IOException {
        
    }
}
