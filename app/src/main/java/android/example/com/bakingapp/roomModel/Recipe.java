package android.example.com.bakingapp.roomModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "recipe")
public class Recipe implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    private int recipeId;
    @ColumnInfo(name = "recipe_name")
    private final String recipeName;
    private final int servings;
    private final String image;


    public Recipe(int recipeId, String recipeName, int servings, String image) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.servings = servings;
        this.image = image;
    }

    @Ignore
    public Recipe(String recipeName, int servings, String image) {
        this.recipeName = recipeName;
        this.servings = servings;
        this.image = image;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }


}
