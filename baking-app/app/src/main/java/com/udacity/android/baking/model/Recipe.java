package com.udacity.android.baking.model;

import java.util.List;

/**
 * Model to hold each Recipe after converting JSON. Setters and getters included
 */
public class Recipe {

    private int id;
    private String name;
    private List<Ingredient> ingredients = null;
    private List<Step> steps = null;
    private int servings;
    private String image;

    public Recipe() {}

    public Recipe(int id, String name, List<Ingredient> ingredients,
                  List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return this.steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return this.servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
