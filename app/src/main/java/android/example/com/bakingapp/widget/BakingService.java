package android.example.com.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.utilities.RecipeUtilities;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.util.Log;

import androidx.annotation.Nullable;

public class BakingService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE =
            "android.example.com.bakingapp";
    public static final String SERIALIZED_RECIPE_KEY =
            "serialized_recipe";
    private static final String TAG = BakingService.class.getSimpleName();


    public BakingService() {
        super("BakingService");
    }

    public static void startActionUpdateRecipe(Context context, SimpleRecipe simpleRecipe){
        RecipeUtilities.setSimpleRecipe(simpleRecipe);
        Intent intent = new Intent(context, BakingService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        intent.putExtra(SERIALIZED_RECIPE_KEY, simpleRecipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            final String action = intent.getAction();
            if(ACTION_UPDATE_RECIPE.equals(action)){
                Log.d(TAG, "Action click:");
                SimpleRecipe simpleRecipe = (SimpleRecipe) intent.getSerializableExtra(SERIALIZED_RECIPE_KEY);
                handleActionUpdateRecipe(simpleRecipe);
            }
        }
    }

    private void handleActionUpdateRecipe(SimpleRecipe simpleRecipe){

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_ingredients_layout);
        BakingAppWidgetProvider.updateAppWidgets(this, appWidgetManager, appWidgetIds, simpleRecipe);
    }
}
