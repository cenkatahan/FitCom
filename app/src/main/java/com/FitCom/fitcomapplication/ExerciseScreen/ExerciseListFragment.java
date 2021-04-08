package com.FitCom.fitcomapplication.ExerciseScreen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executor;


public class ExerciseListFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecAdaptorExercises adapter;
    private ArrayList<String> exercises;
    private ArrayList<String> categories;
    DocumentReference docRef;

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
        exercises.add("osman");
        categories.add("cinar");

        RecyclerView recyclerView = view.findViewById(R.id.recycler_exercise_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecAdaptorExercises(exercises,categories);
        recyclerView.setAdapter(adapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        //docRef = firebaseFirestore.document("Exercises");
        //fillFromFB();
        //f();
        //omg(view);
    }

    /*
    @Override
    public void onStart() {
        super.onStart();
        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if(value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()) {

                        Map <String,Object> data = snapshot.getData();

                        String name = (String) data.get("name");
                        String category = (String) data.get("category");

                        categories.add(category);
                        exercises.add(name);

                        adapter.notifyDataSetChanged();

                        //System.out.println(name);
                        //System.out.println(category);
                    }
                }}
        });
    }
*/

    public void omg(View view){
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String name = documentSnapshot.getString("name");
                    String category = documentSnapshot.getString("category");

                    //Map <String,Object> data = documentSnapshot.getData();

                    categories.add(category);
                    exercises.add(name);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "problem", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void f(){
        Query query = firebaseFirestore.collection("Exercises");
        ListenerRegistration registration = query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if(value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()) {
                        Map <String,Object> data = snapshot.getData();

                        String name = (String) data.get("name");
                        String category = (String) data.get("category");

                        categories.add(category);
                        exercises.add(name);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        registration.remove();
    }

    public void fillFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if(value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()) {

                        Map <String,Object> data = snapshot.getData();

                        String name = (String) data.get("name");
                        String category = (String) data.get("category");

                        categories.add(category);
                        exercises.add(name);

                        adapter.notifyDataSetChanged();

                        //System.out.println(name);
                        //System.out.println(category);
                    }
                }}
        });
    }
}