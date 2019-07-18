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

        Espresso.onView(ViewMatchers.withText("BROWNIES")).perform(ViewActions.click());

        intended(hasComponent(RecipeListActivity.class.getName()));

        Espresso.onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.scrollToPosition(3));

        Espresso.onView(ViewMatchers.withId(R.id.recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(3, ViewActions.click()));

        intended(hasComponent(RecipeDetailActivity.class.getName()));

    }
}
