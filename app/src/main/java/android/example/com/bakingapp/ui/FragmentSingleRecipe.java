package android.example.com.bakingapp.ui;

import android.example.com.bakingapp.R;
import android.example.com.bakingapp.databinding.FragmentSingleRecipeStepsBinding;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.model.Step;
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

public class FragmentSingleRecipe extends Fragment implements SingleRecipeAdapter.OnStepListener {

    Recipe mRecipe;
    OnStepClickListener mOnStepClickListener;

    public interface OnStepClickListener{
        void onStepClick(Step step);
    }


    public FragmentSingleRecipe(OnStepClickListener onStepClickListener) {
        mOnStepClickListener = onStepClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentSingleRecipeStepsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_single_recipe_steps,
                container, false);

        if(mRecipe != null){
            binding.singleRecipeIngredientsTextView.setText(mRecipe.getIngredientsToString());

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                    RecyclerView.VERTICAL, false);
            SingleRecipeAdapter singleRecipeAdapter = new SingleRecipeAdapter(getContext(),this);
            SingleRecipeAdapter.setRecipe(mRecipe);

            binding.singleRecipeStepsRecyclerView.setLayoutManager(layoutManager);
            binding.singleRecipeStepsRecyclerView.setAdapter(singleRecipeAdapter);

            return binding.getRoot();
        }

        return null;
    }

    public void setRecipe(Recipe recipe){
        mRecipe = recipe;
    }


    @Override
    public void onStepClick(int position) {
        mOnStepClickListener.onStepClick(mRecipe.getSteps().get(position));
    }
}
