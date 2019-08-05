package com.udacity.android.podcastbemine.ui;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.udacity.android.podcastbemine.R;

public class SearchActivity extends AppCompatActivity implements OnItemSelectedListener {

    Button search_btn;
    TextInputEditText keyword_input;
    Spinner spinner;
    String keyword;
    String type;

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

        // get input from input bar and drop down
        keyword = keyword_input.getText().toString();
    }

    private void createSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.search_option_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.search_option_menu, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void startPodcastSearch() {
        // TODO: start async task and pass data over
        Intent intent = new Intent(this, PodcastListActivity.class);
        startActivity(intent);
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
}
