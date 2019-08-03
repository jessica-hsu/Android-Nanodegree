package com.udacity.android.podcastbemine.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.udacity.android.podcastbemine.R;
import com.udacity.android.podcastbemine.model.User;
import com.udacity.android.podcastbemine.utils.Constant;

public class MainMenuActivity extends AppCompatActivity {

    User user;
    TextView greeting_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        greeting_tv = findViewById(R.id.main_menu_title);
        user = (User) getIntent().getSerializableExtra(Constant.INTENT_LABEL_USER_INFO);

        // set greeting with user's name
        String greeting = "Hello, " + user.getName() + ".";
        greeting_tv.setText(greeting);

        // set onclick listeners
    }
}
