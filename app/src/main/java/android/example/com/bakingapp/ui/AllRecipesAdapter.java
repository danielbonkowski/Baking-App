package android.example.com.bakingapp.ui;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllRecipesAdapter extends RecyclerView.Adapter<AllRecipesAdapter.RecipesViewHolder> {

    private static final String TAG = AllRecipesAdapter.class.getSimpleName();
    private static final int INTRODUCTION = 1;

    List<SimpleRecipe> mSimpleRecipes;
    final Context mContext;
    private final OnRecipeListener mOnRecipeListener;

    public interface OnRecipeListener {
        void onRecipeClick(int position);
    }

    public void setRecipes(List<SimpleRecipe> simpleRecipes){
        Log.d(TAG, "Notify data set changed");
        mSimpleRecipes = simpleRecipes;
        notifyDataSetChanged();
    }

    public AllRecipesAdapter(Context context, OnRecipeListener onRecipeListener){
        mContext = context;
        mOnRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recipes_item, parent, false);
        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder holder, int position) {

        holder.recipeName.setText(mSimpleRecipes.get(position).getName());
        holder.servings.setText(String.valueOf(mSimpleRecipes.get(position).getServings()));
        holder.servingsLabel.setText(mContext.getResources().getString(R.string.servings));
        holder.steps.setText(String.valueOf(mSimpleRecipes.get(position).getSteps().size() - INTRODUCTION));
        holder.stepsLabel.setText(mContext.getResources().getString(R.string.steps_label));
        holder.ingredients.setText(mSimpleRecipes.get(position).getIngredientsToString());
    }

    @Override
    public int getItemCount() {
        if(mSimpleRecipes == null) return 0;
        return mSimpleRecipes.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final TextView recipeName;
        final TextView servings;
        final TextView servingsLabel;
        final TextView steps;
        final TextView stepsLabel;
        final TextView ingredients;
        final CardView cardView;


        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipes_item_recipe_name);
            servings = itemView.findViewById(R.id.recipes_item_recipe_servings);
            servingsLabel = itemView.findViewById(R.id.recipes_item_recipe_servings_label);
            steps = itemView.findViewById(R.id.recipes_item_recipe_steps);
            stepsLabel = itemView.findViewById(R.id.recipes_item_recipe_steps_label);
            ingredients = itemView.findViewById(R.id.recipes_item_recipe_ingredients);
            cardView = itemView.findViewById(R.id.materialCardViewAllRecipes);

            cardView.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }
}
