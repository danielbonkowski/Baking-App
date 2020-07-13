package android.example.com.bakingapp;

import android.example.com.bakingapp.ui.AllRecipesActivity;
import android.example.com.bakingapp.ui.SingleRecipeActivity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtras;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ValidRecipeIntentDataTest {

    int TESTED_ITEM = 0;
    private IdlingResource mIdlingResource;
    int FIRST_ITEM_POSITION = 0;

    @Rule
    public IntentsTestRule<AllRecipesActivity> mActivityTestRule =
            new IntentsTestRule<>(AllRecipesActivity.class);

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(mIdlingResource);
    }

    //click is used to verify that the recyclerview is not empty
    @Test
    public void selectRecipeItem_CheckIfSendsData(){
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(TESTED_ITEM, click()));

        intended(allOf(
                hasComponent(SingleRecipeActivity.class.getName())));
    }

    @After
    public void unregisterIdlingResources(){
        if(mIdlingResource != null){
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
