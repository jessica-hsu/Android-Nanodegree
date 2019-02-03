package com.hsu.android.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Adapter to fill the grid with images
 * Followed Google's Android Developer Guide on GridView
 */

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private String[] info;

    // info - holds 10 images
    public ImageAdapter(Context c/*, String[] info*/) {
        this.context = c;
        //this.info = info;
    }

    public int getCount() {
        return this.info.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // creates the image for each grid item
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView poster;

        if (convertView == null) {
            poster = new ImageView(this.context);
            poster.setLayoutParams(new GridView.LayoutParams(85, 85));
            poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
            poster.setPadding(8, 8, 8, 8);
        }
        else
        {
            poster = (ImageView) convertView;
        }

        // set full poster path name and insert to view
        /*String poster_url = this.info[position].split("~")[1];
        String imageEndpoint = "http://image.tmdb.org/t/p/w185/" + poster_url;*/
        // http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
        // Picasso.get().load(imageEndpoint).into(poster);

        poster.setImageResource(mThumbIds[position]);
        return poster;

    }

    public Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
}
