package com.udacity.android.app.utils;
import android.content.Context;
import android.net.Uri;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Properties;
/**
 * Helper class with methods to call external API
 */
public class NetworkUtils {
    private static final String IMDB_URL = "https://api.themoviedb.org/3/movie/";
    private static final String LANGUAGE = "en-US";
    private static String KEY = null;

    private Context context;
    private AssetsReader assetsReader;
    private Properties prop;

    public NetworkUtils(Context context) {
        this.context = context;
    }
    /**
     * Build endpoint with given params
     * @param api
     * @return
     */
    public URL buildEndpoint(String api) {

        this.assetsReader = new AssetsReader(this.context);
        this.prop = this.assetsReader.getProperties("config.properties");
        KEY = this.prop.getProperty("key");

        // create uri
        Uri uri = Uri.parse(IMDB_URL+api).buildUpon()
                    .appendQueryParameter("api_key", KEY)
                    .appendQueryParameter("language", LANGUAGE)
                    .build();

        URL endpoint = null;
        System.out.print("ENDPOINT: " + uri.toString());
        try {
            endpoint = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return endpoint;

    }

    /**
     * Build endpoint to get videos with movie id
     * @param api, movie_id
     * @return
     * https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key={api_key}&language=en-US
     */
    public URL buildEndpoint(String api, String movie_id) {

        this.assetsReader = new AssetsReader(this.context);
        this.prop = this.assetsReader.getProperties("config.properties");
        KEY = this.prop.getProperty("key");

        // create uri
        Uri uri = Uri.parse(IMDB_URL+movie_id+"/"+api).buildUpon()
                .appendQueryParameter("api_key", KEY)
                .appendQueryParameter("language", LANGUAGE)
                .build();

        URL endpoint = null;
        System.out.print("ENDPOINT: " + uri.toString());
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
    public String getHttpResponse(URL url) throws IOException {
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
