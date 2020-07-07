package android.example.com.bakingapp.ui;

import android.example.com.bakingapp.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentCookingGraphic extends Fragment {

    public FragmentCookingGraphic(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cooking_graphic, container, false);

        ImageView imageView = rootView.findViewById(R.id.fragment_graphic_image_view);
        imageView.setImageResource(R.drawable.cooking_graphic);

        return rootView;
    }
}
