package android.example.com.bakingapp.listingModel;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SimpleIngredient implements Serializable {

    private int id;
    private double quantity;
    private String measure;
    @SerializedName("ingredient")
    private String name;

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

    public int getId() { return id; }



}
