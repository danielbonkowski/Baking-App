package android.example.com.bakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleIngredient;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.example.com.bakingapp.listingModel.SimpleStep;
import android.example.com.bakingapp.roomModel.Ingredient;
import android.example.com.bakingapp.roomModel.RecipeWithIngredientsAndSteps;
import android.example.com.bakingapp.roomModel.Step;
import android.example.com.bakingapp.viewModel.AllRecipesViewModel;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentAllRecipes extends Fragment implements AllRecipesAdapter.OnRecipeListener {

    private static final String TAG = FragmentAllRecipes.class.getSimpleName();

    public void setSimpleRecipes(List<SimpleRecipe> simpleRecipes) {
        this.mSimpleRecipes = simpleRecipes;
    }

    private List<SimpleRecipe> mSimpleRecipes;
    private OnRecipeClickListener mCallback;
    private RecyclerView mRecyclerView;
    private AllRecipesAdapter mAdapter;
    private TextView mErrorTextView;
    private ProgressBar mProgressBar;

    public interface OnRecipeClickListener{
        void onRecipeSelected(SimpleRecipe simpleRecipe);
    }

    public FragmentAllRecipes(){

    }



    private void displayProgressBar(){
        if(mProgressBar != null && mErrorTextView != null){
            mProgressBar.setVisibility(View.VISIBLE);
            mErrorTextView.setVisibility(View.GONE);
        }
    }

    private void displayErrorMessage(){
        if(mProgressBar != null && mErrorTextView != null){
            Log.d(TAG, "Display error message");
            mProgressBar.setVisibility(View.GONE);
            mErrorTextView.setVisibility(View.VISIBLE);
        }

    }

    private void hideProgressBar(){
        if(mProgressBar != null){
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void hideErrorMessage(){
        if(mErrorTextView != null){
            mErrorTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        mProgressBar = rootView.findViewById(R.id.allRecipes_progress_bar);
        mErrorTextView = rootView.findViewById(R.id.allRecipes_error_text_view);
        displayProgressBar();

        RecyclerView.LayoutManager layoutManager = null;

        double smallestScreenWith = getSmallestScreenWidth();
        Log.d(TAG, "Smallest screen width: " + smallestScreenWith);
        if(smallestScreenWith >= 600){
            layoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
        }else {
           layoutManager  = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        }



        mAdapter = new AllRecipesAdapter(getContext(), this);
        mAdapter.setRecipes(mSimpleRecipes);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recipes_recycler_view);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        hideProgressBar();
        if(mSimpleRecipes == null || mSimpleRecipes.size() == 0){
            displayErrorMessage();
        }



        AllRecipesViewModel mViewModel = ViewModelProviders.of(requireActivity()).get(AllRecipesViewModel.class);
        mViewModel.getRecipes().observe(this, recipeWithIngredientsAndSteps -> {
            Log.d(TAG,"Updating the list of recipes from LiveData from ViewModel");
            List<SimpleRecipe> simpleRecipes = new ArrayList<>();
            Log.d(TAG, "Live data object size: " + recipeWithIngredientsAndSteps.size());
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
            Log.d(TAG, "Recipes size: " + simpleRecipes.size());


            mAdapter.setRecipes(mSimpleRecipes);
            if(mSimpleRecipes == null || mSimpleRecipes.size() == 0){
                displayErrorMessage();
            }else {
                hideErrorMessage();
            }
        });


        return rootView;
    }

    public double getSmallestScreenWidth(){
        Configuration configuration = getActivity().getApplicationContext().getResources().getConfiguration();
        return configuration.smallestScreenWidthDp;
    }

    @Override
    public void onRecipeClick(int position) {
        mCallback.onRecipeSelected(mSimpleRecipes.get(position));
    }
}
