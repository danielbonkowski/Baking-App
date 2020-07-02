package android.example.com.bakingapp.ui;

import android.content.Intent;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.databinding.FragmentRecipesBinding;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.network.NetworkUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FragmentRecipes extends Fragment implements RecipesAdapter.onRecipeListener{

    private List<Recipe> mRecipes;
    private int mIndex;

    public FragmentRecipes(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        NetworkUtils.getRecipesFromApi();
        FragmentRecipesBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipes, container, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        RecipesAdapter recipesAdapter = new RecipesAdapter(getContext());

        binding.recipesRecyclerView.setLayoutManager(layoutManager);
        binding.recipesRecyclerView.setAdapter(recipesAdapter);



        return binding.getRoot();
    }

    public void setRecipes(List<Recipe> recipes){mRecipes = recipes;}
    public void setIndex(int index){mIndex = index;}

    @Override
    public void onRecipeClick(int position) {
        Intent recipeIntent = new Intent(getContext(), SingleRecipeActivity.class);
        startActivity(recipeIntent);
    }
}
