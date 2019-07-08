package com.udacity.android.baking.model;

//object to hold all recipe's main details

import java.util.List;

public class DetailsContainer {
    private String name;
    private String image;
    private int servings;
    private List<Ingredient> ingredients;

    public DetailsContainer(String name, String image, int servings, List<Ingredient> ingredients) {
        this.name = name;
        this.image = image;
        this.servings = servings;
        this.ingredients = ingredients;
    }

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.image;
    }

    public int getServings() {
        return this.servings;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }
}
