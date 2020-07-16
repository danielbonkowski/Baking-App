package android.example.com.bakingapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.idlingResource.SimpleIdlingResource;
import android.example.com.bakingapp.widget.BakingService;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.example.com.bakingapp.network.NetworkUtils;
import android.os.Bundle;
import android.util.Log;

public class AllRecipesActivity extends AppCompatActivity implements
FragmentAllRecipes.OnRecipeClickListener,
        NetworkUtils.DownloaderCallback {

    public static final String INTENT_EXTRA_RECIPE = "Selected_recipe";
    private static final String TAG = AllRecipesActivity.class.getSimpleName();

    @Nullable
    private static SimpleIdlingResource mSimpleIdlingResource;

    @Nullable
    public static SimpleIdlingResource getIdlingResource(){
        if(mSimpleIdlingResource == null){
            mSimpleIdlingResource = new SimpleIdlingResource();
        }
        return mSimpleIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent widgetIntent = getIntent();
        if(savedInstanceState == null){

            NetworkUtils.getRecipesFromApi(getApplicationContext(), this, mSimpleIdlingResource);

            FragmentAllRecipes fragmentAllRecipes = new FragmentAllRecipes();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipes_container, fragmentAllRecipes)
                    .commit();
        }
    }

    @Override
    public void onRecipeSelected(SimpleRecipe simpleRecipe) {
        //Toast.makeText(this, "Position clicked: " + simpleRecipe.getId(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "SimpleRecipe: " + simpleRecipe.getIngredientsToString());
        BakingService.startActionUpdateRecipe(this, simpleRecipe);

        startRecipeDetailsActivity(simpleRecipe);
    }

    private void startRecipeDetailsActivity(SimpleRecipe simpleRecipe){
        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_EXTRA_RECIPE, simpleRecipe);

        final Intent intent = new Intent(this, SingleRecipeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDownloaded(boolean isFinished) {

    }
}