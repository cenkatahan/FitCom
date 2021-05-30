package com.FitCom.fitcomapplication.TrainerScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.FitCom.fitcomapplication.R;

public class InsertListFragment extends Fragment {

    private Button btn_insert_exercise ,btn_insert_recipe, btn_insert_article;


    public InsertListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_insert_exercise = view.findViewById(R.id.button_insert_exercise);
        btn_insert_recipe = view.findViewById(R.id.button_insert_recipe);
        btn_insert_article = view.findViewById(R.id.button_insert_article);

        btn_insert_exercise.setOnClickListener(v -> {
            NavDirections direction = InsertListFragmentDirections.actionInsertListFragmentToInsertExerciseFragment();
            Navigation.findNavController(view).navigate(direction);
        });

        btn_insert_recipe.setOnClickListener(v -> {
            NavDirections direction = InsertListFragmentDirections.actionInsertListFragmentToInsertRecipeFragment();
            Navigation.findNavController(view).navigate(direction);
        });

        btn_insert_article.setOnClickListener(v -> {
            NavDirections direction = InsertListFragmentDirections.actionInsertListFragmentToInsertArticleFragment();
            Navigation.findNavController(view).navigate(direction);
        });


    }


}