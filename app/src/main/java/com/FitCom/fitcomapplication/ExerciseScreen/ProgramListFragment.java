package com.FitCom.fitcomapplication.ExerciseScreen;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ALL")
public class ProgramListFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecAdaptorPrograms recAdaptorPrograms;
    private ArrayList<String> titles, imgs;
    private String title, selected_language;
    SharedPreferences sharedPrefs;

    public ProgramListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_program_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        titles = new ArrayList<>();
        imgs = new ArrayList<>();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_program_list);
        recAdaptorPrograms = new RecAdaptorPrograms(titles, imgs);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(recAdaptorPrograms);
        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        fetchDataFromFB();
    }

    private void fetchDataFromFB(){

        CollectionReference collectionReference = firebaseFirestore.collection("Programs");
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

                    String url = (String) Objects.requireNonNull(data).get("img_src");
                    titles.add(title);
                    imgs.add(url);

                    recAdaptorPrograms.notifyDataSetChanged();
                }
            }
        });
    }
}