package com.FitCom.fitcomapplication.TrainerScreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

@SuppressWarnings({"ALL", "FieldCanBeLocal"})
public class InsertArticleFragment extends Fragment {

    //osman
    private EditText et_title_tr, et_desc_tr;
    @SuppressWarnings("FieldCanBeLocal")
    private Button btn_translate;
    private String selected_language;
    SharedPreferences sharedPrefs;

    private ImageButton btn_backToList;
    private EditText et_title, et_desc;
    private Button btn_apply;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private HashMap<String, Object> postData;
    private int counter_id;

    public InsertArticleFragment() {
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

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        return inflater.inflate(R.layout.fragment_insert_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //osman
        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        et_title_tr = view.findViewById(R.id.editText_article_title_tr);
        et_desc_tr = view.findViewById(R.id.editText_article_desc_tr);
        btn_translate = view.findViewById(R.id.button_translate_2);
        btn_translate.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url2)));
            startActivity(browserIntent);
            Toast.makeText(getContext(),getString(R.string.supported_langs), Toast.LENGTH_LONG).show();
        });

        getArticleId();
        et_title = view.findViewById(R.id.editText_article_title);
        et_desc = view.findViewById(R.id.editText_article_desc);
        btn_apply = view.findViewById(R.id.button_send_article);
        btn_apply.setOnClickListener(v -> {
            sendArticle(v);
        });

        btn_backToList = view.findViewById(R.id.button_insertArticle_backToList);
        btn_backToList.setOnClickListener(v -> {
            goBackToList(v);
        });
    }

    private void sendArticle(View view){
        if (et_title.getText().toString().isEmpty() || et_desc.getText().toString().isEmpty()
            || et_title_tr.getText().toString().isEmpty() || et_desc_tr.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), getString(R.string.error_fields), Toast.LENGTH_LONG).show();
        }else{
            postData = new HashMap<>();
            postData.put("id", counter_id);

            if(selected_language.matches("en")){
                postData.put("description", et_desc.getText().toString());
                postData.put("title", et_title.getText().toString());
                postData.put("description_tr", et_desc_tr.getText().toString());
                postData.put("title_tr", et_title_tr.getText().toString());
            }else if(selected_language.matches("tr")){
                postData.put("description_tr", et_desc.getText().toString());
                postData.put("title_tr", et_title.getText().toString());
                postData.put("description", et_desc_tr.getText().toString());
                postData.put("title", et_title_tr.getText().toString());
            }

            firebaseFirestore.collection("Article").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){

                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(view.getContext(), getString(R.string.str_successful), Toast.LENGTH_SHORT).show();

                    NavDirections directions = InsertArticleFragmentDirections.actionInsertArticleFragmentToInsertListFragment();
                    Navigation.findNavController(view).navigate(directions);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(view.getContext(), getString(R.string.error_error), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getArticleId(){
        counter_id = 0;

        CollectionReference collectionReference = firebaseFirestore.collection("Article");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null) {
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if(value != null){
                    for (DocumentSnapshot snapshot: value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();
                        counter_id++;
                    }
                }
            }
        });
    }

    private void goBackToList(View view){
        NavDirections directions = InsertArticleFragmentDirections.actionInsertArticleFragmentToInsertListFragment();
        Navigation.findNavController(view).navigate(directions);
    }
}