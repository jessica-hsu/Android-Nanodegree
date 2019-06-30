package com.udacity.android.baking;

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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.android.baking.dummy.DummyContent;
import com.udacity.android.baking.model.Ingredient;
import com.udacity.android.baking.model.Recipe;
import com.udacity.android.baking.model.Step;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        private final Recipe recipeDetails;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, item.id);
                    RecipeDetailFragment fragment = new RecipeDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.recipe_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, RecipeDetailActivity.class);
                    intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(RecipeListActivity parent,
                                      Recipe recipeDetails,
                                      boolean twoPane) {
            this.recipeDetails = recipeDetails;
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
        public void onBindViewHolder(final ViewHolder holder, int position) {
            // set recipe title
            holder.title_tv.setText(this.recipeDetails.getName());

            // set recipe servings
            holder.servings_tv.setText("Servings: " + this.recipeDetails.getServings());

            // set recipe ingredients
            StringBuilder ingredientString = new StringBuilder();
            for (int i = 0; i < this.recipeDetails.getIngredients().size(); i++) {
                Ingredient ing = this.recipeDetails.getIngredients().get(i);
                ingredientString.append((i+1) + ") " + ing.getIngredient()
                        + " - " + ing.getQuantity() + " " + ing.getMeasure() + "\n");
            }
            holder.ingredients_tv.setText(ingredientString.toString());

            // set list of recipe steps
            for (int i = 0; i < this.recipeDetails.getSteps().size(); i++) {
                Step currStep = this.recipeDetails.getSteps().get(i);
                Button btn = new Button(mParentActivity);
                btn.setText(currStep.getShortDescription());
                holder.steps_layout.addView(btn);
            }


            //holder.itemView.setTag(recipeDetails.get(position));
            //holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return 1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView title_tv;
            final TextView servings_tv;
            final TextView ingredients_tv;
            final LinearLayout steps_layout;

            ViewHolder(View view) {
                super(view);
                title_tv = (TextView) view.findViewById(R.id.recipe_title);
                servings_tv = (TextView) view.findViewById(R.id.recipe_serving);
                ingredients_tv = (TextView) view.findViewById(R.id.recipe_ingredients);
                steps_layout = (LinearLayout) view.findViewById(R.id.step_list);
            }
        }
    }
}
