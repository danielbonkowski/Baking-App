package android.example.com.bakingapp.roomModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ingredient")
public class Ingredient implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredient_id")
    private int ingredientId;
    @ColumnInfo(name = "parent_recipe_id")
    private final int parentRecipeId;
    private final double quantity;
    private final String measure;
    @ColumnInfo(name = "ingredient_name")
    private final String ingredientName;

    public Ingredient(int ingredientId, int parentRecipeId, double quantity, String measure, String ingredientName) {
        this.ingredientId = ingredientId;
        this.parentRecipeId = parentRecipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredientName;
    }

    @Ignore
    public Ingredient(int parentRecipeId, double quantity, String measure, String ingredientName) {
        this.parentRecipeId = parentRecipeId;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredientName = ingredientName;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getIngredientId() { return ingredientId; }

    public int getParentRecipeId() { return this.parentRecipeId; }



}
