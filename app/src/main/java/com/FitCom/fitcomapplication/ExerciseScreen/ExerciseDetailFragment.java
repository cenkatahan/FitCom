package com.FitCom.fitcomapplication.ExerciseScreen;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
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
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

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
    }

    public void fillFromFB(){
        //this function will fill the listed rows
        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
        collectionReference.orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

                if(value != null){
                    for(DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String,Object> data = snapshot.getData();
                        String name = (String) data.get("name");
                        String category = (String) data.get("category");
                        String description = (String) data.get("description");
                        String imgUrl = (String) data.get("imgUrl");

                        //to be modified....
                    }
                }
            }
        });
    }

}