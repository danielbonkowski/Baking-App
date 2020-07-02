package android.example.com.bakingapp.network;

import android.example.com.bakingapp.model.Ingredient;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.model.Step;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static List<Recipe> mRecipes;

    public static List<Recipe> getmRecipes() {
        return mRecipes;
    }

    public static void getRecipesFromApi(){

        final List<Recipe>[] recipes = new List[]{null};

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonRecipeListingApi jsonRecipeListingApi = retrofit.create(JsonRecipeListingApi.class);

        Call<List<Recipe>> call = jsonRecipeListingApi.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {

                if(!response.isSuccessful()){
                    Log.d(TAG, "Code: " + response.code());
                    return;
                }


                setRecipes(response.body());
                List<Recipe> myRecipes = response.body();
                for(Recipe recipe: myRecipes){
                    Log.d(TAG, "Recipe name: " + recipe.getName());
                    for(Ingredient ingredient: recipe.getIngredients()){
                        Log.d(TAG, "Ingredient: " + ingredient.getName());
                    }
                    for(Step step: recipe.getSteps()){
                        Log.d(TAG, "Step: " + step.getDescription());
                    }
                    Log.d(TAG, "Servings: " + String.valueOf(recipe.getServings()));
                    Log.d(TAG, "Image: " + recipe.getImage());

                }

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e(TAG, "Error getting json objects from server");
                Log.e(TAG, t.getMessage());
            }
        });

    }

    private static void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
    }

}
