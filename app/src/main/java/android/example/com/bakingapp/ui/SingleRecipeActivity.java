package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.model.Step;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class SingleRecipeActivity extends AppCompatActivity  implements FragmentSingleRecipe.OnStepClickListener {

    private static final String TAG = SingleRecipeActivity.class.getSimpleName();
    public static final String INTENT_EXTRA_STEP = "Selected_step";
    TextView mSingleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        setTitle("Recipe");

        Intent receivedIntent = getIntent();

        if(receivedIntent != null){
            Log.d(TAG, "In the single recipe");
            Recipe recipe = (Recipe) receivedIntent.getSerializableExtra(AllRecipesActivity.INTENT_EXTRA_RECIPE);
            FragmentSingleRecipe fragmentSingleRecipe = new FragmentSingleRecipe(this);
            fragmentSingleRecipe.setRecipe(recipe);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.single_recipe_container, fragmentSingleRecipe)
                    .commit();
        }
    }

    @Override
    public void onStepClick(Step step) {
        Toast.makeText(this, "Position clicked: " + step.getId(), Toast.LENGTH_SHORT).show();

        Bundle bundle = new Bundle();
        bundle.putSerializable(INTENT_EXTRA_STEP, step);

        Intent intent = new Intent(this, StepActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}