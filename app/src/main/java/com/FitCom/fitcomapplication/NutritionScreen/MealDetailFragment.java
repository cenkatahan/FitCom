package com.FitCom.fitcomapplication.NutritionScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class MealDetailFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private TextView mealTitle, mealDesc;
    private ImageButton btnBack;
    private int mealId;
    private String str_mealId;

    public MealDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mealId = MealDetailFragmentArgs.fromBundle(getArguments()).getMealId();
        str_mealId = String.valueOf(mealId);

        mealTitle = view.findViewById(R.id.meal_detail_title);
        mealDesc = view.findViewById(R.id.meal_detail_desc);
        btnBack = view.findViewById(R.id.button_meal_backToList);
        btnBack.setOnClickListener(v -> goMealList(v));

        fetchFromFB();
    }

    private void fetchFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Meals");
        collectionReference.whereEqualTo("id",str_mealId).addSnapshotListener((value, error) -> {
            if(error != null)
                Toast.makeText(getContext(), error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {

                    Map<String,Object> data = snapshot.getData();
                    String name = (String) data.get("title");
                    String description = (String) data.get("desc");
                    mealTitle.setText(name);
                    mealDesc.setText(description);
                }
            }
        });
    }

    private void goMealList(View view){
        NavDirections actionToList = MealDetailFragmentDirections.actionMealDetailFragmentToMealListFragment();
        Navigation.findNavController(view).navigate(actionToList);

    }
}