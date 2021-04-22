package com.FitCom.fitcomapplication.BlogScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class ArticleDetailFragment extends Fragment {

    //private FirebaseStorage firebaseStorage;
    //private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    private TextView articleTitle, articleDescription;

    private int id;
    private String articleId;

    public ArticleDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();

        articleTitle = view.findViewById(R.id.article_title);
        articleDescription = view.findViewById(R.id.article_description);

        id = ArticleDetailFragmentArgs.fromBundle(getArguments()).getArticleId();
        articleId = String.valueOf(id);

        fetchFromFB();
    }


    private void fetchFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Article");
        collectionReference.whereEqualTo("id", articleId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null)
                    Toast.makeText(getContext(), error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

                if(value != null){

                    for(DocumentSnapshot snapshot: value.getDocuments()){

                        Map<String, Object> data = snapshot.getData();

                        String title = (String) data.get("title");
                        String description = (String) data.get("description");

                        articleTitle.setText(title);
                        articleDescription.setText(description);
                    }


                }
            }
        });
    }
}