package com.FitCom.fitcomapplication.TrainerScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.FitCom.fitcomapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class InsertExerciseFragment extends Fragment {

    private ImageButton btn_backToList;
    private EditText et_title, et_desc, et_category;
    private Button btn_apply;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private HashMap<String, Object> postData;
    private String newId;
    private int counter_id;

    public InsertExerciseFragment() {
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
        return inflater.inflate(R.layout.fragment_insert_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getExerciseId();

        et_title = view.findViewById(R.id.editText_exercise_name);
        et_desc = view.findViewById(R.id.editText_exercise_desc);
        et_category = view.findViewById(R.id.editText_exercise_category);
        btn_apply = view.findViewById(R.id.button_send_exercise);
        btn_apply.setOnClickListener(v -> {
            sendArticle(v);
        });


        btn_backToList = view.findViewById(R.id.button_insertExer_backToList);
        btn_backToList.setOnClickListener(v -> {
            NavDirections direction = InsertExerciseFragmentDirections.actionInsertExerciseFragmentToInsertListFragment();
            Navigation.findNavController(view).navigate(direction);
        });
    }

    private void sendArticle(View view){
        postData = new HashMap<>();
        postData.put("description", et_desc.getText().toString());
        postData.put("id", newId);
        postData.put("name", et_title.getText().toString());
        postData.put("category", et_category.getText().toString());
        postData.put("imgUrl", "add URL`s");

        firebaseFirestore.collection("Exercises").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){

            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(view.getContext(), "Exercise add", Toast.LENGTH_SHORT).show();

                NavDirections directions = InsertExerciseFragmentDirections.actionInsertExerciseFragmentToInsertListFragment();
                Navigation.findNavController(view).navigate(directions);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(), "Exercise error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getExerciseId(){
        counter_id = 0;

        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if(value != null){
                    for (DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();
                        counter_id++;
                    }
                    newId = String.valueOf(counter_id);
                }
            }

        });
    }

}