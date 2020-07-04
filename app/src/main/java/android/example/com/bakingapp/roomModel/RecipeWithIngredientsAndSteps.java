package android.example.com.bakingapp.roomModel;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class RecipeWithIngredientsAndSteps {
    @Embedded public Recipe recipe;
    @Relation(
            entity = Ingredient.class,
            parentColumn = "recipe_id",
            entityColumn = "parent_recipe_id"
    )
    public List<Ingredient> ingredients;

    @Relation(
            entity = Step.class,
            parentColumn = "recipe_id",
            entityColumn = "parent_recipe_id"
    )
    public List<Step> steps;

}
