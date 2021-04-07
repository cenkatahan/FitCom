package com.FitCom.fitcomapplication.ExerciseScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.FitCom.fitcomapplication.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class ExerciseActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private TabItem tabItemAllPrograms;
    private TabItem tabItemMyPrograms;
    private ViewPager viewPager;
    private AdapterForViewPager adapterForViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        tabLayout = findViewById(R.id.tabLayout_exercise);
        tabItemAllPrograms = findViewById(R.id.tab_all_programs);
        tabItemMyPrograms = findViewById(R.id.tab_my_programs);
        viewPager = findViewById(R.id.viewpager_exercise);

        adapterForViewPager = new AdapterForViewPager(getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(adapterForViewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}