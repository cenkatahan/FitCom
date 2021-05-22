package com.FitCom.fitcomapplication.NutritionScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.FitCom.fitcomapplication.R;
import java.util.ArrayList;

public class RecAdaptorNutrition extends RecyclerView.Adapter<RecAdaptorNutrition.NutritionPlaceHolder> {

    private ArrayList<String> nutrition_names;
    private ArrayList<String> nutrition_calories;
    private ArrayList<String> nutrition_carbs;
    private ArrayList<String> nutrition_protein;
    private ArrayList<String> nutrition_fat;

    public RecAdaptorNutrition(ArrayList<String> nutrition_names, ArrayList<String> nutrition_calories,
                               ArrayList<String> nutrition_carbs, ArrayList<String> nutrition_protein, ArrayList<String> nutrition_fat) {

        this.nutrition_names = nutrition_names;
        this.nutrition_calories = nutrition_calories;
        this.nutrition_carbs = nutrition_carbs;
        this.nutrition_protein = nutrition_protein;
        this.nutrition_fat = nutrition_fat;
    }

    @NonNull
    @Override
    public NutritionPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_nutrition, parent, false);
        return new NutritionPlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionPlaceHolder holder, int position) {
        holder.names.setText(nutrition_names.get(position));
        holder.calorie.setText(nutrition_calories.get(position));
        holder.carb.setText(nutrition_carbs.get(position));
        holder.protein.setText(nutrition_protein.get(position));
        holder.fat.setText(nutrition_fat.get(position));
    }

    @Override
    public int getItemCount() {
        return nutrition_names.size();
    }

    class NutritionPlaceHolder extends RecyclerView.ViewHolder{

        private TextView names, calorie, carb, protein, fat;

        public NutritionPlaceHolder(@NonNull View itemView) {
            super(itemView);

            names = itemView.findViewById(R.id.row_nutr_name);
            calorie = itemView.findViewById(R.id.row_nutr_calorie);
            carb = itemView.findViewById(R.id.row_nutr_carb);
            protein = itemView.findViewById(R.id.row_nutr_protein);
            fat = itemView.findViewById(R.id.row_nutr_fat);

        }
    }
}
