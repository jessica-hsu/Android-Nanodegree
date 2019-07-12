package com.udacity.android.bakingrecipes;

import android.app.Activity;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.udacity.android.bakingrecipes.model.Step;


/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link RecipeListActivity}
 * in two-pane mode (on tablets) or a {@link RecipeDetailActivity}
 * on handsets.
 */
public class RecipeDetailFragment extends Fragment {

    /**
     * The Step content this fragment is presenting.
     */
    private Step step;
    private String url;

    PlayerView playerView;
    SimpleExoPlayer player;
    DefaultBandwidthMeter bandwidthMeter;
    TrackSelector trackSelector;
    DataSource.Factory dataSourceFactory;
    MediaSource videoSource;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        step = (Step) getArguments().getSerializable("details");
        if (appBarLayout != null) {
            appBarLayout.setTitle(step.getShortDescription());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (step != null) {

            ((TextView) rootView.findViewById(R.id.recipe_detail)).setText(step.getDescription());
            if (step.getVideoURL().length() > 0 || step.getThumbnailURL().length() > 0) {

                if (step.getVideoURL().length() > 0) {
                    url = step.getVideoURL();
                } else if (step.getThumbnailURL().length() > 0) {
                    url = step.getThumbnailURL();
                }

                playerView = rootView.findViewById(R.id.video_player);
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

                setUpPlayer();
            }

        }

        return rootView;
    }

    private void setUpPlayer() {

        bandwidthMeter = new DefaultBandwidthMeter();
        trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        playerView.setPlayer(player);
        dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "BakingRecipe"), bandwidthMeter);

        Uri videoURI = Uri.parse(url);
        videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoURI);

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
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

   /* @Override
    public void onStart() {
        super.onStart();
        setUpPlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }*/
}
