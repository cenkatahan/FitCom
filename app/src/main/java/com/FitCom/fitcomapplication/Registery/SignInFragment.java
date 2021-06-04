package com.FitCom.fitcomapplication.Registery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.FitCom.fitcomapplication.HomeScreen.HomePageActivity;
import com.FitCom.fitcomapplication.R;
import com.google.firebase.auth.FirebaseAuth;

@SuppressWarnings("ALL")
public class SignInFragment extends Fragment {

    private EditText eMailField, passwordField;
    private FirebaseAuth firebaseAuth;

    public SignInFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        eMailField = view.findViewById(R.id.editTextSignInEmail);
        passwordField = view.findViewById(R.id.editTextSignInPassword);
        Button buttonSignUp = view.findViewById(R.id.buttonToSignUp);
        buttonSignUp.setOnClickListener(v -> {
            onClickToSignUp(view);
        });

        Button buttonSignIn = view.findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(v -> {
            onClickToSignIn(view);
        });

        TextView passwordForget = view.findViewById(R.id.forget_link);
        passwordForget.setOnClickListener(this::goForgetPasswordFrg);
    }

    private void onClickToSignUp(View view){
        NavDirections actionToSignUp = SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
        Navigation.findNavController(view).navigate(actionToSignUp);
    }

    private void onClickToSignIn(View view){
        String eMail = eMailField.getText().toString();
        String password = passwordField.getText().toString();

        if(eMail.isEmpty() || password.isEmpty()){
            Toast.makeText(view.getContext(), getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
        }else {
            firebaseAuth.signInWithEmailAndPassword(eMail, password).addOnSuccessListener(authResult -> {
                Intent intent = new Intent(view.getContext(), HomePageActivity.class);
                getActivity().finish();
                startActivity(intent);
            }).addOnFailureListener(e -> Toast.makeText(view.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void goForgetPasswordFrg(View view){
        NavDirections goForgetFrg = SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment2();
        Navigation.findNavController(view).navigate(goForgetFrg);
    }
}