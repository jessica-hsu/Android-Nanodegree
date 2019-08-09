package com.udacity.android.podcastbemine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.android.podcastbemine.R;

import com.udacity.android.podcastbemine.model.Podcast;
import com.udacity.android.podcastbemine.ui.dummy.DummyContent;
import com.udacity.android.podcastbemine.utils.Constant;

import java.util.List;

/**
 * An activity representing a list of Podcasts. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PodcastDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PodcastListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    TextView error_tv;
    List<Podcast> podcasts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.podcast_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // check if there are error codes in intent
        boolean errorExists = getIntent().hasExtra(Constant.INTENT_LABEL_ERROR);
        if (errorExists) {
            int errorCode = getIntent().getIntExtra(Constant.INTENT_LABEL_ERROR, -1);
            switch (errorCode) {
                case Constant.NO_INTERNET_ERROR:
                    error_tv = findViewById(R.id.podcast_error);
                    error_tv.setText(getResources().getString(R.string.no_internet_error));
                    error_tv.setVisibility(View.VISIBLE);
                    break;
                case Constant.LISTEN_API_ERROR:
                    error_tv = findViewById(R.id.podcast_error);
                    error_tv.setText(getResources().getString(R.string.podcast_api_error));
                    error_tv.setVisibility(View.VISIBLE);
                    break;
                case Constant.NO_RESULTS:
                    error_tv = findViewById(R.id.no_podcasts_found);
                    error_tv.setText(getResources().getString(R.string.no_podcasts_found));
                    error_tv.setVisibility(View.VISIBLE);
                    break;
                default:
                    error_tv = findViewById(R.id.podcast_error);
                    error_tv.setText(getResources().getString(R.string.unknown_error));
                    error_tv.setVisibility(View.VISIBLE);
            }
        } else {
            podcasts = (List<Podcast>) getIntent().getSerializableExtra(Constant.INTENT_LABEL_PODCAST_LIST);
            View recyclerView = findViewById(R.id.podcast_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);
        }


    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, podcasts, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final PodcastListActivity mParentActivity;
        private final List<Podcast> podcasts;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Podcast podcast = (Podcast) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(Constant.INTENT_KEY_PODCAST, podcast);
                    PodcastDetailFragment fragment = new PodcastDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.podcast_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, PodcastDetailActivity.class);
                    intent.putExtra(Constant.INTENT_KEY_PODCAST, podcast);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(PodcastListActivity parent,
                                      List<Podcast> items,
                                      boolean twoPane) {
            podcasts = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.podcast_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.podcast_title_tv.setText(podcasts.get(position).getTitle());

            holder.itemView.setTag(podcasts.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return podcasts.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView podcast_title_tv;

            ViewHolder(View view) {
                super(view);
                podcast_title_tv = view.findViewById(R.id.content);
            }
        }
    }
}
