package com.FitCom.fitcomapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

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


public class SignInFragment extends Fragment {


    private Button buttonSignUp, buttonSignIn;
    private EditText eMailField, passwordField;
    private String eMail, password;
    private FirebaseAuth firebaseAuth;

    public SignInFragment() {
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
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        eMailField = view.findViewById(R.id.editTextSignInEmail);
        passwordField = view.findViewById(R.id.editTextSignInPassword);

        buttonSignUp = view.findViewById(R.id.buttonToSignUp);
        buttonSignUp.setOnClickListener(v -> {
            onClickToSignUp(view);
        });

        buttonSignIn = view.findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(v -> {
            onClickToSignIn(view);
        });

//        if(getArguments() != null){
//            String deliveredEmail = SignInFragmentArgs.fromBundle(getArguments()).getEMail();
//            eMailField.setText(deliveredEmail);
//        }

    }

    private void onClickToSignUp(View view){
        NavDirections actionToSignUp = SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
        Navigation.findNavController(view).navigate(actionToSignUp);
    }

    private void onClickToSignIn(View view){

        eMail = eMailField.getText().toString();
        password = passwordField.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(eMail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Intent intent = new Intent(view.getContext(), HomePageActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}