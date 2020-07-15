package android.example.com.bakingapp.network;

import android.content.Context;
import android.example.com.bakingapp.idlingResource.SimpleIdlingResource;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.example.com.bakingapp.roomModel.AppDatabase;
import android.example.com.bakingapp.listingModel.SimpleIngredient;
import android.example.com.bakingapp.listingModel.SimpleStep;
import android.example.com.bakingapp.roomModel.Ingredient;
import android.example.com.bakingapp.roomModel.Recipe;
import android.example.com.bakingapp.roomModel.Step;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String RECIPE_LISTING_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    private static AppDatabase mDb;

    public interface DownloaderCallback{
        void onDownloaded(boolean isFinished);
    }

    public static void getRecipesFromApi(Context context, final DownloaderCallback callback,
                                         @Nullable final SimpleIdlingResource idlingResource){

        if(idlingResource != null){
            idlingResource.setIdleState(false);
        }

        mDb = AppDatabase.getInstance(context);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RECIPE_LISTING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonRecipeListingApi jsonRecipeListingApi = retrofit.create(JsonRecipeListingApi.class);

        Call<List<SimpleRecipe>> call = jsonRecipeListingApi.getRecipes();

        call.enqueue(new Callback<List<SimpleRecipe>>() {
            @Override
            public void onResponse(Call<List<SimpleRecipe>> call, final Response<List<SimpleRecipe>> response) {

                Log.d(TAG, "Getting API data...");
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if(!response.isSuccessful()){
                            Log.d(TAG, "Code: " + response.code());
                            return;
                        }

                        List<SimpleRecipe> mySimpleRecipes = response.body();

                        for(SimpleRecipe simpleRecipe : mySimpleRecipes){


                            Recipe recipe = mDb.recipeDao().getRecipe(simpleRecipe.getId());




                            if(recipe == null){
                                Log.d(TAG, "Recipe id: " + simpleRecipe.getId() + " is not in db");
                            }else{
                                Log.d(TAG, "Recipe id: " + recipe.getRecipeId() + " is in db");
                                Log.d(TAG, "Recipe id to insert: " + simpleRecipe.getId());
                            }

                            if(recipe == null){
                                Log.d(TAG, "Recipe Live Data is null, mySipmleRecipes size: " + mySimpleRecipes.size());
                                mDb.recipeDao().insertRecipe(new Recipe(simpleRecipe.getId(), simpleRecipe.getName(),
                                        simpleRecipe.getServings(), simpleRecipe.getImage()));


                                for(SimpleIngredient simpleIngredient : simpleRecipe.getIngredients()){
                                    mDb.recipeDao().insertIngredient(new Ingredient(simpleRecipe.getId(),
                                            simpleIngredient.getQuantity(), simpleIngredient.getMeasure(),
                                            simpleIngredient.getName()));
                                }
                                for(SimpleStep simpleStep : simpleRecipe.getSteps()){
                                    mDb.recipeDao().insertStep(new Step(simpleRecipe.getId(),
                                            simpleStep.getShortDescription(), simpleStep.getDescription(),
                                            simpleStep.getVideoUrl(), simpleStep.getThumbnailUrl()));
                                }
                            }else{

                                mDb.recipeDao().updateRecipe(new Recipe(simpleRecipe.getId(), simpleRecipe.getName(),
                                        simpleRecipe.getServings(), simpleRecipe.getImage()));


                                for(SimpleIngredient simpleIngredient : simpleRecipe.getIngredients()){
                                    mDb.recipeDao().updateIngredient(new Ingredient(simpleRecipe.getId(),
                                            simpleIngredient.getQuantity(), simpleIngredient.getMeasure(),
                                            simpleIngredient.getName()));
                                }
                                for(SimpleStep simpleStep : simpleRecipe.getSteps()){
                                    mDb.recipeDao().updateStep(new Step(simpleRecipe.getId(),
                                            simpleStep.getShortDescription(), simpleStep.getDescription(),
                                            simpleStep.getVideoUrl(), simpleStep.getThumbnailUrl()));
                                }
                            }
                            if (callback != null) {
                                callback.onDownloaded(true);
                                if (idlingResource != null) {
                                    idlingResource.setIdleState(true);
                                }
                            }
                        }
                    }
                });


            }

            @Override
            public void onFailure(Call<List<SimpleRecipe>> call, Throwable t) {
                Log.e(TAG, "Error getting json objects from server");
                Log.e(TAG, t.getMessage());
            }
        });

    }

}
