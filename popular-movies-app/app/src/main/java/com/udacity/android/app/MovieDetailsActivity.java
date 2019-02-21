package com.udacity.android.app;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Activity to populate view with movie details
 */
public class MovieDetailsActivity extends Activity {

    private static ImageView image;
    private static TextView title;
    private static TextView plot;
    private static TextView date;
    private static TextView ratings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        image = (ImageView) findViewById(R.id.selected_movie);
        title = (TextView) findViewById(R.id.title_tv);
        plot = (TextView) findViewById(R.id.plot_tv);
        date = (TextView) findViewById(R.id.date_tv);
        ratings = (TextView) findViewById(R.id.ratings_tv);

        populateDetails();
    }

    private void populateDetails() {
        String imageUrl = "http://image.tmdb.org/t/p/w185/" + getIntent().getStringExtra("poster");
        Picasso.get().load(imageUrl).into(image);
        title.setText(getIntent().getStringExtra("title"));
        plot.setText(getIntent().getStringExtra("plot"));
        date.setText(getIntent().getStringExtra("date"));
        String rating_txt = getIntent().getStringExtra("rating")+"/10";
        ratings.setText(rating_txt);
    }
}
