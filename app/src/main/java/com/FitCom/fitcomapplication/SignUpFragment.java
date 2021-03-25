package com.FitCom.fitcomapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {
    private EditText eMailField, passwordField;
    private Button buttonSignUp;
    private String eMail, password;
    private FirebaseAuth firebaseAuth;

    public SignUpFragment() {
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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        eMailField = view.findViewById(R.id.editTextSignUpEmail);
        passwordField = view.findViewById(R.id.editTextSignUpPassword);
        buttonSignUp = view.findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(v -> {
            SignUp(view);
        });
    }

    private void SignUp(View view){
        eMail = eMailField.getText().toString();
        password = passwordField.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(eMail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                firebaseAuth.signOut();
                Toast.makeText(view.getContext(), "User Created", Toast.LENGTH_SHORT).show();
                SignUpFragmentDirections.ActionSignUpFragmentToSignInFragment actionToSignIn = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment();
                //actionToSignIn.setEMail(eMail);
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