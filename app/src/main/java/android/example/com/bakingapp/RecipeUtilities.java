package android.example.com.bakingapp;

import android.content.Context;
import android.example.com.bakingapp.listingModel.SimpleRecipe;

public class RecipeUtilities {


    private static SimpleRecipe mSimpleRecipe;

    private static int getMeasureResId(String measure){

        String upperMeasure = measure.toUpperCase();
        switch (upperMeasure){
            case "G":
                return R.plurals.grams;
            case "K":
                return R.plurals.kilos;
            case "CUP":
                return R.plurals.cups;
            case "TBLSP":
                return R.plurals.tablespoons;
            case "UNIT":
                return R.plurals.units;
            case "TSP":
                return R.plurals.teaspoons;
            case "OZ":
                return R.plurals.ounces;
            default:
                return 0;
        }
    }

    public static String getFullMeasureName(Context context, Double quantity, String measureType) {
        return context.getResources()
                .getQuantityString(RecipeUtilities.getMeasureResId(measureType), quantity.intValue());
    }

    public static String getQuantity(Double quantity) {

        if(quantity != Math.floor(quantity)){
            return String.valueOf(quantity);
        }else{
            return String.valueOf(quantity.intValue());
        }
    }

    public static SimpleRecipe getSimpleRecipe() {
        return mSimpleRecipe;
    }

    public static void setSimpleRecipe(SimpleRecipe simpleRecipe) {
        RecipeUtilities.mSimpleRecipe = simpleRecipe;
    }
}
