package com.udacity.android.baking;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.android.baking.model.DetailsContainer;
import com.udacity.android.baking.model.Ingredient;
import com.udacity.android.baking.model.Recipe;
import com.udacity.android.baking.model.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe allRecipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // get recipe details back
        allRecipeDetails = (Recipe) getIntent().getSerializableExtra("details");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RecipeListActivity.this, "ADD TO HOMESCREEN", Toast.LENGTH_SHORT);
            }
        });

        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.recipe_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, allRecipeDetails, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final RecipeListActivity mParentActivity;
        private final List<Object> DETAILS = new ArrayList<>();
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                Step step = (Step) view.getTag();
                if (mTwoPane) {

                    Bundle arguments = new Bundle();
                    arguments.putSerializable("details", step);
                    //arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, item.id);
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                   // intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, item.id);
                    intent.putExtra("details", step);
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                      Recipe recipeDetails,
                                      boolean twoPane) {
            DetailsContainer mainDetails =
                    new DetailsContainer(recipeDetails.getName(), recipeDetails.getImage(),
                            recipeDetails.getServings(), recipeDetails.getIngredients());
            DETAILS.add(mainDetails);
            for (Step s: recipeDetails.getSteps()) {
                DETAILS.add(s);
            }
            mParentActivity = parent;
            mTwoPane = twoPane;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recipe_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (position == 0) {
                DetailsContainer details = (DetailsContainer) DETAILS.get(position);

                StringBuilder detailString = new StringBuilder();
                detailString.append(details.getName() + "\n");
                detailString.append("Servings: " + details.getServings() + "\nIngredients:\n");
                
                for (int i = 0; i < details.getIngredients().size(); i++) {
                    Ingredient ing = details.getIngredients().get(i);
                    detailString.append((i+1) + ") " + ing.getIngredient()
                            + " - " + ing.getQuantity() + " " + ing.getMeasure() + "\n");
                }
                holder.step_tv.setText(detailString.toString() + "Steps:");
            } else {
                Step step = (Step) DETAILS.get(position);
                holder.step_tv.setText(step.getShortDescription());
                holder.itemView.setTag(step);
                holder.itemView.setOnClickListener(mOnClickListener);
            }
        }

        @Override
        public int getItemCount() {
            return this.DETAILS.size();
        }

        // ViewHolder for the recyclerView item
        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView step_tv;

            ViewHolder(View view) {
                super(view);
                step_tv = (TextView) view.findViewById(R.id.step);
            }
        }

    }
}
