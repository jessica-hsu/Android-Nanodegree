package com.udacity.android.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static GridView movieGrid;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieGrid = (GridView) findViewById(R.id.movie_grid);
        movieGrid.setAdapter(new ImageAdapter(this));

        movieGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // Send intent to SingleViewActivity
                Intent i = new Intent(getApplicationContext(), MovieDetailsActivity.class);
                // Pass image index
                i.putExtra("id", position);
                // transfer movie details over to new activity
                i.putExtra("movie_id", "1234");
                i.putExtra("movie_title", "test title "+position);
                i.putExtra("movie_poster", "poster link "+position);
                i.putExtra("movie_plot", "plot "+position);
                i.putExtra("movie_ratings", "ratings "+position);
                i.putExtra("movie_date", "date "+position);

                startActivity(i);
            }
        });
    }


}
