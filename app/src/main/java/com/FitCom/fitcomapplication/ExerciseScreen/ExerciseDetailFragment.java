package com.FitCom.fitcomapplication.ExerciseScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ExerciseDetailFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private TextView exerciseName, exerciseDescription;
    private ImageView imgExercise;
    private String exerciseId;
    private int id;

    private ImageButton btnBackToList;

    public ExerciseDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        exerciseName = view.findViewById(R.id.detail_name);
        exerciseDescription = view.findViewById(R.id.detail_description);
        imgExercise = view.findViewById(R.id.exercise_image);
        id = ExerciseDetailFragmentArgs.fromBundle(getArguments()).getExerciseId();
        exerciseId = String.valueOf(id);
        fillFromFB(view);

        btnBackToList = view.findViewById(R.id.button_exercise_backToList);
        btnBackToList.setOnClickListener(v -> goExerciseList(v));

    }

    public void fillFromFB(View view){
        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
        collectionReference.whereEqualTo("id",exerciseId).addSnapshotListener((value, error) -> {
            if(error != null)
                Toast.makeText(getContext(), error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {

                    Map<String,Object> data = snapshot.getData();
                    String name = (String) data.get("name");
                    String description = (String) data.get("description");
                    String imgUrl = (String) data.get("imgUrl");
                    exerciseName.setText(name);
                    exerciseDescription.setText(description);
                    Picasso.get().load(imgUrl).into(imgExercise);
                }
            }
        });
    }

    private void goExerciseList(View view){
        NavDirections actionToSignIn = ExerciseDetailFragmentDirections.actionExerciseDetailFragmentToExerciseListFragment();
        Navigation.findNavController(view).navigate(actionToSignIn);
    }

}