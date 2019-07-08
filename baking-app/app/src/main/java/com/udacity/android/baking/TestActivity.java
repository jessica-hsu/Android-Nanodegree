package com.udacity.android.baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.udacity.android.baking.model.Recipe;
import com.udacity.android.baking.model.Step;

public class TestActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Step step = (Step) getIntent().getSerializableExtra("details");
        tv = (TextView) findViewById(R.id.test);
        String text = step.getDescription();
        tv.setText(text);
    }
}
