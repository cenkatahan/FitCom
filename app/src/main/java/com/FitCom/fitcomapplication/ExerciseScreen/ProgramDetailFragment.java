package com.FitCom.fitcomapplication.ExerciseScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ProgramDetailFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private TextView programName, programDescription;
    private String programId;
    private int id;
    private ProgressBar progressBar;
    private ImageButton btnToList;
    private ImageView programImg;

    public ProgramDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_program_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseFirestore = FirebaseFirestore.getInstance();
        programName = view.findViewById(R.id.fragment_popup_title);
        programDescription = view.findViewById(R.id.fragment_popup_description);
        programImg = view.findViewById(R.id.fragment_popup_img);
        progressBar = view.findViewById(R.id.fragment_popup_progressbar);
        id = ProgramDetailFragmentArgs.fromBundle(getArguments()).getProgramId();
        programId = String.valueOf(id);

        fillFromFB(view);

        btnToList = view.findViewById(R.id.button_programs_backToList);
        btnToList.setOnClickListener(v -> goProgramList(v));
    }

    public void fillFromFB(View view){
        CollectionReference collectionReference = firebaseFirestore.collection("Programs");
        collectionReference.whereEqualTo("id",programId).addSnapshotListener((value, error) -> {
            if(error != null)
                Toast.makeText(getContext(), error.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {
                    Map<String,Object> data = snapshot.getData();
                    String title = (String) data.get("title");
                    String description = (String) data.get("desc");
                    String url = (String) data.get("img_src");
                    programName.setText(title);
                    programDescription.setText(description);

                    Picasso.get().load(url).into(programImg, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
                }
            }
        });
    }




    private void goProgramList(View view){
        NavDirections actionToSignIn = ProgramDetailFragmentDirections.actionProgramDetailFragmentToProgramListFragment();
        Navigation.findNavController(view).navigate(actionToSignIn);
    }
}