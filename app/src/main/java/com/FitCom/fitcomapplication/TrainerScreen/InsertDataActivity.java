package com.FitCom.fitcomapplication.TrainerScreen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import com.FitCom.fitcomapplication.BlogScreen.BlogActivity;
import com.FitCom.fitcomapplication.ExerciseScreen.ExerciseActivity;
import com.FitCom.fitcomapplication.HomeScreen.HomePageActivity;
import com.FitCom.fitcomapplication.NutritionScreen.NutritionsActivity;
import com.FitCom.fitcomapplication.R;
import com.FitCom.fitcomapplication.SettingScreen.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class InsertDataActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        BottomNavigationView bnv = findViewById(R.id.bottom_nav_View_INS);
        bnv.setSelectedItemId(R.id.trainer);
        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nutrition:
                    startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.exercise:
                    startActivity(new Intent(getApplicationContext(), ExerciseActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.blog:
                    startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    overridePendingTransition(0, 0);
                    return true;

                case R.id.trainer:
                    return true;
            }
            return false;
        });

        bnv.setOnNavigationItemReselectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nutrition:
                    startActivity(new Intent(getApplicationContext(), NutritionsActivity.class));
                    overridePendingTransition(0, 0);
                    break;

                case R.id.exercise:
                    startActivity(new Intent(getApplicationContext(), ExerciseActivity.class));
                    overridePendingTransition(0, 0);
                    break;

                case R.id.blog:
                    startActivity(new Intent(getApplicationContext(), BlogActivity.class));
                    overridePendingTransition(0, 0);
                    break;

                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                    overridePendingTransition(0, 0);
                    break;

                case R.id.trainer:
                    break;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent(getString(R.string.str_download));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(InsertDataActivity.this, SettingActivity.class);
            finish();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        shareActionProvider.setShareIntent(intent);
    }
}