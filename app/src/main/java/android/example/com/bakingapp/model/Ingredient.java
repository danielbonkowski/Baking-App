package android.example.com.bakingapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private double quantity;
    private String measure;
    @SerializedName("ingredient")
    private String name;

    public Ingredient(double quantity, String measure, String name) {
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
