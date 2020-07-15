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

import java.util.regex.Pattern;

public class FragmentInstructions extends Fragment {

    private String mInstructions;
    private int mStepNr;

    public FragmentInstructions(){

    }

    public void setStep(int position) { mStepNr = position; }

    public void setInstructions(String instructions){
        mInstructions = instructions;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_instructions, container, false);

        TextView textView = (TextView) rootView.findViewById(R.id.instructions_text_view);

        String instructions = getInstructions();
        if(instructions != null && !instructions.isEmpty()){
            instructions = instructions.replaceAll("�", "\u00B0");

            textView.setText(instructions);
        }


        return rootView;
    }

    private String getInstructions(){
        if(mStepNr == 0){
            return mInstructions;
        }
        return mInstructions.replaceFirst("\\d+", String.valueOf(mStepNr));
    }
}
