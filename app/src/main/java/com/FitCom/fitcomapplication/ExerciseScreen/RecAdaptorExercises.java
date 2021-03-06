package com.FitCom.fitcomapplication.ExerciseScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.FitCom.fitcomapplication.R;
import java.util.ArrayList;

@SuppressWarnings("ALL")
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

    @SuppressWarnings("FieldMayBeFinal")
    class PlaceHolder extends RecyclerView.ViewHolder{

        private TextView category, title;

        public PlaceHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.rec_row_title);
            category = itemView.findViewById(R.id.rec_row_category);
        }
    }
}
