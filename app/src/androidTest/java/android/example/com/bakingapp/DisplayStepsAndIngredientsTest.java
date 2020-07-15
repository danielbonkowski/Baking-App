package android.example.com.bakingapp;

import android.example.com.bakingapp.ui.AllRecipesActivity;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;

@SuppressWarnings("unchecked")
@RunWith(AndroidJUnit4.class)
public class DisplayStepsAndIngredientsTest {

    @Rule
    public final ActivityTestRule<AllRecipesActivity> mAllRecipes
        = new ActivityTestRule(AllRecipesActivity.class);

    final int TESTED_ITEM = 1;
    private IdlingResource mIdlingResource;



    @Before
    public void registerIdlingResource(){
        mAllRecipes.getActivity()
                .getSupportFragmentManager().beginTransaction();

        mIdlingResource = mAllRecipes.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    //check if ingredients and steps recycler views are displayed
    @Test
    public void clickGridViewItem_DisplaysStepsAndIngredients(){
        onView(withId(R.id.recipes_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(TESTED_ITEM, click()));
        onView(withId(R.id.single_recipe_container)).check(matches(isDisplayed()));
        onView(withId(R.id.single_recipe_ingredients_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.single_recipe_steps_recycler_view)).check(matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResources(){
        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
