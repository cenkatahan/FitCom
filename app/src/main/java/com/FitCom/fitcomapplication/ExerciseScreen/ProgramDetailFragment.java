package com.FitCom.fitcomapplication.ExerciseScreen;

import android.app.Activity;
import android.content.SharedPreferences;
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
import java.util.Objects;

@SuppressWarnings("ALL")
public class ProgramDetailFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private TextView programName, programDescription;
    private int id;
    private ProgressBar progressBar;
    private ImageView programImg;
    private String title, selected_language;
    SharedPreferences sharedPrefs;

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
        id = ProgramDetailFragmentArgs.fromBundle(requireArguments()).getProgramId();
        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        fillFromFB();
        ImageButton btnToList = view.findViewById(R.id.button_programs_backToList);
        btnToList.setOnClickListener(this::goProgramList);
    }

    public void fillFromFB(){
        CollectionReference collectionReference = firebaseFirestore.collection("Programs");
        collectionReference.whereEqualTo("id",id).addSnapshotListener((value, error) -> {
            if(error != null)
                Toast.makeText(getContext(), Objects.requireNonNull(error.getLocalizedMessage()),Toast.LENGTH_LONG).show();

            if(value != null){
                for(DocumentSnapshot snapshot : value.getDocuments()) {
                    Map<String,Object> data = snapshot.getData();
                    if(selected_language.matches("en")) {
                        title = (String) Objects.requireNonNull(data).get("title");
                    }else if(selected_language.matches("tr")){
                        title = (String) Objects.requireNonNull(data).get("title_tr");
                    }

                    String trimmed = (String) Objects.requireNonNull(data).get("desc");
                    String description = Objects.requireNonNull(trimmed).replace(",","\n");

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