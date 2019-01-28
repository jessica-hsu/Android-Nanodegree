package com.hsu.android.popularmovies.utils;
import android.net.Uri;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Properties;

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

        // read config file for API KEY
        Properties prop = new Properties();
        try {
            InputStream in = NetworkUtils.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(in);
            KEY = prop.getProperty("key");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

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
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String current=null;
        StringBuilder sb = new StringBuilder();
        while ((current = br.readLine()) != null) {
            sb.append(current+"\n");
        }
        br.close();
        conn.disconnect();
        return sb.toString();
    }
}
