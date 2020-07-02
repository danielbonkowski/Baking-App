package android.example.com.bakingapp.model;

public class Recipe {

    private int id;
    private String name;
    private Ingredient[] ingredients;
    private Step[] steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, Ingredient[] ingredients, Step[] steps, int servings, String image) {
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

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public Step[] getSteps() {
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

            for(int i = 0; i < ingredients.length; i++){
                ingredientsString += ingredients[i].getName();

                if(i != ingredients.length - 1) {
                    ingredientsString += ", ";
                }
            }
        }
        return ingredientsString;
    }
}