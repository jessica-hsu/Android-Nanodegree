package com.udacity.android.baking;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.android.baking.model.Recipe;
import com.udacity.android.baking.tasks.FetchRecipesTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private TextView error_tv;
    private LinearLayout homepage_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set variables
        error_tv = (TextView) findViewById(R.id.homepage_error);
        homepage_layout = (LinearLayout) findViewById(R.id.home_page);

        getRecipes();
    }

    /**
     * Fetch Recipes
     */
    private void getRecipes() {
        boolean internet = checkInternetAccess();
        // only make http request if internet connection
        if (internet) {
            FetchRecipesTask fetchMovies = new FetchRecipesTask(MainActivity.this);
            try {
                List<Recipe> recipes = fetchMovies.execute().get();
                loadHomePageUI(recipes);
            } catch (ExecutionException | InterruptedException ei) {
                ei.printStackTrace();
                error_tv.setText("Error fetching recipes");
            }

        } else {
            error_tv.setText("No Internet Connection");
        }
    }

    /**
     * Check for internet access
     * @return boolean
     */
    private boolean checkInternetAccess() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * Load recipe buttons programmatically
     * @param recipes
     */
    private void loadHomePageUI(List<Recipe> recipes) {
        for (int i = 0; i < recipes.size(); i++) {
            Button b = new Button(MainActivity.this);
            final Recipe currentRecipe = recipes.get(i);
            b.setText(recipes.get(i).getName());
            // make each button clickable to master/detail flow
            b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, RecipeListActivity.class);
                    intent.putExtra("details", currentRecipe);
                    startActivity(intent);
                }
            });
            homepage_layout.addView(b);
        }
    }
}
