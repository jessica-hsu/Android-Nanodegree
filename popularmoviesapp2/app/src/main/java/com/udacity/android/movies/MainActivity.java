package com.udacity.android.movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.android.movies.entity.Movie;
import com.udacity.android.movies.tasks.FetchMoviesTask;

import java.util.ArrayList;
import java.util.List;


/**
 * Main activity. Set onclick listeners to call events
 */
public class MainActivity extends AppCompatActivity {

    private GridView movieGrid;
    private TextView test, noMovies;
    private Toolbar toolBar;
    private AppDatabase database;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieGrid = (GridView) findViewById(R.id.movie_grid);
        noMovies = (TextView) findViewById(R.id.no_movies);

        // by default when loading, show most popular movies
        getMovies(MainActivity.this, "popular");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.popular_movies:
                getMovies(MainActivity.this, "popular");
                return true;
            case R.id.top_rated_movies:
                getMovies(MainActivity.this, "top_rated");
                return true;
            case R.id.your_movies:
                loadYourMovies();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getMovies(Context context, String api) {
        boolean internet = checkInternetAccess();
        if (internet) {
            noMovies.setVisibility(View.INVISIBLE);
            FetchMoviesTask fetchMovies = new FetchMoviesTask(context, test, movieGrid);
            fetchMovies.execute(api);
            movieGrid.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG);
        }
    }

    private boolean checkInternetAccess() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private void loadYourMovies() {
        database = AppDatabase.getDatabase(getApplicationContext());
        List<Movie> yourMovies = database.movieDao().getAll();
        if (yourMovies != null && yourMovies.size() > 0) {
            Toast.makeText(MainActivity.this, ""+yourMovies.size(), Toast.LENGTH_SHORT).show();
            /*ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext());
            List<com.udacity.android.movies.model.Movie> gridMovies = new ArrayList<>();
            for (Movie m : yourMovies) {
                String id = m.movieId;
                String title = m.movieTitle;
                String date = m.movieReleaseDate;
                String plot = m.moviePlot;
                String rating = m.movieRating;
                String poster = m.moviePoster;
                com.udacity.android.movies.model.Movie newMovie =
                        new com.udacity.android.movies.model.Movie(id, title, poster, plot, date, rating);
                gridMovies.add(newMovie);
            }
            imageAdapter.setMovieDetails(gridMovies);
            movieGrid.setAdapter(imageAdapter);*/
        } else {
            noMovies.setVisibility(View.VISIBLE);
            noMovies.setText("No movies available.");
            movieGrid.setVisibility(View.INVISIBLE);
        }

    }
}
