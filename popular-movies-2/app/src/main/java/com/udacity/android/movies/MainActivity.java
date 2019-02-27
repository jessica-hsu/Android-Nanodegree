package com.udacity.android.movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.android.app.R;
import com.udacity.android.movies.tasks.FetchMoviesTask;


/**
 * Main activity. Set onclick listeners to call events
 */
public class MainActivity extends AppCompatActivity {

    private GridView movieGrid;
    private TextView test;
    private Toolbar toolBar;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieGrid = (GridView) findViewById(R.id.movie_grid);

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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getMovies(Context context, String api) {
        boolean internet = checkInternetAccess();
        if (internet) {
            FetchMoviesTask fetchMovies = new FetchMoviesTask(context, test, movieGrid);
            fetchMovies.execute(api);
        } else {
            Toast.makeText(MainActivity.this, R.string.no_internet, Toast.LENGTH_LONG);
        }
    }

    private boolean checkInternetAccess() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }
}
