package com.FitCom.fitcomapplication.NutritionScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.FitCom.fitcomapplication.R;

import java.util.ArrayList;

public class RecAdaptorMeal extends RecyclerView.Adapter<RecAdaptorMeal.MealPlaceHolder>{

    private ArrayList<String> meal_titles;

    public RecAdaptorMeal(ArrayList<String> meal_titles) {
        this.meal_titles = meal_titles;
    }

    @NonNull
    @Override
    public MealPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rec_row_meal, parent, false);
        return new MealPlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealPlaceHolder holder, int position) {
        holder.title.setText(meal_titles.get(position));
        holder.itemView.setOnClickListener(v -> {
            MealListFragmentDirections.ActionMealListFragmentToMealDetailFragment navDir = MealListFragmentDirections.actionMealListFragmentToMealDetailFragment(position);
            navDir.setMealId(position);
            Navigation.findNavController(v).navigate(navDir);
        });
    }

    @Override
    public int getItemCount() {
        return meal_titles.size();
    }

    class MealPlaceHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public MealPlaceHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.row_meal_tv);

        }
    }
}
