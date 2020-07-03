package android.example.com.bakingapp.ui;

import android.example.com.bakingapp.R;
import android.example.com.bakingapp.databinding.FragmentInstructionsBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

public class FragmentInstructions extends Fragment {

    private String mInstructions;

    public FragmentInstructions(){

    }

    public void setInstructions(String instructions){
        mInstructions = instructions;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FragmentInstructionsBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_instructions,container, false);

        binding.instructionsTextView.setText(mInstructions);

        return binding.getRoot();
    }
}
