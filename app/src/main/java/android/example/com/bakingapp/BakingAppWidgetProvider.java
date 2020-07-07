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
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, SimpleRecipe simpleRecipe) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_provider);

        Bundle bundle = new Bundle();
        bundle.putSerializable(AllRecipesActivity.INTENT_EXTRA_RECIPE, simpleRecipe);

        Intent recipeUpdateIntent = new Intent(context, AllRecipesActivity.class);
        recipeUpdateIntent.setAction(BakingService.ACTION_UPDATE_RECIPE);
        recipeUpdateIntent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, recipeUpdateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(simpleRecipe != null){
            views.setTextViewText(R.id.appwidget_recipe_name, simpleRecipe.getName());
            views.setOnClickPendingIntent(R.id.appwidget_apple_pie, pendingIntent);
        }



        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager,
                                        int[] appWidgetIds, SimpleRecipe simpleRecipe) {
        for(int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId, simpleRecipe);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateAppWidgets(context, appWidgetManager, appWidgetIds, null);
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

