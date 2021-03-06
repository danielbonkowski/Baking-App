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
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.room.Room;

import org.jetbrains.annotations.NotNull;

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
    private static JsonRecipeListingApi mJsonRecipeListingApi;
    private static Object LOCK = new Object();

    private static JsonRecipeListingApi getJsonRecipeListingApi(){
        if(mJsonRecipeListingApi == null){
            synchronized (LOCK){
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(RECIPE_LISTING_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                mJsonRecipeListingApi = retrofit.create(JsonRecipeListingApi.class);
            }
        }
        return mJsonRecipeListingApi;
    }

    public interface DownloaderCallback{
        void onDownloaded(boolean isFinished);
    }

    public static void getRecipesFromApi(Context context, final DownloaderCallback callback,
                                         @Nullable final SimpleIdlingResource idlingResource){

        if(idlingResource != null){
            idlingResource.setIdleState(false);
        }

        mDb = AppDatabase.getInstance(context);

        Call<List<SimpleRecipe>> call = getJsonRecipeListingApi().getRecipes();

        call.enqueue(new Callback<List<SimpleRecipe>>() {
            @Override
            public void onResponse(@NotNull Call<List<SimpleRecipe>> call, @NotNull final Response<List<SimpleRecipe>> response) {

                Log.d(TAG, "Getting API data...");
                AppExecutors.getInstance().diskIO().execute(() -> {

                    if(!response.isSuccessful()){
                        Log.d(TAG, "Code: " + response.code());
                        return;
                    }

                    List<SimpleRecipe> mySimpleRecipes = response.body();

                    for(SimpleRecipe simpleRecipe : mySimpleRecipes){

                        Recipe recipe = mDb.recipeDao().getRecipe(simpleRecipe.getId());

                        if(recipe == null){
                            Log.d(TAG, "Recipe Live Data is null, mySimpleRecipes size: " + mySimpleRecipes.size());
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
                });


            }

            @Override
            public void onFailure(@NotNull Call<List<SimpleRecipe>> call, @NotNull Throwable t) {
                Log.e(TAG, "Error getting json objects from server");
                Log.e(TAG, t.getMessage());
            }
        });

    }

    public static boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null){
            return false;
        }

        Network network = connectivityManager.getActiveNetwork();
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(network == null || networkInfo == null){
            return false;
        }

        return networkInfo.isConnected();
    }

}
