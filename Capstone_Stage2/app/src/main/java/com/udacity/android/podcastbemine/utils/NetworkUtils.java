package com.udacity.android.podcastbemine.utils;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {

    public static URL buildEndpoint(String keyword, String type) {

        // create uri
        Uri uri = Uri.parse(Constant.LISTEN_API_URL).buildUpon()
                .appendQueryParameter("q", keyword)
                .appendQueryParameter("type", type)
                .build();

        URL endpoint = null;
        try {
            endpoint = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return endpoint;

    }

    public static String getHttpResponse(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("X-ListenAPI-Key", Constant.LISTEN_KEY);
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
