package android.example.com.bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.example.com.bakingapp.R;
import android.os.Bundle;

public class SingleRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);
    }
}