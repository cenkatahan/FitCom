package com.FitCom.fitcomapplication.TrainerScreen;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("ALL")
public class InsertExerciseFragment extends Fragment {

    private EditText et_category_tr, et_desc_tr;
    private String selected_language;
    SharedPreferences sharedPrefs;
    private EditText et_title, et_desc, et_category;
    private FirebaseFirestore firebaseFirestore;
    private int counter_id;
    private ImageView destImg;
    private StorageReference storageReference;
    private Bitmap imgChosen;
    SaveBitmap saveBitmap;
    Uri imageData;
    private static final String NAME = "saveBitmap";

    public InsertExerciseFragment() {
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
        return inflater.inflate(R.layout.fragment_insert_exercise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        et_category_tr = view.findViewById(R.id.editText_exercise_category_tr);
        et_desc_tr = view.findViewById(R.id.editText_exercise_desc_tr);
        Button btn_translate = view.findViewById(R.id.button_translate);
        btn_translate.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url2)));
            startActivity(browserIntent);
            Toast.makeText(getContext(),getString(R.string.supported_langs), Toast.LENGTH_LONG).show();
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        destImg = view.findViewById(R.id.imageViewExercise);
        Button select_img = view.findViewById(R.id.button_select_img);
        getExerciseId();
        et_title = view.findViewById(R.id.editText_exercise_name);
        et_desc = view.findViewById(R.id.editText_exercise_desc);
        et_category = view.findViewById(R.id.editText_exercise_category);
        Button btn_apply = view.findViewById(R.id.button_send_exercise);
        btn_apply.setOnClickListener(this::upload);

        select_img.setOnClickListener(v -> {
            chooseFromGallery();
        });

        ImageButton btn_backToList = view.findViewById(R.id.button_insertExer_backToList);
        btn_backToList.setOnClickListener(v -> {
            NavDirections direction = InsertExerciseFragmentDirections.actionInsertExerciseFragmentToInsertListFragment();
            Navigation.findNavController(view).navigate(direction);
        });

        Bitmap defaultImg = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.upload_icon_2);
        destImg.setImageBitmap(defaultImg);
        startSavedBitmapFragment();
        loadBitmap();
    }

    public void upload(View view) {

        if(et_title.getText().toString().isEmpty() || et_desc.getText().toString().isEmpty()
                || et_category.getText().toString().isEmpty() || et_desc_tr.getText().toString().isEmpty()
                || et_category_tr.getText().toString().isEmpty()){
            Toast.makeText(view.getContext(), getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
        }else{
            //universal unique id
            UUID uuid = UUID.randomUUID();
            final String imageName = "images/exercise-images" + uuid + ".jpg";

            if (imageData == null) {
                Toast.makeText(view.getContext(), getString(R.string.error_img), Toast.LENGTH_SHORT).show();
            } else {
                storageReference.child(imageName).putFile(imageData).addOnSuccessListener(taskSnapshot -> {
                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        String downloadUrl = uri.toString();
                        HashMap<String, Object> postData = new HashMap<>();
                        postData.put("imgUrl", downloadUrl);
                        postData.put("id", counter_id);
                        postData.put("name", et_title.getText().toString());

                        if (selected_language.matches("en")) {
                            postData.put("category", et_category.getText().toString());
                            postData.put("description", et_desc.getText().toString());
                            postData.put("description_tr", et_desc_tr.getText().toString());
                            postData.put("category_tr", et_category_tr.getText().toString());
                        } else if (selected_language.matches("tr")) {
                            postData.put("description_tr", et_desc.getText().toString());
                            postData.put("category_tr", et_category.getText().toString());
                            postData.put("category", et_category_tr.getText().toString());
                            postData.put("description", et_desc_tr.getText().toString());
                        }

                        firebaseFirestore.collection("Exercises").add(postData).addOnSuccessListener(documentReference -> {
                            Toast.makeText(view.getContext(), getString(R.string.str_successful), Toast.LENGTH_SHORT).show();

                            NavDirections directions = InsertExerciseFragmentDirections.actionInsertExerciseFragmentToInsertListFragment();
                            Navigation.findNavController(view).navigate(directions);
                        }).addOnFailureListener(e -> Toast.makeText(view.getContext(), getString(R.string.error_error), Toast.LENGTH_SHORT).show());
                    });
                }).addOnFailureListener(e -> Toast.makeText(view.getContext(), getString(R.string.error_error), Toast.LENGTH_SHORT).show());
            }
        }
    }

    private void getExerciseId(){
        counter_id = 0;

        CollectionReference collectionReference = firebaseFirestore.collection("Exercises");
        collectionReference.addSnapshotListener((value, error) -> {
            if(error != null) {
                Toast.makeText(getContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
            }

            if(value != null){
                for (DocumentSnapshot snapshot: value.getDocuments()){
                    Map<String, Object> data = snapshot.getData();
                    counter_id++;
                }
            }
        });
    }

    public void chooseFromGallery(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Intent toMediaStore = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(toMediaStore,124);
        }
        else
            ActivityCompat.requestPermissions(requireActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 123){
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Intent toMediaStore = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(toMediaStore,124);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!(data ==null) && resultCode == Activity.RESULT_OK && (requestCode == 124)){
            imageData = data.getData();

            try {
                if (Build.VERSION.SDK_INT < 28)
                    //noinspection deprecation
                    imgChosen = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageData);
                else {
                    //  when user tries to save a record without selecting a picture, I used to have error caused empty uri(address of data in device).
                    ImageDecoder.Source source = ImageDecoder.createSource(requireActivity().getContentResolver(), imageData);
                    imgChosen = ImageDecoder.decodeBitmap(source);
                }
                this.saveBitmap.setBitmap(imgChosen);
                destImg.setImageBitmap(imgChosen);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    public void startSavedBitmapFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        this.saveBitmap = (SaveBitmap) fragmentManager.findFragmentByTag(NAME);
        if(this.saveBitmap == null){
            this.saveBitmap = new SaveBitmap();
            fragmentManager.beginTransaction().add(this.saveBitmap,NAME).commit();
        }
    }

    public void loadBitmap(){
        if (this.saveBitmap == null)
            return;

        imgChosen = this.saveBitmap.getBitmap();
        if (imgChosen == null)
            return;

        destImg.setImageBitmap(imgChosen);
    }
}