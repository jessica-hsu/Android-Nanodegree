package com.udacity.android.app;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.android.app.tasks.FetchMoviesTask;
import com.udacity.android.app.tasks.FetchVideosTask;

/**
 * Activity to populate view with movie details
 */
public class MovieDetailsActivity extends Activity {

    private static ImageView image;
    private static TextView title;
    private static TextView plot;
    private static TextView date;
    private static TextView ratings;
    private static TextView noVideos;
    private static LinearLayout trailersLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        image = (ImageView) findViewById(R.id.selected_movie);
        title = (TextView) findViewById(R.id.title_tv);
        plot = (TextView) findViewById(R.id.plot_tv);
        date = (TextView) findViewById(R.id.date_tv);
        ratings = (TextView) findViewById(R.id.ratings_tv);
        noVideos = (TextView) findViewById(R.id.no_videos);
        trailersLayout = (LinearLayout) findViewById(R.id.trailer_layout);

        String movie_id = getIntent().getStringExtra("movie_id");
        getTrailers(movie_id);
        //getReviews(movie_id);
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

        /*example youtube link: https://www.youtube.com/watch?v=ii3n7hYQOl4
        https://www.youtube.com/watch?v={key}
        https://api.themoviedb.org/3/movie/{movie_id}/videos?api_key={api_key}&language=en-US*/


        /*reviews:
        https://api.themoviedb.org/3/movie/{movie_id}/reviews?api_key={api_key}&language=en-US&page=1
         */

    }

    /**
     * Make API call to IMDB to get trailers and populate movie details intent
     * @param movie_id
     */
    private void getTrailers(String movie_id) {
        FetchVideosTask fetchVideos = new FetchVideosTask(MovieDetailsActivity.this, trailersLayout, noVideos);
        fetchVideos.execute(movie_id);
    }

    /**
     * Make API call to IMDB to get reviews and populate movie details intent
     * @param movie_id
     */
    private void getReviews(String movie_id) {

    }
}
