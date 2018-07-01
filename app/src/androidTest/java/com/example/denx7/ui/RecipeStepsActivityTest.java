package com.example.denx7.ui;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ui.RecipeListActivity;

@RunWith(AndroidJUnit4.class)
public class RecipeStepsActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);

    @Test
    public void clickRecyclerViewItem_NutellaPie_OpensStepsActivity() {
        onView(withId(R.id.recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_steps)).check(matches(isDisplayed()));
    }


    @Test
    public void clickRecyclerViewItem_Brownies_OpensStepsActivity() {
        onView(withId(R.id.recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_steps)).check(matches(isDisplayed()));

    }


    @Test
    public void clickRecyclerViewItem_YellowCake_OpensStepsActivity() {
        onView(withId(R.id.recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(2, click()));
        onView(withId(R.id.ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_steps)).check(matches(isDisplayed()));
    }


    @Test
    public void clickRecyclerViewItem_CheeseCake_OpensStepsActivity() {
        onView(withId(R.id.recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(3, click()));
        onView(withId(R.id.ingredients)).check(matches(isDisplayed()));
        onView(withId(R.id.recipe_steps)).check(matches(isDisplayed()));
    }

}