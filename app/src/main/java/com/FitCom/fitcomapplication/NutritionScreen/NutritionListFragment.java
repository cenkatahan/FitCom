package com.FitCom.fitcomapplication.NutritionScreen;

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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Map;

public class NutritionListFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private ArrayList<String> names, calories, carbs, proteins, fats;
    private RecAdaptorNutrition recAdaptorNutrition;
    private RecyclerView recyclerView;

    public NutritionListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutrition_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        names = new ArrayList<>();
        calories = new ArrayList<>();
        carbs = new ArrayList<>();
        proteins = new ArrayList<>();
        fats = new ArrayList<>();

        recAdaptorNutrition = new RecAdaptorNutrition(names, calories, carbs, proteins, fats);
        recyclerView = view.findViewById(R.id.recycler_nutrition_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recAdaptorNutrition);

        firebaseFirestore = FirebaseFirestore.getInstance();
        fetchDataFromFB();
    }

    private void fetchDataFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Nutrition");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                if(value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()) {

                        Map<String, Object> data = snapshot.getData();

                        String name = (String) data.get("name");
                        String calorie = (String) data.get("Calorie");
                        String carb = "Carbs: " + data.get("Carb");
                        String protein = "Protein: " + data.get("Protein");
                        String fat = "Fat: " + data.get("Fat");
                        names.add(name);
                        calories.add(calorie);
                        carbs.add(carb);
                        proteins.add(protein);
                        fats.add(fat);

                        recAdaptorNutrition.notifyDataSetChanged();
                    }
                }
            }
        });
    }
}