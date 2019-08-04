package com.udacity.android.podcastbemine.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.udacity.android.podcastbemine.MainActivity;
import com.udacity.android.podcastbemine.R;
import com.udacity.android.podcastbemine.model.User;
import com.udacity.android.podcastbemine.utils.Constant;

public class MainMenuActivity extends AppCompatActivity {

    User user;
    TextView greeting_tv;
    Button search_btn;
    Button fav_btn;
    Button logout_btn;

    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        MainActivity main = new MainActivity();
        mGoogleSignInClient = main.getmGoogleSignInClient();

        greeting_tv = findViewById(R.id.main_menu_title);
        search_btn = findViewById(R.id.main_menu_search_btn);
        fav_btn = findViewById(R.id.main_menu_fav_btn);
        logout_btn = findViewById(R.id.main_menu_logout_btn);

        // set greeting with user's name
        user = (User) getIntent().getSerializableExtra(Constant.INTENT_LABEL_USER_INFO);
        String greeting = "Hello, " + user.getName() + ".";
        greeting_tv.setText(greeting);

        // set onclick listeners
        search_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                searchPodcasts();
            }
        });

        fav_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFavorites();
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                googleLogout();
            }
        });
    }

    private void searchPodcasts() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void getFavorites() {
        Intent intent = new Intent(this, PodcastListActivity.class);
        startActivity(intent);
    }

    private void googleLogout() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       // go back to homepage after signing out
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
    }


}
