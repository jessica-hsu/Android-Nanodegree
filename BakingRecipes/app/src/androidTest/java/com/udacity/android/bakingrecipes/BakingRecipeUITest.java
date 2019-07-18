package com.udacity.android.bakingrecipes;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class BakingRecipeUITest {
    @Rule
    public IntentsTestRule<MainActivity> activityRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void recipeTest() {
        // Let the UI load completely first
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // click the brownies button
        Espresso.onView(ViewMatchers.withText("BROWNIES")).perform(ViewActions.click());

        // did intent with this class show up?
        intended(hasComponent(RecipeListActivity.class.getName()));

        // scroll to recipe intro
        Espresso.onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(3));

        // click recipe intro
        Espresso.onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, ViewActions.click()));

        // did intent with this class show up?
        intended(hasComponent(RecipeDetailActivity.class.getName()));

    }
}
