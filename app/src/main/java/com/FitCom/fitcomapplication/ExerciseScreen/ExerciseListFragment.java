package com.FitCom.fitcomapplication.ExerciseScreen;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("StringOperationCanBeSimplified")
public class ExerciseListFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecAdaptorExercises adapter;
    private ArrayList<String> exercises;
    private ArrayList<String> categories;
    private String selected_language, category;
    SharedPreferences sharedPrefs;

    public ExerciseListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exercises = new ArrayList<>();
        categories = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_exercise_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecAdaptorExercises(exercises,categories);
        recyclerView.setAdapter(adapter);
        firebaseFirestore = FirebaseFirestore.getInstance();

        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        fetchDataFromFB();
    }

    public void fetchDataFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
        collectionReference.orderBy("id", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {
            if(error != null) {
                Toast.makeText(getContext(), Objects.requireNonNull(error.getLocalizedMessage()).toString(), Toast.LENGTH_LONG).show();
            }

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {
                    Map <String,Object> data = snapshot.getData();

                    String name = (String) Objects.requireNonNull(data).get("name");

                    if(selected_language.matches("en")){
                        category = (String) data.get("category");
                    }else if(selected_language.matches("tr")){
                        category = (String) data.get("category_tr");
                    }

                    categories.add(category);
                    exercises.add(name);
                    adapter.notifyDataSetChanged();
                }
            }});
    }
}