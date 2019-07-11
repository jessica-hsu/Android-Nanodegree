package com.udacity.android.bakingrecipes.utils;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to call http to get recipes
 */
public class NetworkUtils {

    private static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static final String RECIPE_FILE = "baking.json";

    /**
     * Build endpoint with given params
     * @return URL
     */
    public static URL buildEndpoint() {

        // create uri
        Uri uri = Uri.parse(RECIPE_URL+RECIPE_FILE).buildUpon()
                .build();

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
        String current = null;
        StringBuilder sb = new StringBuilder();
        while ((current = br.readLine()) != null) {
            sb.append(current+"\n");
        }
        br.close();
        conn.disconnect();
        return sb.toString();
    }
}
