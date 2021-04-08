package com.FitCom.fitcomapplication.NutritionScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.FitCom.fitcomapplication.R;
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
    }
}