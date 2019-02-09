package com.udacity.android.app.utils;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.InputStream;
import java.util.Properties;

/**
 * Helper class to read the config file that contains IMDB API KEY
 */
public class AssetsReader {
    private Context context;
    private Properties properties;

    public AssetsReader(Context context) {
        this.context = context;
        this.properties = new Properties();
    }

    public Properties getProperties(String name) {
        try {
            AssetManager manager = this.context.getAssets();
            InputStream input = manager.open(name);
            this.properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.properties;
    }
}
