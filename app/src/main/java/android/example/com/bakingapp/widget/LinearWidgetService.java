package android.example.com.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.utilities.RecipeUtilities;
import android.example.com.bakingapp.listingModel.SimpleIngredient;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

public class LinearWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("TAG", "First ingredient is tested");
        return new LinearLayoutRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}


class LinearLayoutRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = LinearLayoutRemoteViewsFactory.class.getSimpleName();

    final Context mContext;
    SimpleRecipe mSimpleRecipe;

    public LinearLayoutRemoteViewsFactory(Context applicationContext, Intent intent) {
        Log.d(TAG, "First ingredient is tested");
        this.mContext = applicationContext;
        mSimpleRecipe = RecipeUtilities.getSimpleRecipe();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d(TAG, "First ingredient is tested");
        mSimpleRecipe = RecipeUtilities.getSimpleRecipe();

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "First ingredient is tested");
    }

    @Override
    public int getCount() {
        Log.d(TAG, "First ingredient is tested");
        if(mSimpleRecipe == null) return 0;
        return mSimpleRecipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        SimpleIngredient ingredient = mSimpleRecipe.getIngredients().get(position);
        Log.d(TAG, "First ingredient is: " + ingredient.getName());

        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_widget_item);

        Double quantity = ingredient.getQuantity();
        String measureType = ingredient.getMeasure();
        String ingredientName = ingredient.getName();
        String fullMeasureName = RecipeUtilities.getFullMeasureName(mContext, quantity, measureType);
        String stringQuantity = RecipeUtilities.getQuantity(quantity);

        views.setTextViewText(R.id.ingredient_widget_quantity, stringQuantity);
        views.setTextViewText(R.id.ingredient_widget_measure, fullMeasureName);
        views.setTextViewText(R.id.ingredient_widget_name, ingredientName);

        return views;
    }


    @Override
    public RemoteViews getLoadingView() {
        Log.d(TAG, "First ingredient is tested");
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.d(TAG, "First ingredient is tested");
        return 1;
    }

    @Override
    public long getItemId(int position) {
        Log.d(TAG, "First ingredient is tested");
        return position;
    }

    @Override
    public boolean hasStableIds() {
        Log.d(TAG, "First ingredient is tested");
        return true;
    }
}
