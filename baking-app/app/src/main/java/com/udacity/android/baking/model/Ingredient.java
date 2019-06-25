package com.udacity.android.baking.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for Ingredient after converting JSON. Getters and setters included
 * Used http://www.parcelabler.com/ to turn into Parcelable class
 */
public class Ingredient implements Parcelable {

    private int quantity;
    private String measure;
    private String ingredient;

    public Ingredient() {}

    public Ingredient(int quantity, String measure, String ingredient) {
      this.quantity = quantity;
      this.measure = measure;
      this.ingredient = ingredient;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return this.measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return this.ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }

    // methods to make Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
