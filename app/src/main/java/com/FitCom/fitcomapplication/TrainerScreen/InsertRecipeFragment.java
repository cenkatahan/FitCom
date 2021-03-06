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
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("ALL")
public class InsertRecipeFragment extends Fragment {

    private EditText et_title_tr, et_desc_tr;
    private String selected_language;
    SharedPreferences sharedPrefs;
    private ImageView imageViewRecipe;
    private EditText et_title, et_desc, et_calorie, prep_time;
    private FirebaseFirestore firebaseFirestore;
    private int counter_id;
    private StorageReference storageReference;
    private Bitmap imgChosen;
    private static final String NAME = "saveBitmap";
    SaveBitmap saveBitmap;
    Uri imgData;

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


        sharedPrefs = view.getContext().getSharedPreferences("preferences", Activity.MODE_PRIVATE);
        selected_language = sharedPrefs.getString("selected_lang" ,"");
        et_title_tr = view.findViewById(R.id.editText_recipe_title_tr);
        et_desc_tr = view.findViewById(R.id.editText_recipe_desc_tr);
        Button btn_translate = view.findViewById(R.id.button_translate_3);
        btn_translate.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url2)));
            startActivity(browserIntent);
            Toast.makeText(getContext(),getString(R.string.supported_langs), Toast.LENGTH_LONG).show();
        });
        prep_time = view.findViewById(R.id.editText_recipe_prep_time);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        getRecipeId();
        Button select_image = view.findViewById(R.id.button_select_img_rec);
        select_image.setOnClickListener(this::choose_image);
        imageViewRecipe = view.findViewById(R.id.imageViewRecipe);
        et_title = view.findViewById(R.id.editText_recipe_title);
        et_desc = view.findViewById(R.id.editText_recipe_desc);
        et_calorie = view.findViewById(R.id.editText_recipe_calorie);
        Button btn_apply = view.findViewById(R.id.button_send_recipe);
        btn_apply.setOnClickListener(this::uploadToFB);
        ImageButton btn_backToList = view.findViewById(R.id.button_insertRecipe_backToList);
        btn_backToList.setOnClickListener(this::goBackToList);
        Bitmap defaultImg = BitmapFactory.decodeResource(Objects.requireNonNull(getContext()).getResources(), R.drawable.upload_icon_2);
        imageViewRecipe.setImageBitmap(defaultImg);
        startSavedBitmapFragment();
        loadBitMap();
    }

    public void uploadToFB(View view){

        if(et_title.getText().toString().isEmpty() || et_desc.getText().toString().isEmpty()
            || et_calorie.getText().toString().isEmpty() || et_title_tr.getText().toString().isEmpty()
                || et_desc_tr.getText().toString().isEmpty() || prep_time.getText().toString().isEmpty()){
            Toast.makeText(view.getContext(), getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
        }else{
            if(imgData == null){
                Toast.makeText(view.getContext(), getString(R.string.error_img), Toast.LENGTH_SHORT).show();
            }else{
                UUID uuid = UUID.randomUUID();
                final String imageName = "images/nutrition-images" + uuid + ".jpeg";
                storageReference.child(imageName).putFile(imgData).addOnSuccessListener(taskSnapshot -> {
                    StorageReference storageReference1 = FirebaseStorage.getInstance().getReference(imageName);
                    storageReference1.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        HashMap<String, Object> postData = new HashMap<>();

                        postData.put("id", counter_id);
                        postData.put("calorie", et_calorie.getText().toString());
                        postData.put("imgUrl", downloadUrl);
                        postData.put("prep_time", prep_time.getText().toString());

                        if (selected_language.matches("en")) {
                            postData.put("desc", et_desc.getText().toString());
                            postData.put("title", et_title.getText().toString());
                            postData.put("desc_tr", et_desc_tr.getText().toString());
                            postData.put("title_tr", et_title_tr.getText().toString());
                        } else if (selected_language.matches("tr")) {
                            postData.put("desc_tr", et_desc.getText().toString());
                            postData.put("title_tr", et_title.getText().toString());
                            postData.put("desc", et_desc_tr.getText().toString());
                            postData.put("title", et_title_tr.getText().toString());
                        }

                        firebaseFirestore.collection("Meals").add(postData).addOnSuccessListener(documentReference -> {
                            Toast.makeText(view.getContext(), getString(R.string.str_successful), Toast.LENGTH_SHORT).show();
                            NavDirections directions = InsertRecipeFragmentDirections.actionInsertRecipeFragmentToInsertListFragment();
                            Navigation.findNavController(view).navigate(directions);
                        }).addOnFailureListener(e -> Toast.makeText(view.getContext(), getString(R.string.error_error), Toast.LENGTH_SHORT).show());
                    });
                }).addOnFailureListener(e -> Toast.makeText(view.getContext(), getString(R.string.error_error),Toast.LENGTH_SHORT).show());
            }
        }
    }

    public void startSavedBitmapFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        this.saveBitmap = (SaveBitmap) fragmentManager.findFragmentByTag(NAME);
        if(this.saveBitmap == null){
            this.saveBitmap = new SaveBitmap();
            fragmentManager.beginTransaction().add(this.saveBitmap,NAME).commit();
        }
    }

    public void loadBitMap(){
        if(this.saveBitmap == null)
            return;

        imgChosen = this.saveBitmap.getBitmap();
        if(imgChosen == null)
            return;

        imageViewRecipe.setImageBitmap(imgChosen);
    }

    public void choose_image(View view){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            Intent openMediaStore = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openMediaStore,124);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 123){
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Intent openMediaStore = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openMediaStore,124);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(!(data == null) && resultCode == Activity.RESULT_OK && (requestCode == 124)){
            imgData = data.getData();

            try{
                if(Build.VERSION.SDK_INT < 28){
                    imgChosen = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(),imgData);
                }else {
                    ImageDecoder.Source source = ImageDecoder.createSource(requireActivity().getContentResolver(), imgData);
                    imgChosen = ImageDecoder.decodeBitmap(source);
                }
                this.saveBitmap.setBitmap(imgChosen);
                imageViewRecipe.setImageBitmap(imgChosen);
            } catch (IOException exception){
                exception.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getRecipeId() {
        counter_id = 0;

        CollectionReference collectionReference = firebaseFirestore.collection("Meals");
        collectionReference.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(getContext(), Objects.requireNonNull(error.getLocalizedMessage()).toString(), Toast.LENGTH_LONG).show();
            }

            if (value != null) {
                for (DocumentSnapshot snapshot : value.getDocuments()) {
                    Map<String, Object> data = snapshot.getData();
                    counter_id++;
                }
            }
        });
    }

    private void goBackToList(View view){
        NavDirections direction = InsertRecipeFragmentDirections.actionInsertRecipeFragmentToInsertListFragment();
        Navigation.findNavController(view).navigate(direction);
    }
}