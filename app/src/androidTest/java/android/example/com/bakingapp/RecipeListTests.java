package android.example.com.bakingapp;

import android.example.com.bakingapp.ui.AllRecipesActivity;

import androidx.recyclerview.widget.RecyclerView;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeListTests {

    @Rule
    public final ActivityTestRule<AllRecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(AllRecipesActivity.class);

    final int TESTED_ITEM = 0;
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    //click is used to verify that the recyclerview is not empty
    @Test
    public void checkIfAnyItemsAreDisplayedTest(){
        onView(withId(R.id.recipes_recycler_view)).check(matches(isCompletelyDisplayed()));

        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.recipes_recycler_view);
        int itemsCount = recyclerView.getAdapter().getItemCount();

        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.scrollToPosition(itemsCount - 1));

    }

    @Test
    public void recipesListScrollsTest(){
        RecyclerView recyclerView = mActivityTestRule.getActivity().findViewById(R.id.recipes_recycler_view);
        int itemsCount = recyclerView.getAdapter().getItemCount();

        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.scrollToPosition(itemsCount - 1));
    }

    @Test
    public void recipeTitleTest(){
        String recipeTitle = "Nutella Pie";

        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.scrollToPosition(TESTED_ITEM));
        onView(withText(recipeTitle)).check(matches(isDisplayed()));

    }

    @After
    public void unregisterIdlingResources(){
        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
