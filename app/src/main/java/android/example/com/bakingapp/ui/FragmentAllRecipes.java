package android.example.com.bakingapp.ui;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleIngredient;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.example.com.bakingapp.listingModel.SimpleStep;
import android.example.com.bakingapp.network.AppExecutors;
import android.example.com.bakingapp.roomModel.AppDatabase;
import android.example.com.bakingapp.roomModel.Ingredient;
import android.example.com.bakingapp.roomModel.RecipeWithIngredientsAndSteps;
import android.example.com.bakingapp.roomModel.Step;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentAllRecipes extends Fragment implements AllRecipesAdapter.OnRecipeListener {

    private List<SimpleRecipe> mSimpleRecipes;
    private OnRecipeClickListener mCallback;
    private AppDatabase mDb;
    private RecyclerView mRecyclerView;
    private AllRecipesAdapter mAdapter;

    public interface OnRecipeClickListener{
        void onRecipeSelected(SimpleRecipe simpleRecipe);
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


        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mAdapter = new AllRecipesAdapter(getContext(), this);
        mAdapter.setRecipes(mSimpleRecipes);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipes_recycler_view);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void setRecipes() {

        mDb = AppDatabase.getInstance(getContext());
        final LiveData<List<RecipeWithIngredientsAndSteps>> recipeWithIngredientsAndSteps =
                mDb.recipeDao().getRecipesWithIngredientsAndSteps();
        recipeWithIngredientsAndSteps.observe(this, new Observer<List<RecipeWithIngredientsAndSteps>>() {
            @Override
            public void onChanged(List<RecipeWithIngredientsAndSteps> recipeWithIngredientsAndSteps) {
                List<SimpleRecipe> simpleRecipes = new ArrayList<>();
                for(RecipeWithIngredientsAndSteps fullRecipe :recipeWithIngredientsAndSteps){

                    List<SimpleIngredient> simpleIngredients = new ArrayList<>();
                    List<SimpleStep> simpleSteps = new ArrayList<>();
                    for(Ingredient ingredient : fullRecipe.ingredients){
                        SimpleIngredient simpleIngredient = new SimpleIngredient(ingredient.getIngredientId(),
                                ingredient.getQuantity(), ingredient.getMeasure(), ingredient.getIngredientName());
                        simpleIngredients.add(simpleIngredient);
                    }

                    for(Step step : fullRecipe.steps){
                        SimpleStep simpleStep = new SimpleStep(step.getStepId(), step.getShortDescription(),
                                step.getDescription(), step.getVideoUrl(), step.getThumbnailUrl());
                        simpleSteps.add(simpleStep);
                    }

                    SimpleRecipe simpleRecipe = new SimpleRecipe(fullRecipe.recipe.getRecipeId(),
                            fullRecipe.recipe.getRecipeName(), simpleIngredients, simpleSteps,
                            fullRecipe.recipe.getServings(), fullRecipe.recipe.getImage());
                    simpleRecipes.add(simpleRecipe);
                }

                mSimpleRecipes = simpleRecipes;


                mAdapter.setRecipes(mSimpleRecipes);
            }
        });



    }

    @Override
    public void onRecipeClick(int position) {
        mCallback.onRecipeSelected(mSimpleRecipes.get(position));
    }
}
