package android.example.com.bakingapp.listingModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SimpleIngredient implements Serializable {

    private final int id;
    private final double quantity;
    private final String measure;
    @SerializedName("ingredient")
    private final String name;

    public SimpleIngredient(int id, double quantity, String measure, String name) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }


}
