package com.FitCom.fitcomapplication.ExerciseScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Placeholder;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.FitCom.fitcomapplication.R;
import org.w3c.dom.Text;
import java.util.ArrayList;

public class RecAdaptorExercises extends RecyclerView.Adapter<RecAdaptorExercises.PlaceHolder> {

    ArrayList<String> exercises;
    ArrayList<String> categories;

    public RecAdaptorExercises(ArrayList<String> exercises, ArrayList<String> categories){

        this.categories = categories;
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public PlaceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceHolder holder, int position) {
        // after some point in time, try to solve the difference between editing an existing exercise and creating a new one.
        // Osman handled this by sending a string that indicates the operation is editing/creating by navigation component.

        holder.title.setText(exercises.get(position));
        holder.category.setText(categories.get(position));
        holder.itemView.setOnClickListener(v -> {


            ExerciseListFragmentDirections.ActionExerciseListFragmentToExerciseDetailFragment navDir = ExerciseListFragmentDirections.actionExerciseListFragmentToExerciseDetailFragment(position);
            navDir.setExerciseId(position);
            Navigation.findNavController(v).navigate(navDir);
        });
    }

    @Override
    public int getItemCount() {

        return exercises.size();
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
