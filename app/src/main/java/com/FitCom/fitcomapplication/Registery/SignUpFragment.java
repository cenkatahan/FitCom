package com.FitCom.fitcomapplication.Registery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.FitCom.fitcomapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import java.util.HashMap;

public class SignUpFragment extends Fragment {
    private EditText eMailField, passwordField, passwordField2, age, fullName;
    private Button buttonSignUp, button_nav_to_sign_in;
    private CheckBox terms,trainer;
    private User user;
    private HashMap<String, Object> postData;
    private String eMail, password,password2, theAge, name;
    private boolean termsAndConditions,trainercheckbox;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public SignUpFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseFirestore = FirebaseFirestore.getInstance();

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        eMailField = view.findViewById(R.id.editTextSignUpEmail);
        passwordField = view.findViewById(R.id.editTextSignUpPassword);
        passwordField2 = view.findViewById(R.id.editTextSignUpPassword2);
        age = view.findViewById(R.id.editTextAge);
        terms = view.findViewById(R.id.termsCheckBox);
        fullName = view.findViewById(R.id.editTextFullName);
        trainer=view.findViewById(R.id.checkBoxAccountType);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);
        button_nav_to_sign_in = view.findViewById(R.id.button_nav_to_sign_in);
        buttonSignUp.setOnClickListener(v -> {
            SignUp(view);
        });
        button_nav_to_sign_in.setOnClickListener(v -> {
            nav_to_sign_in(view);
        });
    }

    public void  nav_to_sign_in(View view){
        NavDirections actionToSignIn = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
        Navigation.findNavController(view).navigate(actionToSignIn);
    }

    private void SignUp(View view){
        eMail = eMailField.getText().toString();
        password = passwordField.getText().toString();
        password2 = passwordField2.getText().toString();
        theAge = age.getText().toString();
        termsAndConditions = terms.isChecked();
        trainercheckbox=trainer.isChecked();
        name = fullName.getText().toString();

        if(eMail.isEmpty() || password.isEmpty() || password2.isEmpty() || theAge.isEmpty() || name.isEmpty()) {
            Toast.makeText(view.getContext(), getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
        }else if(Integer.parseInt(theAge) < 18){
            Toast.makeText(view.getContext(), getString(R.string.error_age), Toast.LENGTH_SHORT).show();
        }else if(!termsAndConditions){
            Toast.makeText(view.getContext(), getString(R.string.error_terms), Toast.LENGTH_SHORT).show();
        }else if(!password.matches(password2)){
            Toast.makeText(view.getContext(), getString(R.string.error_passwor_match), Toast.LENGTH_SHORT).show();
        }else if(!eMail.contains("@")){
            Toast.makeText(view.getContext(), getString(R.string.error_email), Toast.LENGTH_SHORT).show();
        }else {
            firebaseAuth.createUserWithEmailAndPassword(eMail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {}
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            if(trainercheckbox) {
                user = new User(eMail, theAge, "1", name);
                postData = new HashMap<>();
                postData.put("email", eMail);
                postData.put("age", theAge);
                postData.put("trainer", "1");
                postData.put("fullName",name);
            }
            else{
                user = new User(eMail, theAge, "0", name);
                postData = new HashMap<>();
                postData.put("email", eMail);
                postData.put("age", theAge);
                postData.put("trainer", "0");
                postData.put("fullName",name);
            }
            firebaseFirestore.collection("Users").add(postData).addOnSuccessListener(new OnSuccessListener<DocumentReference>(){

                @Override
                public void onSuccess(DocumentReference documentReference) {
                    firebaseAuth.signOut();

                    SignUpFragmentDirections.ActionSignUpFragmentToSignInFragment actionToSignIn = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
                    Navigation.findNavController(view).navigate(actionToSignIn);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }
}