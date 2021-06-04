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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MealListFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private RecAdaptorMeal recAdaptorMeal;
    private ArrayList<String> titles, calories, imgUrls;
    private String selected_language, title;
    SharedPreferences sharedPrefs;

    public MealListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titles = new ArrayList<>();
        calories = new ArrayList<>();
        imgUrls = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        fetchDataFromFB();
        RecyclerView recyclerView = view.findViewById(R.id.rec_view_meal);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recAdaptorMeal = new RecAdaptorMeal(titles,calories,imgUrls);
        recyclerView.setAdapter(recAdaptorMeal);
    }

    @SuppressWarnings("StringOperationCanBeSimplified")
    private void fetchDataFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Meals");
        collectionReference.orderBy("id", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {
            if(error != null) {
                Toast.makeText(getContext(), Objects.requireNonNull(error.getLocalizedMessage()).toString(), Toast.LENGTH_LONG).show();
            }

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {
                    Map<String,Object> data = snapshot.getData();

                    if(selected_language.matches("en")) {
                        title = (String) Objects.requireNonNull(data).get("title");
                    }else if(selected_language.matches("tr")){
                        title = (String) Objects.requireNonNull(data).get("title_tr");
                    }

                    String calorie = (String) Objects.requireNonNull(data).get("calorie");
                    String imgUrl = (String) data.get("imgUrl");

                    titles.add(title);
                    calories.add(calorie);
                    imgUrls.add(imgUrl);
                    recAdaptorMeal.notifyDataSetChanged();
                }
            }});
    }
}