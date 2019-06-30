package com.udacity.android.baking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.udacity.android.baking.model.Recipe;

public class TestActivity extends AppCompatActivity {

    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        tv = (TextView) findViewById(R.id.test);
        Recipe recipe = (Recipe) getIntent().getSerializableExtra("details");
        String text = recipe.getName() + "\n" + recipe.getServings();
        tv.setText(text);
    }
}
