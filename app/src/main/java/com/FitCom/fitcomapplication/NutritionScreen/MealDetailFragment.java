package com.FitCom.fitcomapplication.NutritionScreen;

import android.app.Activity;
import android.content.SharedPreferences;
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
    private TextView mealTitle, mealDesc, et_prep_time;
    private ImageButton btnBack;
    private int mealId;
    private String selected_language, title, description, prep_time;
    SharedPreferences sharedPrefs;

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
        mealTitle = view.findViewById(R.id.meal_detail_title);
        et_prep_time = view.findViewById(R.id.prep_time);
        mealDesc = view.findViewById(R.id.meal_detail_desc);
        btnBack = view.findViewById(R.id.button_meal_backToList);
        btnBack.setOnClickListener(v -> goMealList(v));
        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        fetchFromFB();
    }

    private void fetchFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Meals");
        collectionReference.whereEqualTo("id",mealId).addSnapshotListener((value, error) -> {
            if(error != null)
                Toast.makeText(getContext(), error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {

                    Map<String,Object> data = snapshot.getData();
                    prep_time = (String) data.get("prep_time");

                    if(selected_language.matches("en")) {
                        title = (String) data.get("title");
                        description = (String) data.get("desc");
                        et_prep_time.setText(prep_time + getString(R.string.how_many_en));
                    }else if(selected_language.matches("tr")){
                        title = (String) data.get("title_tr");
                        description = (String) data.get("desc_tr");
                        et_prep_time.setText(prep_time + getString(R.string.how_many_tr));
                    }

                    mealTitle.setText(title);
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