package android.example.com.bakingapp.ui;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentSingleRecipe extends Fragment implements SingleRecipeStepsAdapter.OnStepListener {

    private SimpleRecipe mSimpleRecipe;
    private OnStepClickListener mCallback;

    public interface OnStepClickListener{
        void onStepClick(int position, SimpleRecipe simpleRecipe);
    }

    public FragmentSingleRecipe() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mCallback = (OnStepClickListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnRecipeClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_single_recipe_steps, container, false);

        RecyclerView ingredientsRecyclerView = rootView.findViewById(R.id.single_recipe_ingredients_recycler_view);
        RecyclerView stepsRecyclerView = rootView.findViewById(R.id.single_recipe_steps_recycler_view);


        if(mSimpleRecipe != null){
            RecyclerView.LayoutManager ingredientsLayoutManager = new LinearLayoutManager(getContext(),
                    RecyclerView.VERTICAL, false);
            SingleRecipeIngredientsAdapter singleRecipeIngredientsAdapter =
                    new SingleRecipeIngredientsAdapter(getContext());
            singleRecipeIngredientsAdapter.setSimpleRecipe(mSimpleRecipe);

            ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
            ingredientsRecyclerView.setAdapter(singleRecipeIngredientsAdapter);


            RecyclerView.LayoutManager stepsLayoutManager = new LinearLayoutManager(getContext(),
                    RecyclerView.VERTICAL, false);
            SingleRecipeStepsAdapter singleRecipeStepsAdapter = new SingleRecipeStepsAdapter(getContext(),this);
            singleRecipeStepsAdapter.setRecipe(mSimpleRecipe);

            stepsRecyclerView.setLayoutManager(stepsLayoutManager);
            stepsRecyclerView.setAdapter(singleRecipeStepsAdapter);

            return rootView;
        }

        return null;
    }

    public void setRecipe(SimpleRecipe simpleRecipe){
        mSimpleRecipe = simpleRecipe;
    }


    @Override
    public void onStepClick(int position) {
        mCallback.onStepClick(position, mSimpleRecipe);
    }
}
