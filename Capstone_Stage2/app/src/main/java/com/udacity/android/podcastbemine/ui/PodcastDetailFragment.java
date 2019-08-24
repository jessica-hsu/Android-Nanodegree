package com.udacity.android.podcastbemine.ui;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.android.podcastbemine.PodcastPlayerWidget;
import com.udacity.android.podcastbemine.R;
import com.udacity.android.podcastbemine.model.Podcast;
import com.udacity.android.podcastbemine.utils.Constant;

/**
 * A fragment representing a single Podcast detail screen.
 * This fragment is either contained in a {@link PodcastListActivity}
 * in two-pane mode (on tablets) or a {@link PodcastDetailActivity}
 * on handsets.
 */
public class PodcastDetailFragment extends Fragment {

    Button widget_btn;
    Button play_btn;
    Podcast podcast;
    ImageView thumbnail;
    TextView author;
    TextView audio_len;
    TextView description;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PodcastDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this.getActivity();
        podcast = (Podcast) getArguments().getSerializable(Constant.INTENT_KEY_PODCAST);
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(podcast.getTitle());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.podcast_detail, container, false);


        play_btn = rootView.findViewById(R.id.details_start_podcast);
        widget_btn = rootView.findViewById(R.id.details_add_widget);

        // set onclick listeners for the buttons
        play_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlayPodcastActivity.class);

                if (podcast.getAudioUrl() != null) {
                    intent.putExtra(Constant.INTENT_LABEL_AUDIO_URL, podcast.getAudioUrl());
                } else {
                    intent.putExtra(Constant.INTENT_LABEL_ERROR, Constant.NO_AUDIO_URL_ERROR);
                }

                startActivity(intent);
            }
        });

        widget_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                addToWidget(podcast.getAudioUrl(), v);
            }
        });

        if (podcast != null) {
           // ((TextView) rootView.findViewById(R.id.podcast_detail)).setText(mItem.details);
            // find those views
            thumbnail = rootView.findViewById(R.id.podcast_thumbnail);
            author = rootView.findViewById(R.id.podcast_author);
            audio_len = rootView.findViewById(R.id.podcast_audio_length);
            description = rootView.findViewById(R.id.podcast_description);

            if (podcast.getAuthor() != null) {
                author.setText(podcast.getAuthor());
            } else  {
                author.setText(Constant.NO_AUTHOR);
            }

            if (podcast.getAudioLength() != -1) {
                int min = podcast.getAudioLength() / 60;
                audio_len.setText(min + " minutes");
            } else  {
                audio_len.setText(Constant.NO_AUDIO_LENGTH);
            }

            if (podcast.getDescription() != null) {
                description.setText(podcast.getDescription());
            } else  {
                description.setText(Constant.NO_DESCRIPTION);
            }

            // set image
            if (podcast.getThumbnail() != null) {
                Picasso.get().load(podcast.getThumbnail()).into(thumbnail);
            }


        } else {
            Log.e(Constant.NO_DETAILS_ERROR_TAG, "missing serializable podcast");
        }

        return rootView;
    }

    private void addToWidget(String url, View view) {
        SharedPreferences pref = getContext().getSharedPreferences(Constant.WIDGET_PREFERENCE, 0);
        SharedPreferences.Editor editor = pref.edit();
        if (pref.getString(Constant.WIDGET_LABEL, null) != null) {
            // remove some old values
            editor.remove(Constant.WIDGET_LABEL);
            editor.commit();
        }
        editor.putString(Constant.WIDGET_LABEL, url);
        editor.commit();
        Snackbar.make(view, Constant.WIDGET_ADDED, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        // Put changes on the Widget
        ComponentName provider = new ComponentName(getContext(), PodcastPlayerWidget.class);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
        int[] ids = appWidgetManager.getAppWidgetIds(provider);
        PodcastPlayerWidget podcastWidgetProvider = new PodcastPlayerWidget();
        podcastWidgetProvider.onUpdate(getContext(), appWidgetManager, ids);
    }

}
