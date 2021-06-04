package com.FitCom.fitcomapplication.TraineeScreen;

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

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ALL")
public class TrainersListFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecAdaptorTrainers recAdaptorTrainers;
    private ArrayList<String> names, emails;

    public TrainersListFragment() {
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
        return inflater.inflate(R.layout.fragment_trainers_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        names = new ArrayList<>();
        emails = new ArrayList<>();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_trainers_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recAdaptorTrainers = new RecAdaptorTrainers(names, emails);
        recyclerView.setAdapter(recAdaptorTrainers);
        fetchFromFB();
    }

    private void fetchFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Users");
        String TRAINER_KEY = "1";
        collectionReference.whereEqualTo("trainer", TRAINER_KEY).addSnapshotListener((value, error) -> {
            if(error != null) {
                Toast.makeText(getContext(), Objects.requireNonNull(error.getLocalizedMessage()).toString(), Toast.LENGTH_LONG).show();
            }

            if(value != null){
                for (DocumentSnapshot snapshot: value.getDocuments()){
                    Map<String, Object> data = snapshot.getData();

                    String email = (String) Objects.requireNonNull(data).get("email");
                    String fullName = (String) data.get("fullName");
                    emails.add(email);
                    names.add(fullName);
                    recAdaptorTrainers.notifyDataSetChanged();
                }
            }
        });
    }
}