package com.FitCom.fitcomapplication.ExerciseScreen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.FitCom.fitcomapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class ExerciseDetailFragment extends Fragment {

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    private TextView exerciseName, exerciseDescription;
    private String exerciseId;
    private String exerciseDirectory;
    private final String path = "Exercises/";

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

        exerciseName = view.findViewById(R.id.detail_name);
        exerciseDescription = view.findViewById(R.id.detail_description);

        int id = ExerciseDetailFragmentArgs.fromBundle(getArguments()).getExerciseId();
        exerciseId = String.valueOf(id);
        exerciseDirectory = path + exerciseId;
        Toast.makeText(view.getContext(), exerciseId, Toast.LENGTH_SHORT).show();

//        fillFromFB();

        fetchAndSetView(view);
    }

    //category = Fragment_tg_list_elementArgs.fromBundle(getArguments()).getRecordType()

//    public void fillFromFB(){
//        //this function will fill the listed rows
//        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
//        collectionReference.addSnapshotListener((value, error) -> {
//            if(error != null)
//                Toast.makeText(getContext(), error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
//
//            if(value != null){
//                for(DocumentSnapshot snapshot : value.getDocuments()) {
//                    Map<String,Object> data = snapshot.getData();
//                    String name = (String) data.get("name");
//                    String category = (String) data.get("category");
//                    String description = (String) data.get("description");
//                    String imgUrl = (String) data.get("imgUrl");
//
////                    //to be modified....
//                    exerciseName.setText(name);
//                    exerciseDescription.setText(description);
//                }
//            }
//
//        });
//    }

    private void fetchAndSetView(View view){
        DocumentReference docRef = firebaseFirestore.collection("Exercises").document(exerciseId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Toast.makeText(view.getContext(), document.getData().toString(), Toast.LENGTH_SHORT).show();
                    } else {

                    }
                } else {

                }
            }
        });

    }

}