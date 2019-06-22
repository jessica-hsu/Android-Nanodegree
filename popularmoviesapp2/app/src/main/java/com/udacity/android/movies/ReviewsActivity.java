package com.udacity.android.movies;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity class to show selected review in details
 */
public class ReviewsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_detail);
        String movieTitle = getIntent().getStringExtra("title");
        String reviewAuthor = getIntent().getStringExtra("author");
        String reviewContent = getIntent().getStringExtra("content");
        populate(movieTitle, reviewAuthor, reviewContent);

    }

    /**
     * Populate another page with review details and content
     * @param title
     * @param author
     * @param content
     */
    private void populate(String title, String author, String content) {
        TextView title_tv = (TextView) findViewById(R.id.reviews_title);
        TextView author_tv = (TextView) findViewById(R.id.reviews_author);
        TextView content_tv = (TextView) findViewById(R.id.reviews_content);

        title_tv.setText("Movie: " + title);
        author_tv.setText("Author: " + author);
        content_tv.setText(content);
    }
}
