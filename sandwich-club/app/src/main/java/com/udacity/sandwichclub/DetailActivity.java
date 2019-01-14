package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final String NA = "NOT AVAILABLE";
    private static final int DEFAULT_POSITION = -1;

    // set variables for layout components
    private ImageView ingredientsIv;
    private TextView sandwichOriginTextView;
    private TextView sandwichDescriptionTextView;
    private TextView sandwichIngredientsTextView;
    private TextView sandwichAlsoKnownAsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // find all the views
        ingredientsIv = (ImageView) findViewById(R.id.image_iv);
        sandwichOriginTextView =  (TextView) findViewById(R.id.origin_tv);
        sandwichDescriptionTextView = (TextView) findViewById(R.id.description_tv);
        sandwichIngredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        sandwichAlsoKnownAsTextView = (TextView) findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);


        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // set place of origin - String
        if (sandwich.getPlaceOfOrigin() == null || sandwich.getPlaceOfOrigin() == "") {
            sandwichOriginTextView.setText(NA);
        } else {
            sandwichOriginTextView.setText(sandwich.getPlaceOfOrigin().toString());
        }


        // set description - String
        if (sandwich.getDescription() == null || sandwich.getDescription() == "") {
            sandwichDescriptionTextView.setText(NA);
        } else {
            sandwichDescriptionTextView.setText(sandwich.getDescription());
        }

        // set ingredients - List<String>
        if (sandwich.getIngredients() == null) {
            sandwichIngredientsTextView.setText(NA);
        } else if (sandwich.getIngredients().size() == 0) {
            sandwichIngredientsTextView.setText("None");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String ingredient : sandwich.getIngredients()) {
                sb.append(ingredient + ",");
            }
            String ingredientList = sb.toString();
            ingredientList = ingredientList.substring(0,ingredientList.length()-1);
            sandwichIngredientsTextView.setText(ingredientList);
        }

        // set also known as - List<String>
        if (sandwich.getAlsoKnownAs() == null) {
            sandwichAlsoKnownAsTextView.setText(NA);
        } else if (sandwich.getAlsoKnownAs().size() == 0) {
            sandwichAlsoKnownAsTextView.setText("None");
        } else {
            StringBuilder sb = new StringBuilder();
            for (String names : sandwich.getAlsoKnownAs()) {
                sb.append(names + ",");
            }
            String nameList = sb.toString();
            nameList = nameList.substring(0,nameList.length()-1);
            sandwichAlsoKnownAsTextView.setText(nameList);
        }

        // set image link - String
        if (sandwich.getImage() != null || sandwich.getImage() != "") {
            Picasso.with(this).load(sandwich.getImage()).into(ingredientsIv);
        }
    }
}
