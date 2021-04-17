package com.FitCom.fitcomapplication.ExerciseScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.FitCom.fitcomapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ExerciseDetailFragment extends Fragment {

    private FirebaseStorage firebaseStorage;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    private TextView exerciseName, exerciseDescription;
    private ImageView imgExercise;

    private String exerciseId;
    private int id;

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

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        firebaseFirestore = FirebaseFirestore.getInstance();

        exerciseName = view.findViewById(R.id.detail_name);
        exerciseDescription = view.findViewById(R.id.detail_description);
        imgExercise = view.findViewById(R.id.exercise_image);

        id = ExerciseDetailFragmentArgs.fromBundle(getArguments()).getExerciseId();
        exerciseId = String.valueOf(id);

        fillFromFB(view);
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
                    String category = (String) data.get("category");
                    String description = (String) data.get("description");
                    String imgUrl = (String) data.get("imgUrl");

                    exerciseName.setText(name);
                    exerciseDescription.setText(description);

                    Picasso.get().load(imgUrl).into(imgExercise);
                }
            }
        });
    }
}