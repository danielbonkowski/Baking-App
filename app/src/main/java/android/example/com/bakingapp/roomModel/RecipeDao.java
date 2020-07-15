package android.example.com.bakingapp.roomModel;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Query("SELECT * FROM recipe")
    LiveData<List<RecipeWithIngredientsAndSteps>> getRecipesWithIngredientsAndSteps();

    @Query("SELECT * FROM recipe WHERE recipe_id = :id")
    Recipe getRecipe(int id);

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

}
