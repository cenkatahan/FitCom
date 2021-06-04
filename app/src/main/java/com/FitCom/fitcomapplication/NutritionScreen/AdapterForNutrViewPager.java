package com.FitCom.fitcomapplication.NutritionScreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

@SuppressWarnings("deprecation")
public class AdapterForNutrViewPager extends FragmentPagerAdapter {

    private final int TAB_COUNT;

    public AdapterForNutrViewPager(@NonNull FragmentManager fm, int behavior) {
        super(fm);
        this.TAB_COUNT = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new AllNutritionsFragment();
        }else {
            return new MealFragment();
        }
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}