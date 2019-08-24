package com.udacity.android.podcastbemine.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.udacity.android.podcastbemine.R;
import com.udacity.android.podcastbemine.model.Podcast;
import com.udacity.android.podcastbemine.tasks.FetchPodcastsTask;
import com.udacity.android.podcastbemine.utils.Constant;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchActivity extends AppCompatActivity implements OnItemSelectedListener {

    private Button search_btn;
    private TextInputEditText keyword_input;
    private Spinner spinner;
    private String keyword;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        createSpinner();

        keyword_input = findViewById(R.id.search_keyword_input);
        spinner = findViewById(R.id.search_option_menu);
        spinner.setOnItemSelectedListener(this);

        search_btn = findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startPodcastSearch();
            }
        });


    }

    private void createSpinner() {
        Spinner spinner = findViewById(R.id.search_option_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_option_menu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void startPodcastSearch() {

        boolean internet = checkInternetAccess();
        // get input from input bar and drop down
        keyword = keyword_input.getText().toString();
        Intent intent = new Intent(this, PodcastListActivity.class);

        if (internet && (keyword != null || !keyword.equals(""))) {

            FetchPodcastsTask fetchPodcastsTask = new FetchPodcastsTask(this);
            List<Podcast> podcasts;
            try {
                podcasts = fetchPodcastsTask.execute(keyword, type).get();
                if (podcasts == null || podcasts.size() < 1) {
                    // empty list. no results found
                    intent.putExtra(Constant.INTENT_LABEL_ERROR, Constant.NO_RESULTS);
                } else {
                    intent.putExtra(Constant.INTENT_LABEL_PODCAST_LIST, (Serializable) podcasts);
                }
                startActivity(intent);
            } catch (ExecutionException | InterruptedException ei) {
                // error calling async task. show error page instead
                ei.printStackTrace();
                Log.e(Constant.SEARCH_ERROR_TAG, ei.getStackTrace().toString());
                intent.putExtra(Constant.INTENT_LABEL_ERROR, Constant.LISTEN_API_ERROR);
                startActivity(intent);
            }

        } else {

            // show no internet
            intent.putExtra(Constant.INTENT_LABEL_ERROR, Constant.NO_INTERNET_ERROR);
            startActivity(intent);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // get selected value from drop down
        type = ((String) parent.getItemAtPosition(position)).toLowerCase();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        type = "podcast";
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
}
