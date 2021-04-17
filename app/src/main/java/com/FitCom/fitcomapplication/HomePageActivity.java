package com.FitCom.fitcomapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.FitCom.fitcomapplication.BlogScreen.BlogActivity;
import com.FitCom.fitcomapplication.ExerciseScreen.ExerciseActivity;
import com.FitCom.fitcomapplication.NutritionScreen.NutritionsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {

    private Button goToExercise, goToNutrition;
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        goToExercise = findViewById(R.id.button_to_exercise);
        goToExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                startActivity(intent);
            }
        });

        goToNutrition = findViewById(R.id.button_to_nutrition);
        goToNutrition.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), NutritionsActivity.class);
            startActivity(intent);
        });

        bnv = findViewById(R.id.bottom_nav_View);
        bnv.setSelectedItemId(R.id.home);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nutrition:
                        startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.exercise:
                        startActivity(new Intent(getApplicationContext(), ExerciseActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.blog:
                        startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.home:
                        return true;
                }
                return false;
            }
        });

        bnv.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nutrition:
                        startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.exercise:
                        startActivity(new Intent(getApplicationContext(), ExerciseActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.blog:
                        startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.home:
                        break;
                }
            }
        });
    }
}