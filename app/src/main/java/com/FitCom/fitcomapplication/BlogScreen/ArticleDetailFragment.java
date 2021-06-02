package com.FitCom.fitcomapplication.BlogScreen;

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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.Map;

public class ArticleDetailFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private TextView articleTitle, articleDescription;
    private int id;
    private ImageButton btnToList;
    private String selected_language, title, description;
    SharedPreferences sharedPrefs;

    public ArticleDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        articleTitle = view.findViewById(R.id.article_title);
        articleDescription = view.findViewById(R.id.article_description);
        id = ArticleDetailFragmentArgs.fromBundle(getArguments()).getArticleId();
        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        fetchFromFB();
        btnToList = view.findViewById(R.id.button_article_backToList);
        btnToList.setOnClickListener(v -> {
            goArticleList(v);
        });
    }

    private void fetchFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Article");
        collectionReference.whereEqualTo("id", id).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

                if(value != null){
                    for(DocumentSnapshot snapshot: value.getDocuments()){

                        Map<String, Object> data = snapshot.getData();

                        if(selected_language.matches("en")) {
                            title = (String) data.get("title");
                            description = (String) data.get("description");
                        }else if(selected_language.matches("tr")){
                            title = (String) data.get("title_tr");
                            description = (String) data.get("description_tr");
                        }

                        articleTitle.setText(title);
                        articleDescription.setText(description);
                    }
                }
            }
        });
    }

    private void goArticleList(View view){
        NavDirections actionToSignIn = ArticleDetailFragmentDirections.actionArticleDetailFragmentToArticleListFragment2();
        Navigation.findNavController(view).navigate(actionToSignIn);
    }
}