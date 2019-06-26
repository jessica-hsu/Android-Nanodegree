package com.udacity.android.baking.utils;

import com.udacity.android.baking.model.Ingredient;
import com.udacity.android.baking.model.Recipe;
import com.udacity.android.baking.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class with methods to convert httpresponse json string to Java Objects
 */
public class JSONUtils {

    public static List<Recipe> parseJson(String jsonString) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();

        JSONArray mainArray = new JSONArray(jsonString);

        // parse thru array of JSON recipes
        for (int i = 0; i < mainArray.length(); i++) {
            // parse each JSON recipe attributes
            JSONObject recipeObj = mainArray.getJSONObject(i);
            int id = recipeObj.getInt("id");
            String name = recipeObj.getString("name");
            int servings = recipeObj.getInt("servings");
            String image = recipeObj.getString("image");

            // parse out ingredient
            JSONArray allIngredients = recipeObj.getJSONArray("ingredients");
            List<Ingredient> ingredients = parseIngredients(allIngredients);

            // parse out steps
            JSONArray allSteps = recipeObj.getJSONArray("steps");
            List<Step> steps = parseSteps(allSteps);

            // create Recipe obj and add to list
            Recipe recipe = new Recipe(id, name, ingredients, steps, servings, image);
            recipes.add(recipe);
        }

        return recipes;
    }

    /**
     * Parse ingredients
     * @param allIngredients
     * @return
     */
    private static List<Ingredient> parseIngredients(JSONArray allIngredients) throws JSONException {
        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < allIngredients.length(); i++) {

            JSONObject ingredientObj = allIngredients.getJSONObject(i);
            int quantity = ingredientObj.getInt("quantity");
            String measure = ingredientObj.getString("measure");
            String name = ingredientObj.getString("ingredient");

            Ingredient ingredient = new Ingredient(quantity, measure, name);
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    /**
     * Parse steps
     * @param allSteps
     * @return
     */
    private static List<Step> parseSteps(JSONArray allSteps) throws JSONException {
        List<Step> steps = new ArrayList<>();

        for (int i = 0; i < allSteps.length(); i++) {

            JSONObject stepsObj = allSteps.getJSONObject(i);
            int id = stepsObj.getInt("id");
            String shortDescription = stepsObj.getString("shortDescription");
            String description = stepsObj.getString("description");
            String videoUrl = stepsObj.getString("videoURL");
            String thumbnailUrl = stepsObj.getString("thumbnailURL");

            Step step = new Step(id, shortDescription, description, videoUrl, thumbnailUrl);
            steps.add(step);
        }
        return steps;
    }
}
