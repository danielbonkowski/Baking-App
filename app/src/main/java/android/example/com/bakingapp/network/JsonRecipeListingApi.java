package android.example.com.bakingapp.network;

import android.example.com.bakingapp.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonRecipeListingApi {

    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
