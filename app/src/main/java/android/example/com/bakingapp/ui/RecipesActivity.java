package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.example.com.bakingapp.R;
import android.example.com.bakingapp.model.Recipe;
import android.example.com.bakingapp.network.NetworkUtils;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentRecipes fragmentRecipes = new FragmentRecipes();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipes_container, fragmentRecipes)
                .commit();
    }
}