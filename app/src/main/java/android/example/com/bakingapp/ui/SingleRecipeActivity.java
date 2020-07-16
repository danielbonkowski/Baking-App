package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.network.NetworkUtils;
import android.example.com.bakingapp.widget.BakingService;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.os.Bundle;

public class SingleRecipeActivity extends AppCompatActivity  implements FragmentSingleRecipe.OnStepClickListener {

    public static final String INTENT_EXTRA_STEP_POSITION = "selected_step_position";
    public static final String INTENT_EXTRA_STEP_RECIPE = "selected_step_recipe";
    private static final int FIRST_STEP_POSITION = 0;
    boolean mIsConnectedToInternet = true;

    private boolean mTwoPane;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, AllRecipesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
        mIsConnectedToInternet =  NetworkUtils.isConnectedToInternet(getApplicationContext());

        mTwoPane = findViewById(R.id.double_pane_constraint_layout) != null;
        Intent receivedIntent = getIntent();

        if(!mTwoPane && receivedIntent != null){

            SimpleRecipe simpleRecipe = (SimpleRecipe) receivedIntent.getSerializableExtra(AllRecipesActivity.INTENT_EXTRA_RECIPE);
            FragmentManager fragmentManager = getSupportFragmentManager();
            addSingleStepFragment(simpleRecipe, fragmentManager);

        }else if (receivedIntent != null){

            SimpleRecipe simpleRecipe = (SimpleRecipe) receivedIntent.getSerializableExtra(AllRecipesActivity.INTENT_EXTRA_RECIPE);
            if(simpleRecipe == null){
                return;
            }

            FragmentManager fragmentManager = getSupportFragmentManager();

            addSingleStepFragment(simpleRecipe, fragmentManager);
            addIngredientsFragment(simpleRecipe, fragmentManager);

            String videoUrl = simpleRecipe.getSteps().get(FIRST_STEP_POSITION).getVideoUrl();
            if(videoUrl != null && !videoUrl.isEmpty() && mIsConnectedToInternet){
                addMediaPlayerFragment(fragmentManager, videoUrl);
            }else{
                addCookingGraphicFragment(fragmentManager);
            }
        }
    }

    private void addCookingGraphicFragment(FragmentManager fragmentManager) {
        FragmentCookingGraphic fragmentCookingGraphic = new FragmentCookingGraphic();
        fragmentManager.beginTransaction()
                .add(R.id.media_player_or_graphic_container, fragmentCookingGraphic)
                .commit();
    }

    private void addMediaPlayerFragment(FragmentManager fragmentManager, String videoUrl) {
        FragmentMediaPlayer fragmentMediaPlayer = new FragmentMediaPlayer();
        fragmentMediaPlayer.setVideoUrl(videoUrl);
        fragmentManager.beginTransaction()
                .add(R.id.media_player_or_graphic_container, fragmentMediaPlayer)
                .commit();
    }

    private void addIngredientsFragment(SimpleRecipe simpleRecipe, FragmentManager fragmentManager) {
        FragmentInstructions fragmentInstructions = new FragmentInstructions();
        fragmentInstructions.setInstructions(simpleRecipe.getSteps().get(FIRST_STEP_POSITION).getDescription());
        fragmentInstructions.setStep(FIRST_STEP_POSITION);
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_container, fragmentInstructions)
                .commit();
    }

    private void addSingleStepFragment(SimpleRecipe simpleRecipe,
                                       FragmentManager fragmentManager) {
        BakingService.startActionUpdateRecipe(this, simpleRecipe);

        setTitle(simpleRecipe.getName());
        FragmentSingleRecipe fragmentSingleRecipe = new FragmentSingleRecipe();
        fragmentSingleRecipe.setRecipe(simpleRecipe);

        fragmentManager.beginTransaction()
                .add(R.id.single_recipe_container, fragmentSingleRecipe)
                .commit();
    }


    @Override
    public void onStepClick(int position, SimpleRecipe simpleRecipe) {

        if(!mTwoPane){
            Bundle bundle = new Bundle();
            bundle.putSerializable(INTENT_EXTRA_STEP_RECIPE, simpleRecipe);
            bundle.putSerializable(INTENT_EXTRA_STEP_POSITION, position);

            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            replaceFragments(position, simpleRecipe);
        }

    }

    private void replaceFragments(int position, SimpleRecipe simpleRecipe){
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentInstructions fragmentInstructions = new FragmentInstructions();
        fragmentInstructions.setInstructions(simpleRecipe.getSteps().get(position).getDescription());
        fragmentInstructions.setStep(position);
        fragmentManager.beginTransaction()
                .replace(R.id.ingredients_container, fragmentInstructions)
                .commit();

        String videoUrl = simpleRecipe.getSteps().get(position).getVideoUrl();
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
}