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
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class StepDisplayTest {

    @Rule
    public ActivityTestRule<AllRecipesActivity> mAllRecipesActivity =
            new ActivityTestRule<>(AllRecipesActivity.class);

    int TESTED_ITEM = 0;
    String RECIPE_NAME = "Nutella Pie";
    int FIRST_STEP_POSITION = 0;
    private IdlingResource mIdlingResource;
    int FIRST_ITEM_POSITION = 0;

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mAllRecipesActivity.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickRecipeItem_ReturnToAllRecipesActivity(){

        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TESTED_ITEM, click()));


        onView(withId(R.id.single_recipe_steps_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(FIRST_STEP_POSITION, click()));

        onView(withId(R.id.instructions_text_view)).check(matches(isDisplayed()));
        onView(withId(R.id.media_player_or_graphic_container)).check(matches(isDisplayed()));
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
