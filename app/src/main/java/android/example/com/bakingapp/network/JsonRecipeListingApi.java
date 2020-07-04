package android.example.com.bakingapp.network;

import android.example.com.bakingapp.listingModel.SimpleRecipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonRecipeListingApi {

    @GET("baking.json")
    Call<List<SimpleRecipe>> getRecipes();
}
