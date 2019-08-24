package com.udacity.android.podcastbemine.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.udacity.android.podcastbemine.R;
import com.udacity.android.podcastbemine.utils.Constant;

public class SignInErrorActivity extends AppCompatActivity {

    private TextView error_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_error);
        error_tv = findViewById(R.id.sign_in_error);

        int errorCode = getIntent().getIntExtra(Constant.INTENT_LABEL_ERROR, -1);
        switch (errorCode) {
            case Constant.NO_INTERNET_ERROR:
                error_tv.setText(getResources().getString(R.string.no_internet_error));
                break;
            case Constant.GOOGLE_SIGN_IN_ERROR:
                error_tv.setText(getResources().getString(R.string.google_error));
                break;
            default:
                error_tv.setText(getResources().getString(R.string.unknown_error));
        }

    }
}
