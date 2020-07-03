package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.network.NetworkUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AllRecipesActivity extends AppCompatActivity implements
FragmentAllRecipes.OnRecipeClickListener{

    public static final String INTENT_EXTRA_RECIPE = "Selected_recipe";
    private static final String TAG = AllRecipesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkUtils.getRecipesFromApi();
        FragmentAllRecipes fragmentAllRecipes = new FragmentAllRecipes();
        fragmentAllRecipes.setRecipes(NetworkUtils.getmRecipes());

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipes_container, fragmentAllRecipes)
                .commit();
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Toast.makeText(this, "Position clicked: " + recipe.getId(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Recipe: " + recipe.getIngredientsToString());

        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_EXTRA_RECIPE, recipe);

        final Intent intent = new Intent(this, SingleRecipeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}