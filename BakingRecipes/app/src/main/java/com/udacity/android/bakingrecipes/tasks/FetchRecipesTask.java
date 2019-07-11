package com.udacity.android.bakingrecipes.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.udacity.android.bakingrecipes.model.Recipe;
import com.udacity.android.bakingrecipes.utils.JSONUtils;
import com.udacity.android.bakingrecipes.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.List;

/**
 * Async task to get recipes
 */
public class FetchRecipesTask extends AsyncTask<Void, Void, List<Recipe>> {

    private ProgressDialog progressDialog;
    private Context context;

    public FetchRecipesTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Recipe> doInBackground(Void... voids) {
        URL endpoint = NetworkUtils.buildEndpoint();
        try {
            String httpResponse = NetworkUtils.getHttpResponse(endpoint);
            List<Recipe> recipes = JSONUtils.parseJson(httpResponse);
            return recipes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Getting recipes ...");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(List<Recipe> recipes) {
        progressDialog.dismiss();
        super.onPostExecute(recipes);
    }
}
