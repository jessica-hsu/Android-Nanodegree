package com.udacity.android.baking.model;

import java.io.Serializable;

/**
 * Model for Ingredient
 */
public class Ingredient implements Serializable {
    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient(int q, String m, String i) {
        this.quantity = q;
        this.measure = m;
        this.ingredient = i;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
