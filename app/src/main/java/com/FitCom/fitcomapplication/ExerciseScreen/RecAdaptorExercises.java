package com.FitCom.fitcomapplication.ExerciseScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Placeholder;
import androidx.recyclerview.widget.RecyclerView;

import com.FitCom.fitcomapplication.R;

import org.w3c.dom.Text;

public class RecAdaptorExercises extends RecyclerView.Adapter<RecAdaptorExercises.PlaceHolder> {


    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);

        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        holder.title.setText("BENCH PRESS");
        holder.category.setText("CHEST");
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class PlaceHolder extends RecyclerView.ViewHolder{

        private TextView category, title;

        public PlaceHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.rec_row_title);
            category = itemView.findViewById(R.id.rec_row_category);
        }
    }
}
