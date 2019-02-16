package com.udacity.android.app;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.udacity.android.app.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

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
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
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