package com.FitCom.fitcomapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.FitCom.fitcomapplication.ExerciseScreen.ExerciseActivity;
import com.FitCom.fitcomapplication.NutritionScreen.NutritionsActivity;

public class HomePageActivity extends AppCompatActivity {

    private Button goToExercise, goToNutrition;

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
    }

}

