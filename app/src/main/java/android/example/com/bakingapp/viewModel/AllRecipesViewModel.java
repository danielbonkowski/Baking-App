package android.example.com.bakingapp.viewModel;

import android.app.Application;
import android.example.com.bakingapp.roomModel.AppDatabase;
import android.example.com.bakingapp.roomModel.RecipeWithIngredientsAndSteps;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AllRecipesViewModel  extends AndroidViewModel {

    private static final String TAG = AllRecipesViewModel.class.getSimpleName();
    private final LiveData<List<RecipeWithIngredientsAndSteps>> recipes;

    public AllRecipesViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the recipes from the database");
        recipes = database.recipeDao().getRecipesWithIngredientsAndSteps();
    }

    public LiveData<List<RecipeWithIngredientsAndSteps>> getRecipes() {
        return recipes;
    }
}
