package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.databinding.FragmentInstructionsBinding;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.model.Step;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class StepActivity extends AppCompatActivity {

    private static final String TAG = StepActivity.class.getSimpleName();
    int mPosition;
    Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent receivedIntent = getIntent();
        if(receivedIntent != null && mRecipe == null){

            mPosition = (int) receivedIntent.getSerializableExtra(SingleRecipeActivity.INTENT_EXTRA_STEP_POSITION);
            mRecipe = (Recipe) receivedIntent.getSerializableExtra(SingleRecipeActivity.INTENT_EXTRA_STEP_RECIPE);

            Log.d(TAG,  "Video URL: " + mRecipe.getSteps().get(mPosition).getVideoUrl());
            addFragments();

            setTitle(mRecipe.getName());
        }

        Button previousButton = findViewById(R.id.button_previous);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPosition > 0){
                    --mPosition;
                    replaceFragments();
                }
            }
        });

        Button nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPosition < mRecipe.getSteps().size() - 1){
                    ++mPosition;
                    replaceFragments();
                }
            }
        });
    }

    private void addFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentInstructions fragmentInstructions = new FragmentInstructions();
        fragmentInstructions.setInstructions(mRecipe.getSteps().get(mPosition).getDescription());
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_container, fragmentInstructions)
                .commit();


        FragmentMediaPlayer fragmentMediaPlayer = new FragmentMediaPlayer();
        fragmentMediaPlayer.setVideoUrl(mRecipe.getSteps().get(mPosition).getVideoUrl());
        fragmentManager.beginTransaction()
                .add(R.id.media_player_container, fragmentMediaPlayer)
                .commit();
    }

    private void replaceFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentInstructions fragmentInstructions = new FragmentInstructions();
        fragmentInstructions.setInstructions(mRecipe.getSteps().get(mPosition).getDescription());
        fragmentManager.beginTransaction()
                .replace(R.id.ingredients_container, fragmentInstructions)
                .commit();

        FragmentMediaPlayer fragmentMediaPlayer = new FragmentMediaPlayer();
        fragmentMediaPlayer.setVideoUrl(mRecipe.getSteps().get(mPosition).getVideoUrl());
        fragmentManager.beginTransaction()
                .replace(R.id.media_player_container, fragmentMediaPlayer)
                .commit();
    }
}