package android.example.com.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.example.com.bakingapp.ui.AllRecipesActivity;
import android.example.com.bakingapp.ui.SingleRecipeActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    private static SimpleRecipe mSimpleRecipe;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, SimpleRecipe simpleRecipe) {


        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int height = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

        RemoteViews views;
        if(width >= 200 && height >= 200){
            views = getRecipeLogoWithNameAndIngredients();
        }else if(width >= 180){
            views = getRecipeLogoWithName(context, simpleRecipe);
        }else{
            views = getRecipeLogo(context, simpleRecipe);
        }

        // Construct the RemoteViews object




        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static RemoteViews getRecipeLogoWithNameAndIngredients() {
        return null;
    }

    private static RemoteViews getRecipeLogo(Context context, SimpleRecipe simpleRecipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);
        PendingIntent pendingIntent = getPendingIntent(context, simpleRecipe);

        if(simpleRecipe != null){
            views.setOnClickPendingIntent(R.id.widget_main_layout, pendingIntent);
            views.setViewVisibility(R.id.appwidget_recipe_name, View.GONE);
        }

        return views;
    }




    private static RemoteViews getRecipeLogoWithName(Context context, SimpleRecipe simpleRecipe) {

        RemoteViews views = getRecipeLogo(context, simpleRecipe);

        if(simpleRecipe != null){
            views.setViewVisibility(R.id.appwidget_recipe_name, View.VISIBLE);
            views.setTextViewText(R.id.appwidget_recipe_name, simpleRecipe.getName());
        }

        return views;
    }

    private static PendingIntent getPendingIntent(Context context, SimpleRecipe simpleRecipe) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AllRecipesActivity.INTENT_EXTRA_RECIPE, simpleRecipe);

        Intent recipeUpdateIntent = new Intent(context, AllRecipesActivity.class);
        recipeUpdateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        recipeUpdateIntent.setAction(BakingService.ACTION_UPDATE_RECIPE);
        recipeUpdateIntent.putExtras(bundle);

        return PendingIntent.getActivity(context, 0, recipeUpdateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds, SimpleRecipe simpleRecipe) {
        mSimpleRecipe = simpleRecipe;
        for(int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId, simpleRecipe);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        BakingService.startActionUpdateRecipe(context, mSimpleRecipe);
        super.onAppWidgetOptionsChanged(context,appWidgetManager, appWidgetId, newOptions);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        BakingService.startActionUpdateRecipe(context, mSimpleRecipe);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

