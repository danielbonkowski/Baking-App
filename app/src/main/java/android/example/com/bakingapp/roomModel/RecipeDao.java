package android.example.com.bakingapp.roomModel;

import android.example.com.bakingapp.listingModel.SimpleRecipe;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    public LiveData<List<RecipeWithIngredientsAndSteps>> getRecipesWithIngredientsAndSteps();

    @Query("SELECT * FROM recipe WHERE recipe_id = :id")
    public Recipe getRecipe(int id);

    @Insert
    void insertRecipe(Recipe recipe);

    @Insert
    void insertIngredient(Ingredient ingredient);

    @Insert
    void insertStep(Step step);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRecipe(Recipe recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredient(Ingredient ingredient);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateStep(Step step);

    @Delete
    void deleteRecipe(Recipe recipe);

    @Delete
    void deleteIngredient(Ingredient ingredient);

    @Delete
    void deleteStep(Step step);

    @Query("DELETE FROM recipe")
    void dropRecipe();

    @Query("DELETE FROM step")
    void dropStep();

    @Query("DELETE FROM ingredient")
    void dropIngredient();
}
