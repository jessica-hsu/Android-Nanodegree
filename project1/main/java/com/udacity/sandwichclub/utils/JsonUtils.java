package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException{
        Sandwich sandwich = new Sandwich();
        JSONObject sandwichJSON = new JSONObject(json);

        // parse out name: {mainName="", alsoKnownAs:[]} and set to Sandwich object
        JSONObject sandwichName = sandwichJSON.getJSONObject("name");
        String sandwichMainName = sandwichName.getString("mainName");
        JSONArray sandwichNickNameArray = sandwichJSON.getJSONArray("alsoKnownAs");
        List<String> sandwichNickNames = new ArrayList<>();
        for (int i=0; i<sandwichNickNameArray.length(); i++) {
            sandwichNickNames.add(sandwichNickNameArray.getString(i));
        }
        sandwich.setMainName(sandwichMainName);
        sandwich.setAlsoKnownAs(sandwichNickNames);

        // parse out placeOfOrigin and set to Sandwich object
        String sandwichPlaceofOrigin = sandwichJSON.getString("placeOfOrigin");
        sandwich.setPlaceOfOrigin(sandwichPlaceofOrigin);

        // parse out description and set to Sandwich object
        String sandwichDescription = sandwichJSON.getString("description");
        sandwich.setDescription(sandwichDescription);

        // parse out imageLink and set to Sandwich object
        String sandwichImage = sandwichJSON.getString("image");
        sandwich.setImage(sandwichImage);

        // parse out ingredient array and set to Sandwich object
        JSONArray ingredientArray = sandwichJSON.getJSONArray("ingredients");
        List<String> sandwichIngredients = new ArrayList<>();
        for (int i=0; i<ingredientArray.length(); i++)  {
            sandwichIngredients.add(ingredientArray.getString(i));
        }
        sandwich.setIngredients(sandwichIngredients);


        return sandwich;
    }
}
