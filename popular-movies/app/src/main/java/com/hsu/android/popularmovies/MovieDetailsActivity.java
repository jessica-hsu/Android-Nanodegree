package com.hsu.android.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Display movie details on a separate pop up page when you tap on movie poster.
 * Details include original title, movie poster image thumbnail, plot synopsis (overview),
 * user rating (vote_average), release date
 * Will call IMDB API for movie details
 */
public class MovieDetailsActivity extends Activity {

    /**
     * Get movie details
     * https://api.themoviedb.org/3/movie/{movie_id}?api_key=73c51e9ec420508456d018916725686d&language=en-US
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);

        ImageView imageView = (ImageView) findViewById(R.id.image_iv);
        imageView.setImageResource(imageAdapter.mThumbIds[position]);
    }
}
