package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.example.com.bakingapp.widget.BakingService;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SingleRecipeActivity extends AppCompatActivity  implements FragmentSingleRecipe.OnStepClickListener {

    private static final String TAG = SingleRecipeActivity.class.getSimpleName();
    public static final String INTENT_EXTRA_STEP_POSITION = "selected_step_position";
    public static final String INTENT_EXTRA_STEP_RECIPE = "selected_step_recipe";
    private static final int FIRST_STEP_POSITION = 0;
    TextView mSingleTextView;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        if(findViewById(R.id.double_pane_constraint_layout) != null){
            mTwoPane = true;
        }else {
            mTwoPane = false;
        }

        if(!mTwoPane){
            Intent receivedIntent = getIntent();

            if(receivedIntent != null){

                SimpleRecipe simpleRecipe = (SimpleRecipe) receivedIntent.getSerializableExtra(AllRecipesActivity.INTENT_EXTRA_RECIPE);
                BakingService.startActionUpdateRecipe(this, simpleRecipe);

                FragmentSingleRecipe fragmentSingleRecipe = new FragmentSingleRecipe();
                fragmentSingleRecipe.setRecipe(simpleRecipe);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.single_recipe_container, fragmentSingleRecipe)
                        .commit();

                setTitle(simpleRecipe.getName());
            }
        }else{
            Intent receivedIntent = getIntent();

            if(receivedIntent != null){

                SimpleRecipe simpleRecipe = (SimpleRecipe) receivedIntent.getSerializableExtra(AllRecipesActivity.INTENT_EXTRA_RECIPE);
                BakingService.startActionUpdateRecipe(this, simpleRecipe);

                FragmentSingleRecipe fragmentSingleRecipe = new FragmentSingleRecipe();
                fragmentSingleRecipe.setRecipe(simpleRecipe);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.single_recipe_container, fragmentSingleRecipe)
                        .commit();

                FragmentInstructions fragmentInstructions = new FragmentInstructions();
                fragmentInstructions.setInstructions(simpleRecipe.getSteps().get(FIRST_STEP_POSITION).getDescription());
                fragmentInstructions.setStep(FIRST_STEP_POSITION);
                fragmentManager.beginTransaction()
                        .add(R.id.ingredients_container, fragmentInstructions)
                        .commit();



                String videoUrl = simpleRecipe.getSteps().get(FIRST_STEP_POSITION).getVideoUrl();
                if(videoUrl != null && !videoUrl.isEmpty()){

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

                setTitle(simpleRecipe.getName());
            }
        }
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
        if(videoUrl != null && !videoUrl.isEmpty()){

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