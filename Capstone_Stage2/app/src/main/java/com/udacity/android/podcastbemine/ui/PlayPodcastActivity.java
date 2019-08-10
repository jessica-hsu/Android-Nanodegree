package com.udacity.android.podcastbemine.ui;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.android.podcastbemine.R;
import com.udacity.android.podcastbemine.utils.Constant;

public class PlayPodcastActivity extends AppCompatActivity {

    TextView error_tv;
    String audioUrl;

    PlayerView playerView;
    SimpleExoPlayer player;
    DefaultBandwidthMeter bandwidthMeter;
    TrackSelector trackSelector;
    DataSource.Factory dataSourceFactory;
    MediaSource videoSource;
    DefaultHttpDataSourceFactory httpDataSourceFactory;
    long playerPosition;
    boolean playerReady;
    int playerWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_podcast);

        error_tv = findViewById(R.id.play_error);
        playerView = findViewById(R.id.podcast_exoplayer);

        // check for internet and error codes
        if (!checkInternetAccess()) {
            error_tv.setText(getResources().getString(R.string.no_internet_error));
            error_tv.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
        } else if (getIntent().hasExtra(Constant.INTENT_LABEL_ERROR)) {
            int errorCode = getIntent().getIntExtra(Constant.INTENT_LABEL_ERROR, -1);
            if (errorCode == Constant.NO_AUDIO_URL_ERROR) {
                error_tv.setText(getResources().getString(R.string.play_podcast_no_url));
            } else {
                error_tv.setText(getResources().getString(R.string.unknown_error));
            }
            error_tv.setVisibility(View.VISIBLE);
            playerView.setVisibility(View.GONE);
        } else if (getIntent().hasExtra(Constant.INTENT_LABEL_AUDIO_URL)) {
            audioUrl = getIntent().getStringExtra(Constant.INTENT_LABEL_AUDIO_URL);
        }

        if (savedInstanceState != null) {
            playerReady = savedInstanceState.getBoolean(Constant.INSTANCE_KEY_STATE);
            playerPosition = savedInstanceState.getLong(Constant.INSTANCE_KEY_POSITION);
            playerWindow = savedInstanceState.getInt(Constant.INSTANCE_KEY_WINDOW);
        } else {
            playerReady = true;
            playerPosition = 0;
        }
        Log.i("myAudio", audioUrl);
    }

    private boolean checkInternetAccess() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private void setUpPlayer() {
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
        bandwidthMeter = new DefaultBandwidthMeter();
        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        playerView.setPlayer(player);
        httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(this, Constant.APP_NAME),
                null /* listener */,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true);

        dataSourceFactory = new DefaultDataSourceFactory(this,
                null,
                httpDataSourceFactory);
        String url = audioUrl;
        Uri videoURI = Uri.parse(url);
        videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoURI);

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        playerPosition =player.getCurrentPosition();
        outState.putLong(Constant.INSTANCE_KEY_POSITION, playerPosition );
        playerReady = player.getPlayWhenReady();
        outState.putBoolean(Constant.INSTANCE_KEY_STATE, playerReady);
        playerWindow = player.getCurrentWindowIndex();
        outState.putInt(Constant.INSTANCE_KEY_WINDOW, playerWindow);
        super.onSaveInstanceState(outState);
    }

    private void releasePlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            playerWindow = player.getCurrentWindowIndex();
            playerReady = player.getPlayWhenReady();
            player.stop();
            player.release();
            player = null;
            dataSourceFactory = null;
            videoSource = null;
            trackSelector = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setUpPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            setUpPlayer();
        }
    }

}
