package com.udacity.android.movies;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.android.movies.entity.Movie;
import com.udacity.android.movies.model.Review;
import com.udacity.android.movies.model.Video;
import com.udacity.android.movies.tasks.FetchReviewsTask;
import com.udacity.android.movies.tasks.FetchVideosTask;
import com.udacity.android.movies.viewmodel.MovieViewModel;

import java.util.List;

/**
 * Activity to populate view with movie details
 */
public class MovieDetailsActivity extends Activity {

    private ImageView image;
    private TextView title;
    private TextView plot;
    private TextView date;
    private TextView ratings;
    private TextView noVideos;
    private LinearLayout trailersLayout;
    private LinearLayout reviewsLayout;
    private TextView noReviews;
    private Button likeBtn;
    private AppDatabase database;
    private MovieViewModel movieViewModel;

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
        likeBtn = (Button) findViewById(R.id.favorites_btn);

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        populateDetails();
    }

    private void populateDetails() {
        final String movie_poster = getIntent().getStringExtra("poster");
        final String movie_id = getIntent().getStringExtra("movie_id");
        final String movie_title = getIntent().getStringExtra("title");
        final String movie_date = getIntent().getStringExtra("date");
        final String movie_plot = getIntent().getStringExtra("plot");
        final String movie_rating = getIntent().getStringExtra("rating");
        title.setText(movie_title);
        plot.setText(movie_plot);
        date.setText(movie_date);
        String rating_txt = movie_rating+"/10";
        ratings.setText(rating_txt);
        String imageUrl = "http://image.tmdb.org/t/p/w185/" + movie_poster;
        Picasso.get().load(imageUrl).into(image);
        // prep database and set btn to LIKE or UNLIKE
        database = AppDatabase.getDatabase(getApplicationContext());
        likeBtn.setText(database.toString());
        final boolean exists = (database.movieDao().getMovieById(movie_id) != null) ? true : false;
        if (exists) {
            likeBtn.setText("UNLIKE");
        } else {
            likeBtn.setText("LIKE");
        }
        likeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (exists) {
                    /*database.movieDao().delete(movie_id);*/
                    movieViewModel.deleteMovie(movie_id);
                    likeBtn.setText("LIKE");
                } else {
                    Movie movie = new Movie(movie_id, movie_title, movie_date, movie_rating, movie_plot, movie_poster);
                    movieViewModel.insertMovie(movie);
                    likeBtn.setText("UNLIKE");
                }

            }
        });
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
