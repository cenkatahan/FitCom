package com.FitCom.fitcomapplication.ExerciseScreen;

import android.app.Activity;
import android.content.SharedPreferences;
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
import java.util.Objects;

@SuppressWarnings("ALL")
public class ExerciseDetailFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private TextView exerciseName, exerciseDescription;
    private ImageView imgExercise;
    private int id;
    private String selected_language, description;
    SharedPreferences sharedPrefs;

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
        id = ExerciseDetailFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getExerciseId();
        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        fillFromFB(view);
        ImageButton btnBackToList = view.findViewById(R.id.button_exercise_backToList);
        btnBackToList.setOnClickListener(this::goExerciseList);
    }

    public void fillFromFB(View view){
        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
        collectionReference.whereEqualTo("id",id).addSnapshotListener((value, error) -> {
            if(error != null)
                Toast.makeText(getContext(), Objects.requireNonNull(error.getLocalizedMessage()).toString(),Toast.LENGTH_LONG).show();

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {

                    Map<String,Object> data = snapshot.getData();

                    if(selected_language.matches("en")) {
                        description = (String) Objects.requireNonNull(data).get("description");
                    }else if(selected_language.matches("tr")){
                        description = (String) Objects.requireNonNull(data).get("description_tr");
                    }

                    String name = (String) Objects.requireNonNull(data).get("name");
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