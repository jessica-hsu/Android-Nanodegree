package com.udacity.android.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.android.app.model.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 * Main activity. Set onclick listeners to call events
 */
public class MainActivity extends Activity {

    private GridView movieGrid;
    private TextView test;
    private Button popularBtn, ratingsBtn;
    private List<Movie> movieDetails = new ArrayList<>();
    private ImageAdapter imageAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieGrid = (GridView) findViewById(R.id.movie_grid);
        test = (TextView) findViewById(R.id.test);
        popularBtn = (Button) findViewById(R.id.button_popular);
        ratingsBtn = (Button) findViewById(R.id.button_rating);
        imageAdapter = new ImageAdapter(MainActivity.this);

        /**
         * Give buttons functionality when clicked
         */
        popularBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getMovies(MainActivity.this, "popular");

            }
        });

        ratingsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getMovies(MainActivity.this, "top_rated");
            }
        });
    }


    private void getMovies(Context context, String api) {
        FetchMoviesTask fetchMovies = new FetchMoviesTask(context, test, movieGrid);
        fetchMovies.execute(api);
        /*try {
            List<Movie> movieDetails = fetchMovies.execute(api).get();
            imageAdapter.setMovieDetails(movieDetails);
            movieGrid.setAdapter(imageAdapter);
            this.movieGrid.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 Toast.makeText(MainActivity.this, "Image "+position, Toast.LENGTH_SHORT);
                }
            });
        } catch (Exception e) {
            test.setText(e.toString());
        }*/

        /*this.movieDetails = fetchMovies.getMovies();
        this.movieGrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                Movie movie = movieDetails.get(position);
                intent.putExtra("id", position);
                // transfer movie details over to new activity
                intent.putExtra("movie_id", movie.getId());
                intent.putExtra("movie_title", movie.getTitle());
                intent.putExtra("movie_poster", movie.getPoster());
                intent.putExtra("movie_plot", movie.getPlot());
                intent.putExtra("movie_ratings", movie.getRating());
                intent.putExtra("movie_date", movie.getDate());
                startActivity(intent);
            }
        });*/

    }
}
