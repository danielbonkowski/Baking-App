package android.example.com.bakingapp.ui;

import android.example.com.bakingapp.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.instructions_text_view);

        textView.setText(mInstructions);

        return rootView;
    }
}