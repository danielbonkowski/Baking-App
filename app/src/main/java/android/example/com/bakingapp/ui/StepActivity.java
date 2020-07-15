package android.example.com.bakingapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.example.com.bakingapp.network.AppExecutors;
import android.example.com.bakingapp.network.NetworkUtils;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

public class StepActivity extends AppCompatActivity {

    private static final String TAG = StepActivity.class.getSimpleName();

    private static final String SIMPLE_RECIPE_KEY = "simple_recipe";
    private static final String POSITION_KEY = "position";

    int mPosition;
    SimpleRecipe mSimpleRecipe;
    boolean mIsConnectedToInternet = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Intent receivedIntent = getIntent();
        if(receivedIntent != null && savedInstanceState == null){
            Log.d(TAG, "Intent is not null");

            mPosition = (int) receivedIntent.getSerializableExtra(SingleRecipeActivity.INTENT_EXTRA_STEP_POSITION);
            mSimpleRecipe = (SimpleRecipe) receivedIntent.getSerializableExtra(SingleRecipeActivity.INTENT_EXTRA_STEP_RECIPE);



        }else if(savedInstanceState != null){
            mPosition = savedInstanceState.getInt(POSITION_KEY);
            mSimpleRecipe = (SimpleRecipe) savedInstanceState.getSerializable(SIMPLE_RECIPE_KEY);
        }
        Log.d(TAG,  "Video URL: " + mSimpleRecipe.getSteps().get(mPosition).getVideoUrl());

        addFragmentsAsynchronously();
        addPreviousButtonClickListener();
        addNextButtonClickListener();

        setTitle(mSimpleRecipe.getName());
    }

    private void addFragmentsAsynchronously(){
        AppExecutors.getInstance().networkIO().execute(() -> {
            mIsConnectedToInternet = NetworkUtils.isConnectedToInternet(getApplicationContext());
            runOnUiThread(() -> addFragments());

        });
    }

    private void addNextButtonClickListener() {
        Button nextButton = findViewById(R.id.button_next);
        nextButton.setOnClickListener(v -> {
            if(mPosition < mSimpleRecipe.getSteps().size() - 1){
                ++mPosition;
                replaceFragments();
            }
        });
    }

    private void addPreviousButtonClickListener() {
        Button previousButton = findViewById(R.id.button_previous);
        previousButton.setOnClickListener(v -> {
            if(mPosition > 0){
                --mPosition;
                replaceFragments();
            }
        });
    }

    private void addFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentInstructions fragmentInstructions = new FragmentInstructions();
        fragmentInstructions.setInstructions(mSimpleRecipe.getSteps().get(mPosition).getDescription());
        fragmentInstructions.setStep(mPosition);
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_container, fragmentInstructions)
                .commit();



        String videoUrl = mSimpleRecipe.getSteps().get(mPosition).getVideoUrl();

        if(videoUrl != null && !videoUrl.isEmpty() && mIsConnectedToInternet){

            FragmentMediaPlayer fragmentMediaPlayer = new FragmentMediaPlayer();
            fragmentMediaPlayer.setVideoUrl(videoUrl);
            fragmentManager.beginTransaction()
                    .add(R.id.media_player_or_graphic_container, fragmentMediaPlayer)
                    .commit();

        }else{
            FragmentCookingGraphic fragmentCookingGraphic = new FragmentCookingGraphic();
            fragmentManager.beginTransaction()
                    .add(R.id.media_player_or_graphic_container, fragmentCookingGraphic)
                    .commit();
        }


    }


    private void replaceFragments(){
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentInstructions fragmentInstructions = new FragmentInstructions();
        fragmentInstructions.setInstructions(mSimpleRecipe.getSteps().get(mPosition).getDescription());
        fragmentInstructions.setStep(mPosition);
        fragmentManager.beginTransaction()
                .replace(R.id.ingredients_container, fragmentInstructions)
                .commit();

        String videoUrl = mSimpleRecipe.getSteps().get(mPosition).getVideoUrl();
        if(videoUrl != null && !videoUrl.isEmpty() && mIsConnectedToInternet){

            FragmentMediaPlayer fragmentMediaPlayer = new FragmentMediaPlayer();
            fragmentMediaPlayer.setVideoUrl(videoUrl);
            fragmentManager.beginTransaction()
                    .replace(R.id.media_player_or_graphic_container, fragmentMediaPlayer)
                    .commit();

        }else {
            FragmentCookingGraphic fragmentCookingGraphic = new FragmentCookingGraphic();
            fragmentManager.beginTransaction()
                    .replace(R.id.media_player_or_graphic_container, fragmentCookingGraphic)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable( SIMPLE_RECIPE_KEY, mSimpleRecipe);
        outState.putInt(POSITION_KEY, mPosition);
    }
}