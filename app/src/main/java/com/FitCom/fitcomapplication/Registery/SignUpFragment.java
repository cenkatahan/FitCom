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
import androidx.navigation.Navigation;
import com.FitCom.fitcomapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {
    private EditText eMailField, passwordField, passwordField2, age;
    private Button buttonSignUp;
    private CheckBox terms;
    private String eMail, password,password2, theAge;
    private boolean termsAndConditions;
    private FirebaseAuth firebaseAuth;

    public SignUpFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        buttonSignUp = view.findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(v -> {
            SignUp(view);
        });
    }

    private void SignUp(View view){
        eMail = eMailField.getText().toString();
        password = passwordField.getText().toString();
        password2 = passwordField2.getText().toString();
        theAge = age.getText().toString();
        termsAndConditions = terms.isChecked();

        if(eMail.isEmpty() || password.isEmpty() || password2.isEmpty() || theAge.isEmpty()) {
            Toast.makeText(view.getContext(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
        }else if(Integer.parseInt(theAge) < 18){
            Toast.makeText(view.getContext(), "You must be at least 18!", Toast.LENGTH_SHORT).show();
        }else if(!termsAndConditions){
            Toast.makeText(view.getContext(), "To use the app, the terms and conditions must accepted!", Toast.LENGTH_SHORT).show();
        }else if(!password.matches(password2)){
            Toast.makeText(view.getContext(), "Passwords does not match!", Toast.LENGTH_SHORT).show();
        }else if(!eMail.contains("@")){
            Toast.makeText(view.getContext(), "Enter a valid email address!", Toast.LENGTH_SHORT).show();
        }else {
            firebaseAuth.createUserWithEmailAndPassword(eMail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    firebaseAuth.signOut();
                    Toast.makeText(view.getContext(), "User Created", Toast.LENGTH_SHORT).show();
                    SignUpFragmentDirections.ActionSignUpFragmentToSignInFragment actionToSignIn = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
                    Navigation.findNavController(view).navigate(actionToSignIn);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}