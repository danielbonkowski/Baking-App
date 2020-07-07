package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.BakingAppWidgetProvider;
import android.example.com.bakingapp.BakingService;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.example.com.bakingapp.roomModel.AppDatabase;
import android.example.com.bakingapp.network.NetworkUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;

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
            NetworkUtils.getRecipesFromApi(getApplicationContext());

            FragmentAllRecipes fragmentAllRecipes = new FragmentAllRecipes();

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipes_container, fragmentAllRecipes)
                    .commit();
        }

        if(widgetIntent != null && BakingService.ACTION_UPDATE_RECIPE  == widgetIntent.getAction() &&
                    savedInstanceState == null){

            SimpleRecipe simpleRecipe = (SimpleRecipe) widgetIntent.getSerializableExtra(INTENT_EXTRA_RECIPE);

            startRecipeDetailsActivity(simpleRecipe);

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