package android.example.com.bakingapp.roomModel;

import android.example.com.bakingapp.listingModel.SimpleIngredient;
import android.example.com.bakingapp.listingModel.SimpleStep;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "recipe")
public class Recipe implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id")
    private int recipeId;
    @ColumnInfo(name = "recipe_name")
    private String recipeName;
    private int servings;
    private String image;


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
