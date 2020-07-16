package android.example.com.bakingapp.ui;

import androidx.annotation.NonNull;
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
    private static final String MEDIA_PLAYER_FRAGMENT = "media_player_fragment";
    private static final String STEP_POSITION = "step_position";
    private static final int FIRST_STEP_POSITION = 0;
    boolean mIsConnectedToInternet = true;
    int mPosition;


    private boolean mTwoPane;
    private FragmentMediaPlayer mFragmentMediaPlayer;

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
        if(receivedIntent == null){
            return;
        }

        SimpleRecipe simpleRecipe = (SimpleRecipe) receivedIntent.getSerializableExtra(AllRecipesActivity.INTENT_EXTRA_RECIPE);
        if(simpleRecipe == null){
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(!mTwoPane){
            addSingleStepFragment(simpleRecipe, fragmentManager);

        }else if (savedInstanceState == null){
            addSingleStepFragment(simpleRecipe, fragmentManager);
            addIngredientsFragment(simpleRecipe, fragmentManager, FIRST_STEP_POSITION);
            addMediaPlayerOrGraphicFragment(simpleRecipe, fragmentManager);

        }else if(mTwoPane){
            refreshFragments(savedInstanceState, simpleRecipe, fragmentManager);
        }
    }

    private void refreshFragments(Bundle savedInstanceState, SimpleRecipe simpleRecipe, FragmentManager fragmentManager) {
        mFragmentMediaPlayer = (FragmentMediaPlayer) getSupportFragmentManager().getFragment(savedInstanceState, MEDIA_PLAYER_FRAGMENT);
        mPosition = savedInstanceState.getInt(STEP_POSITION);
        if(mFragmentMediaPlayer != null){
            replaceMediaPlayerFragment(mFragmentMediaPlayer, fragmentManager);
        }

        addSingleStepFragment(simpleRecipe, fragmentManager);
        addIngredientsFragment(simpleRecipe, fragmentManager, mPosition);
    }

    private void addMediaPlayerOrGraphicFragment(SimpleRecipe simpleRecipe, FragmentManager fragmentManager) {
        String videoUrl = simpleRecipe.getSteps().get(FIRST_STEP_POSITION).getVideoUrl();
        if(mFragmentMediaPlayer != null && mIsConnectedToInternet){

        }else if(videoUrl != null && !videoUrl.isEmpty() && mIsConnectedToInternet){
            addMediaPlayerFragment(fragmentManager, videoUrl);
        }else{
            addCookingGraphicFragment(fragmentManager);
        }
    }

    private void addCookingGraphicFragment(FragmentManager fragmentManager) {
        FragmentCookingGraphic fragmentCookingGraphic = new FragmentCookingGraphic();
        fragmentManager.beginTransaction()
                .add(R.id.media_player_or_graphic_container, fragmentCookingGraphic)
                .commit();
    }

    private void replaceMediaPlayerFragment(FragmentMediaPlayer fragmentMediaPlayer, FragmentManager fragmentManager) {
        fragmentManager.beginTransaction()
                .replace(R.id.media_player_or_graphic_container, fragmentMediaPlayer)
                .commit();
    }

    private void addMediaPlayerFragment(FragmentManager fragmentManager, String videoUrl) {
        FragmentMediaPlayer fragmentMediaPlayer = new FragmentMediaPlayer();
        fragmentMediaPlayer.setVideoUrl(videoUrl);
        mFragmentMediaPlayer = fragmentMediaPlayer;
        fragmentManager.beginTransaction()
                .add(R.id.media_player_or_graphic_container, fragmentMediaPlayer)
                .commit();
    }

    private void addIngredientsFragment(SimpleRecipe simpleRecipe, FragmentManager fragmentManager, int position) {
        FragmentInstructions fragmentInstructions = new FragmentInstructions();
        fragmentInstructions.setInstructions(simpleRecipe.getSteps().get(position).getDescription());
        fragmentInstructions.setStep(position);
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
            mPosition = position;
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STEP_POSITION, mPosition);
        FragmentMediaPlayer fragmentMediaPlayer = (FragmentMediaPlayer) getSupportFragmentManager().findFragmentByTag(MEDIA_PLAYER_FRAGMENT);
        if(mFragmentMediaPlayer != null && fragmentMediaPlayer != null){
            getSupportFragmentManager().putFragment(outState, MEDIA_PLAYER_FRAGMENT, mFragmentMediaPlayer);
        }
    }
}