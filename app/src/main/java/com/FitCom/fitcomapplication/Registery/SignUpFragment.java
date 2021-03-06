package com.FitCom.fitcomapplication.Registery;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.FitCom.fitcomapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

@SuppressWarnings("ALL")
public class SignUpFragment extends Fragment {
    private EditText eMailField, passwordField, passwordField2, age, fullName;
    private CheckBox terms,trainer;
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

    @SuppressWarnings("CodeBlock2Expr")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        TextView termsDetail = view.findViewById(R.id.terms_detail_text);
        eMailField = view.findViewById(R.id.editTextSignUpEmail);
        passwordField = view.findViewById(R.id.editTextSignUpPassword);
        passwordField2 = view.findViewById(R.id.editTextSignUpPassword2);
        age = view.findViewById(R.id.editTextAge);
        terms = view.findViewById(R.id.termsCheckBox);
        fullName = view.findViewById(R.id.editTextFullName);
        trainer=view.findViewById(R.id.checkBoxAccountType);
        Button buttonSignUp = view.findViewById(R.id.buttonSignUp);
        Button button_nav_to_sign_in = view.findViewById(R.id.button_nav_to_sign_in);
        buttonSignUp.setOnClickListener(v -> {
            SignUp(view);
        });
        button_nav_to_sign_in.setOnClickListener(v -> {
            nav_to_sign_in(view);
        });

        terms.setOnClickListener(v -> {
          showTerms(view);
        });
    }

    public void  nav_to_sign_in(View view){
        NavDirections actionToSignIn = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
        Navigation.findNavController(view).navigate(actionToSignIn);
    }

    private void SignUp(View view){
        String eMail = eMailField.getText().toString();
        String password = passwordField.getText().toString();
        String password2 = passwordField2.getText().toString();
        String theAge = age.getText().toString();
        boolean termsAndConditions = terms.isChecked();
        boolean trainercheckbox = trainer.isChecked();
        String name = fullName.getText().toString();

        if(eMail.isEmpty() || password.isEmpty() || password2.isEmpty() || theAge.isEmpty() || name.isEmpty()) {
            Toast.makeText(view.getContext(), getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
        }else if(Integer.parseInt(theAge) < 18){
            Toast.makeText(view.getContext(), getString(R.string.error_age), Toast.LENGTH_SHORT).show();
        }else if(!termsAndConditions){
            Toast.makeText(view.getContext(), getString(R.string.error_terms), Toast.LENGTH_SHORT).show();
        }else if(!password.matches(password2)){
            Toast.makeText(view.getContext(), getString(R.string.error_password_match), Toast.LENGTH_SHORT).show();
        }else if(!eMail.contains("@")){
            Toast.makeText(view.getContext(), getString(R.string.error_email), Toast.LENGTH_SHORT).show();
        }else {
            firebaseAuth.createUserWithEmailAndPassword(eMail, password).addOnSuccessListener(authResult -> {}).addOnFailureListener(e -> Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
            HashMap<String, Object> postData;
            User user;
            if(trainercheckbox) {
                user = new User(eMail, theAge, "1", name);
                postData = new HashMap<>();
                postData.put("email", eMail);
                postData.put("age", theAge);
                postData.put("trainer", "1");
                postData.put("fullName", name);
            }
            else{
                user = new User(eMail, theAge, "0", name);
                postData = new HashMap<>();
                postData.put("email", eMail);
                postData.put("age", theAge);
                postData.put("trainer", "0");
                postData.put("fullName", name);
            }
            firebaseFirestore.collection("Users").add(postData).addOnSuccessListener(documentReference -> {
                firebaseAuth.signOut();

                SignUpFragmentDirections.ActionSignUpFragmentToSignInFragment actionToSignIn = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
                Navigation.findNavController(view).navigate(actionToSignIn);
            }).addOnFailureListener(e -> {
            });
        }
    }

    public void showTerms(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.str_terms));
        builder.setMessage(view.getResources().getString(R.string.terms_but_detailed));
        builder.setNegativeButton(view.getResources().getString(R.string.hide), null);
        builder.show();
    }
}