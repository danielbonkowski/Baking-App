package android.example.com.bakingapp.listingModel;

import java.io.Serializable;
import java.util.List;

public class SimpleRecipe implements Serializable {


    private int id;
    private String name;
    private List<SimpleIngredient> ingredients;
    private List<SimpleStep> steps;
    private int servings;
    private String image;

    public SimpleRecipe(int id, String name, List<SimpleIngredient> ingredients, List<SimpleStep> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SimpleIngredient> getIngredients() {
        return ingredients;
    }

    public List<SimpleStep> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public String getIngredientsToString(){
        String ingredientsString = "";
        if(ingredients != null){

            for(int i = 0; i < ingredients.size(); i++){
                ingredientsString += ingredients.get(i).getName();

                if(i != ingredients.size() - 1) {
                    ingredientsString += ", ";
                }
            }
        }
        return ingredientsString;
    }

}
