package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.widget.BakingService;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.example.com.bakingapp.network.NetworkUtils;
import android.os.Bundle;
import android.util.Log;

public class AllRecipesActivity extends AppCompatActivity implements
FragmentAllRecipes.OnRecipeClickListener{

    public static final String INTENT_EXTRA_RECIPE = "Selected_recipe";
    private static final String TAG = AllRecipesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent widgetIntent = getIntent();
        if(savedInstanceState == null){
            Log.d(TAG, "First ingredient is tested");
            Log.d(TAG, "Widget intent: " + widgetIntent.getAction());
            Log.d(TAG, "Saved instance state: " + (savedInstanceState == null ? "null": "not null"));
            NetworkUtils.getRecipesFromApi(getApplicationContext());

            FragmentAllRecipes fragmentAllRecipes = new FragmentAllRecipes();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipes_container, fragmentAllRecipes)
                    .commit();
        }

        Log.d(TAG, "Widget intent: " + (widgetIntent == null ? "null" : widgetIntent.getAction()));
        Log.d(TAG, "Saved instance state: " + (savedInstanceState == null ? "null": "not null"));

        if(widgetIntent != null && BakingService.ACTION_UPDATE_RECIPE  == widgetIntent.getAction()){

            Log.d(TAG, "Widget intent: " + widgetIntent.getAction());
            Log.d(TAG, "Saved instance state: " + (savedInstanceState == null ? "null": "not null"));
            SimpleRecipe simpleRecipe = (SimpleRecipe) widgetIntent.getSerializableExtra(INTENT_EXTRA_RECIPE);
            if(simpleRecipe != null){
                startRecipeDetailsActivity(simpleRecipe);
            }


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
}