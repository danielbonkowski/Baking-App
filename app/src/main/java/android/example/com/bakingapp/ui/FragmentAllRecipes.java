package android.example.com.bakingapp.ui;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.databinding.FragmentRecipesBinding;
import android.example.com.bakingapp.model.Recipe;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FragmentAllRecipes extends Fragment implements AllRecipesAdapter.OnRecipeListener {

    private List<Recipe> mRecipes;
    private OnRecipeClickListener mCallback;

    public interface OnRecipeClickListener{
        void onRecipeSelected(Recipe recipe);
    }

    public FragmentAllRecipes(){

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnRecipeClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        FragmentRecipesBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_recipes, container, false);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        AllRecipesAdapter allRecipesAdapter = new AllRecipesAdapter(getContext(), this);

        binding.recipesRecyclerView.setLayoutManager(layoutManager);
        binding.recipesRecyclerView.setAdapter(allRecipesAdapter);

        return binding.getRoot();
    }

    public void setRecipes(List<Recipe> recipes){mRecipes = recipes;}

    @Override
    public void onRecipeClick(int position) {
        mCallback.onRecipeSelected(mRecipes.get(position));
    }
}
