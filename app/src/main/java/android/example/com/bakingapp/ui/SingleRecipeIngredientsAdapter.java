package android.example.com.bakingapp.ui;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.utilities.RecipeUtilities;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleRecipeIngredientsAdapter extends RecyclerView.Adapter<SingleRecipeIngredientsAdapter.SingleRecipeIngredientsViewHolder> {

    SimpleRecipe mSimpleRecipe;
    final Context mContext;

    public void setSimpleRecipe(SimpleRecipe simpleRecipe){
        mSimpleRecipe = simpleRecipe;
    }

    SingleRecipeIngredientsAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public SingleRecipeIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ingredient_steps_item, parent, false);
        return new SingleRecipeIngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleRecipeIngredientsViewHolder holder, int position) {

        Double quantity = mSimpleRecipe.getIngredients().get(position).getQuantity();
        String measure = mSimpleRecipe.getIngredients().get(position).getMeasure();
        String ingredientName = mSimpleRecipe.getIngredients().get(position).getName();

        holder.measure.setText(RecipeUtilities.getFullMeasureName(mContext, quantity, measure));
        holder.quantity.setText(RecipeUtilities.getQuantity(quantity));
        holder.ingredientName.setText(ingredientName);

    }

    @Override
    public int getItemCount() {
        if(mSimpleRecipe == null) return 0;
        return mSimpleRecipe.getIngredients().size();
    }

    public static class SingleRecipeIngredientsViewHolder extends RecyclerView.ViewHolder{

        final TextView measure;
        final TextView quantity;
        final TextView ingredientName;

        public SingleRecipeIngredientsViewHolder(@NonNull View itemView) {
            super(itemView);

            measure = itemView.findViewById(R.id.ingredient_steps_measure);
            quantity = itemView.findViewById(R.id.ingredient_steps_quantity);
            ingredientName = itemView.findViewById(R.id.ingredient_steps_name);
        }
    }
}
