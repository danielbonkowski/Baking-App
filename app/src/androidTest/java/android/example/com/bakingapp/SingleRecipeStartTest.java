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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class SingleRecipeStartTest {

    @Rule
    public final ActivityTestRule<AllRecipesActivity> mActivityTestRule =
            new ActivityTestRule<>(AllRecipesActivity.class);

    final int TESTED_ITEM = 0;
    final String RECIPE_NAME = "Nutella Pie";
    private IdlingResource mIdlingResource;

    @Before
    public void registerIdlingResource(){
        mActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();

        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecipeItem_OpensCorrectSingleRecipeActivity() {
        onView(withId(R.id.recipes_recycler_view)).check(matches(isDisplayed()));
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TESTED_ITEM, click()));


        onView(withId(R.id.single_recipe_container)).check(matches(isDisplayed()));
        onView(allOf(instanceOf(TextView.class), withParent(matchToolbarTitle(RECIPE_NAME))))
                .check(matches(isDisplayed()));

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
