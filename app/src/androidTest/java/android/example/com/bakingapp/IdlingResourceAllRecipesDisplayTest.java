package android.example.com.bakingapp;

import android.example.com.bakingapp.ui.AllRecipesActivity;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class IdlingResourceAllRecipesDisplayTest {

    @Rule
    public ActivityTestRule<AllRecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(AllRecipesActivity.class);

    int TESTED_ITEM = 0;
    private IdlingResource mIdlingResource;
    int FIRST_ITEM_POSITION = 0;
    String RECIPE_NAME = "Nutella Pie";

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    //click is used to verify that the recyclerview is not empty
    @Test
    public void checkIfAnyItemsAreDisplayedTest(){
        onView(withId(R.id.recipes_recycler_view))
        .perform(RecyclerViewActions.actionOnItemAtPosition(FIRST_ITEM_POSITION, click()));
        onView(withId(R.id.single_recipe_ingredients_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.single_recipe_steps_recycler_view)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResources(){
        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

    public static Matcher<View> matchToolbarTitle(final Matcher<CharSequence> matcher) {
        return new BoundedMatcher<View, Toolbar>(androidx.appcompat.widget.Toolbar.class) {

            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(Toolbar toolbar) {
                return matcher.matches(toolbar.getTitle());
            }


        };
    }

    public static Matcher<View> matchToolbarTitle(CharSequence activityTitle) {
        return matchToolbarTitle(is(activityTitle));
    }
}
