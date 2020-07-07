package android.example.com.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.util.Log;

import androidx.annotation.Nullable;

public class BakingService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE =
            "android.example.com.bakingapp";
    public static final String SERIALIZED_RECIPE_KEY =
            "serialized_recipe";
    public static final String EXTRA_RECIPE_ID =
            "recipe_id";
    private static final String TAG = BakingService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public BakingService() {
        super("BakingService");
    }

    public static void startActionUpdateRecipe(Context context, SimpleRecipe simpleRecipe){
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
        BakingAppWidgetProvider.updateAppWidgets(this, appWidgetManager, appWidgetIds, simpleRecipe);
    }
}
