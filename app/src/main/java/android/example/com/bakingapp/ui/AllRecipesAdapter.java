package android.example.com.bakingapp.ui;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.network.NetworkUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllRecipesAdapter extends RecyclerView.Adapter<AllRecipesAdapter.RecipesViewHolder> {

    private static final String TAG = AllRecipesAdapter.class.getSimpleName();

    List<Recipe> mRecipes;
    Context mContext;
    private OnRecipeListener mOnRecipeListener;

    public interface OnRecipeListener {
        void onRecipeClick(int position);
    };

    public List<Recipe> getRecipes(){
        return mRecipes;
    }

    public void setRecipes(List<Recipe> recipes){
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public AllRecipesAdapter(Context context, OnRecipeListener onRecipeListener){
        mRecipes = NetworkUtils.getmRecipes();
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

        holder.recipeName.setText(mRecipes.get(position).getName());
        holder.ingredients.setText(mRecipes.get(position).getIngredientsToString());
    }

    @Override
    public int getItemCount() {
        if(mRecipes == null) return 0;
        return mRecipes.size();
    }

    public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView recipeName;
        public TextView ingredients;

        public RecipesViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipes_item_recipe_name);
            ingredients = itemView.findViewById(R.id.recipes_item_recipe_ingredients);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }
}
