package android.example.com.bakingapp.ui;

import android.content.Context;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.listingModel.SimpleRecipe;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentSingleRecipe extends Fragment implements SingleRecipeAdapter.OnStepListener {

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

        TextView ingredientsTextView = (TextView) rootView.findViewById(R.id.single_recipe_ingredients_text_view);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.single_recipe_steps_recycler_view);


        if(mSimpleRecipe != null){
            ingredientsTextView.setText(mSimpleRecipe.getIngredientsToString());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                    RecyclerView.VERTICAL, false);
            SingleRecipeAdapter singleRecipeAdapter = new SingleRecipeAdapter(getContext(),this);
            SingleRecipeAdapter.setRecipe(mSimpleRecipe);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(singleRecipeAdapter);

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
