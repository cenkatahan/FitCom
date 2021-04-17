package com.FitCom.fitcomapplication.NutritionScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.FitCom.fitcomapplication.BlogScreen.BlogActivity;
import com.FitCom.fitcomapplication.ExerciseScreen.ExerciseActivity;
import com.FitCom.fitcomapplication.HomePageActivity;
import com.FitCom.fitcomapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class NutritionsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabItem allNutr, myNutr;
    private ViewPager viewPager;
    private AdapterForNutrViewPager adapterForNutrViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutritions);

        tabLayout = findViewById(R.id.tabLayout_nutrition);
        allNutr = findViewById(R.id.tab_all_nutrition);
        myNutr = findViewById(R.id.tab_my_nutrition);
        viewPager = findViewById(R.id.viewpager_nutrition);

        adapterForNutrViewPager = new AdapterForNutrViewPager(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapterForNutrViewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        BottomNavigationView bnv = findViewById(R.id.bottom_nav_ViewN);
        bnv.setSelectedItemId(R.id.nutrition);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nutrition:
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
                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
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
                        startActivity(new Intent(getApplicationContext(), HomePageActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
            }
        });
    }
}