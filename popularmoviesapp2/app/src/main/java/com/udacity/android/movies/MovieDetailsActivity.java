package com.udacity.android.movies;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.android.movies.model.Review;
import com.udacity.android.movies.model.Video;
import com.udacity.android.movies.tasks.FetchReviewsTask;
import com.udacity.android.movies.tasks.FetchVideosTask;

import java.util.List;

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
    private static LinearLayout reviewsLayout;
    private static TextView noReviews;

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
        noReviews = (TextView) findViewById(R.id.no_reviews);
        reviewsLayout = (LinearLayout) findViewById(R.id.review_layout);

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

        String movie_id = getIntent().getStringExtra("movie_id");
        getTrailers(movie_id);
        getReviews(movie_id, getIntent().getStringExtra("title"));
    }

    /**
     * Make API call to IMDB to get trailers and populate movie details intent
     * @param movie_id
     */
    @SuppressLint("StaticFieldLeak")
    private void getTrailers(String movie_id) {
        new FetchVideosTask(movie_id) {
            @Override
            protected void onPostExecute(List<Video> videos) {
                if (videos != null) {
                    if (videos.size() == 0) {
                        noVideos.setVisibility(View.VISIBLE);
                    } else if (videos.size() <= 5) {
                        for(Video v: videos) {
                            Button b = new Button(MovieDetailsActivity.this);
                            b.setText(v.getName());
                            final String youtube = "https://www.youtube.com/watch?v=" + v.getKey();
                            b.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(youtube));
                                    startActivity(youtubeIntent);
                                }
                            });
                            trailersLayout.addView(b);
                        }
                    } else {
                        for(int i=0; i<5; i++) {
                            Button b = new Button(MovieDetailsActivity.this);
                            b.setText(videos.get(i).getName());
                            final String youtube = "https://www.youtube.com/watch?v=" + videos.get(i).getKey();
                            b.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,
                                            Uri.parse(youtube));
                                    startActivity(youtubeIntent);
                                }
                            });
                            trailersLayout.addView(b);
                        }
                    }

                }
            }
        }.execute();
    }

    /**
     * Make API call to IMDB to get reviews and populate movie details intent
     * @param movie_id
     */
    @SuppressLint("StaticFieldLeak")
    private void getReviews(String movie_id, final String title) {
        new FetchReviewsTask(movie_id) {
            @Override
            protected void onPostExecute(List<Review> reviews) {
                if (reviews != null) {
                    if (reviews.size() == 0) {
                        noReviews.setVisibility(View.VISIBLE);
                    } else if (reviews.size() <= 5) {
                        int index = 1;
                        for(Review r: reviews) {
                            Button b = new Button(MovieDetailsActivity.this);
                            final String movieTitle = title;
                            final String author = r.getAuthor();
                            final String content = r.getContent();
                            b.setText("Review #" + index);
                            b.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent intent = new Intent(MovieDetailsActivity.this, ReviewsActivity.class);
                                    intent.putExtra("author", author);
                                    intent.putExtra("title", movieTitle);
                                    intent.putExtra("content", content);
                                    startActivity(intent);
                                }
                            });
                            reviewsLayout.addView(b);
                            index++;
                        }

                    } else {
                        for (int i=0; i<5; i++) {
                            Button b = new Button(MovieDetailsActivity.this);
                            final String movieTitle = title;
                            final String author = reviews.get(i).getAuthor();
                            final String content = reviews.get(i).getContent();
                            b.setText("Review #" + (i+1));
                            b.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    Intent intent = new Intent(MovieDetailsActivity.this, ReviewsActivity.class);
                                    intent.putExtra("author", author);
                                    intent.putExtra("title", movieTitle);
                                    intent.putExtra("content", content);
                                    startActivity(intent);
                                }
                            });
                            reviewsLayout.addView(b);
                        }
                    }
                }
            }
        }.execute();
    }
}
