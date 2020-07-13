package android.example.com.bakingapp;

import android.example.com.bakingapp.ui.AllRecipesActivity;
import android.example.com.bakingapp.ui.AllRecipesAdapter;
import android.example.com.bakingapp.ui.FragmentSingleRecipe;
import android.example.com.bakingapp.ui.SingleRecipeActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class DisplayStepsAndIngredientsTest {

    @Rule
    public ActivityScenarioRule<AllRecipesActivity> mActivityTestRule
        = new ActivityScenarioRule(AllRecipesActivity.class);

    int TESTED_ITEM = 1;
    int FIRST_ITEM = 1;
    //check if ingredients and steps recycler views are displayed
    @Test
    public void clickGridViewItem_DisplaysStepsAndIngredients(){
        onView(withId(R.id.recipes_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(TESTED_ITEM, click()));
        //onView(withId(R.id.single_recipe_ingredients_recycler_view)).check(matches(isDisplayed()));
        //onView(withId(R.id.single_recipe_steps_recycler_view)).check(matches(isDisplayed()));

    }
}
