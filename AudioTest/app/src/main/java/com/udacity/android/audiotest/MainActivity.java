package com.udacity.android.audiotest;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            playerReady = savedInstanceState.getBoolean("state");
            playerPosition = savedInstanceState.getLong("position");
            playerWindow = savedInstanceState.getInt("window");

        } else {
            playerReady = true;
            playerPosition = 0;
        }
        playerView = findViewById(R.id.my_exoplayer);

    }

    private void releasePlayer() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
            dataSourceFactory = null;
            videoSource = null;
            trackSelector = null;
        }
    }

    private void setUpPlayer() {
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

        bandwidthMeter = new DefaultBandwidthMeter();

        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        playerView.setPlayer(player);

        httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(this, "AudioTest"),
                null /* listener */,
                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
                true);

        dataSourceFactory = new DefaultDataSourceFactory(this,
                null,
                httpDataSourceFactory);

        String url = "https://www.listennotes.com/e/p/c937577e50004cc3b8188f5234987b34/";
        Uri videoURI = Uri.parse(url);

        videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoURI);

        player.prepare(videoSource);
        player.setPlayWhenReady(playerReady);
        player.seekTo(playerWindow, playerPosition);
        playerView.setPlayer(player);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        playerPosition =player.getCurrentPosition();
        outState.putLong("position", playerPosition );
        playerReady = player.getPlayWhenReady();
        outState.putBoolean("state", playerReady);
        playerWindow = player.getCurrentWindowIndex();
        outState.putInt("window", playerWindow);
        super.onSaveInstanceState(outState);
    }
}
