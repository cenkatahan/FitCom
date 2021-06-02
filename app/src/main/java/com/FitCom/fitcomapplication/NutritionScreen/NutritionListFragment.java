package com.FitCom.fitcomapplication.NutritionScreen;

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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Map;

public class NutritionListFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private ArrayList<String> names, calories, carbs, proteins, fats;
    private RecAdaptorNutrition recAdaptorNutrition;
    private RecyclerView recyclerView;
    private String selected_language, name;
    SharedPreferences sharedPrefs;

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
        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        fetchDataFromFB();
    }

    private void fetchDataFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Nutrition");
        collectionReference.orderBy("id", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                if(value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();

                        if(selected_language.matches("en")) {
                            name = (String) data.get("name");
                        }else if(selected_language.matches("tr")){
                            name = (String) data.get("name_tr");
                        }


                        String calorie = (String) data.get("Calorie");
                        String carb = getString(R.string.str_carb) + " " + data.get("Carb");
                        String protein = getString(R.string.str_protein) + " "  + data.get("Protein");
                        String fat = getString(R.string.str_fat) + " "  + data.get("Fat");
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