package com.FitCom.fitcomapplication.NutritionScreen;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecAdaptorNutrition extends RecyclerView.Adapter<RecAdaptorNutrition.NutritionPlaceHolder> {


    @NonNull
    @Override
    public NutritionPlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NutritionPlaceHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class NutritionPlaceHolder extends RecyclerView.ViewHolder{

        public NutritionPlaceHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
