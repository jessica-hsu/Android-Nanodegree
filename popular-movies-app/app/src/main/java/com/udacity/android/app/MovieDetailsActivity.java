package com.udacity.android.app;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

        // Get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);

        image = (ImageView) findViewById(R.id.selected_movie);
        title = (TextView) findViewById(R.id.title_tv);
        plot = (TextView) findViewById(R.id.plot_tv);
        date = (TextView) findViewById(R.id.date_tv);
        ratings = (TextView) findViewById(R.id.ratings_tv);

        populateDetails(i, imageAdapter, position);
    }

    private static void populateDetails(Intent intent, ImageAdapter adapter, int position) {
        image.setImageResource(adapter.mThumbIds[position]);
        title.setText(intent.getExtras().getString("movie_title"));
        plot.setText(intent.getExtras().getString("movie_plot"));
        date.setText(intent.getExtras().getString("movie_date"));
        ratings.setText(intent.getExtras().getString("movie_ratings"));
    }
}
