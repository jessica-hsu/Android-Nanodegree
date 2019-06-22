package com.udacity.android.movies;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.udacity.android.movies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Image adapter to put all movie posters in grid form
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Movie> movies;

    public ImageAdapter(Context c) {
        this.mContext = c;

    }

    public int getCount() {
        return movies.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void setMovieDetails(List<Movie> movies) {
        this.movies = movies;
    }


    // create a new ImageView for each item referenced by the Adapter
    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        } else {
            imageView = (ImageView) convertView;
        }

        // load images in
        String imageUrl = "http://image.tmdb.org/t/p/w185/" + movies.get(position).getPoster();
        Picasso.get().load(imageUrl).into(imageView);

        //set onclick
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra("movie_id", movies.get(position).getId());
                intent.putExtra("title", movies.get(position).getTitle());
                intent.putExtra("poster", movies.get(position).getPoster());
                intent.putExtra("plot", movies.get(position).getPlot());
                intent.putExtra("rating", movies.get(position).getRating());
                intent.putExtra("date", movies.get(position).getDate());
                mContext.startActivity(intent);
            }
        });
        return imageView;
    }
}