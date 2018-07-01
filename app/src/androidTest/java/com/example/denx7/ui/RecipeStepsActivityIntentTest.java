package com.example.denx7.ui;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ui.RecipeListActivity;

@RunWith(AndroidJUnit4.class)
public class RecipeStepsActivityIntentTest {

    private static final String EXTRA_STEP_INDEX_KEY = "index";
    private static final int EXTRA_STEP_INDEX_VALUE = 1;

    @Rule
    public IntentsTestRule<RecipeListActivity> intentsTestRule
            = new IntentsTestRule<>(RecipeListActivity.class);

    @Test
    public void clickOnRecyclerViewItem_runsRecipeStepActivityIntent() {
        onView(withId(R.id.recipes))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(ViewMatchers.withId(R.id.recipe_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        intended(allOf(
                hasExtra(EXTRA_STEP_INDEX_KEY, EXTRA_STEP_INDEX_VALUE)
        ));
    }
}
