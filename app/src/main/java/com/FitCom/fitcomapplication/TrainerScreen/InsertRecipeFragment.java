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


public class InsertRecipeFragment extends Fragment {

    private ImageButton btn_backToList;
    private EditText et_title, et_desc;
    private Button btn_apply;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private HashMap<String, Object> postData;
    private String newId;
    private int counter_id;

    public InsertRecipeFragment() {
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
        return inflater.inflate(R.layout.fragment_insert_recipe, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getRecipeId();

        et_title = view.findViewById(R.id.editText_recipe_title);
        et_desc = view.findViewById(R.id.editText_recipe_desc);
        btn_apply = view.findViewById(R.id.button_send_recipe);
        btn_apply.setOnClickListener(v -> {
            sendRecipe(v);
        });


        btn_backToList = view.findViewById(R.id.button_insertRecipe_backToList);
        btn_backToList.setOnClickListener(v -> {
            goBackToList(v);
        });
    }

    private void sendRecipe(View view){
        postData = new HashMap<>();
        postData.put("desc", et_desc.getText().toString());
        postData.put("id", newId);
        postData.put("title", et_title.getText().toString());

        firebaseFirestore.collection("Meals").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){

            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(view.getContext(), "Recipe add", Toast.LENGTH_SHORT).show();

                NavDirections directions = InsertArticleFragmentDirections.actionInsertArticleFragmentToInsertListFragment();
                Navigation.findNavController(view).navigate(directions);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(), "Recipe error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getRecipeId() {
        counter_id = 0;

        CollectionReference collectionReference = firebaseFirestore.collection("Meals");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        Map<String, Object> data = snapshot.getData();
                        counter_id++;
                    }
                    newId = String.valueOf(counter_id);
                }
            }

        });
    }

    private void goBackToList(View view){
        NavDirections direction = InsertRecipeFragmentDirections.actionInsertRecipeFragmentToInsertListFragment();
        Navigation.findNavController(view).navigate(direction);
    }
}