package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.model.Recipe;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SingleRecipeActivity extends AppCompatActivity  implements FragmentSingleRecipe.OnStepClickListener {

    private static final String TAG = SingleRecipeActivity.class.getSimpleName();
    public static final String INTENT_EXTRA_STEP_POSITION = "selected_step_position";
    public static final String INTENT_EXTRA_STEP_RECIPE = "selected_step_recipe";
    TextView mSingleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        Intent receivedIntent = getIntent();

        if(receivedIntent != null){




            Recipe recipe = (Recipe) receivedIntent.getSerializableExtra(AllRecipesActivity.INTENT_EXTRA_RECIPE);
            FragmentSingleRecipe fragmentSingleRecipe = new FragmentSingleRecipe();
            fragmentSingleRecipe.setRecipe(recipe);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.single_recipe_container, fragmentSingleRecipe)
                    .commit();

            setTitle(recipe.getName());
        }
    }

    @Override
    public void onStepClick(int position, Recipe recipe) {
        Toast.makeText(this, "Position clicked: " + recipe.getId(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_EXTRA_STEP_RECIPE, recipe);
        bundle.putSerializable(INTENT_EXTRA_STEP_POSITION, position);

        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}