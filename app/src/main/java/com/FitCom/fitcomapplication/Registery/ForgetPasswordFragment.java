package com.FitCom.fitcomapplication.Registery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.FitCom.fitcomapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

@SuppressWarnings({"ALL", "rawtypes"})
public class ForgetPasswordFragment extends Fragment {

    private EditText eMail;
    private FirebaseAuth firebaseAuth;

    public ForgetPasswordFragment() {
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
        return inflater.inflate(R.layout.fragment_forget_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        eMail = view.findViewById(R.id.editText_reset_email);
        ImageButton btn_back = view.findViewById(R.id.button_reset_toSignIn);
        btn_back.setOnClickListener(this::goBackSignIn);

        Button btn_reset = view.findViewById(R.id.button_to_sign_in);
        btn_reset.setOnClickListener(this::resetEMailFromFB);
    }

    private void goBackSignIn(View view){
        NavDirections goBack = ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToSignInFragment2();
        Navigation.findNavController(view).navigate(goBack);
    }

    private void resetEMailFromFB(View view){
        String email_str = eMail.getText().toString();

        if(email_str.isEmpty()){
            Toast.makeText(getContext(),view.getContext().getString(R.string.error_fields), Toast.LENGTH_SHORT).show();
        }else{
            firebaseAuth.sendPasswordResetEmail(email_str)
                    .addOnCompleteListener((OnCompleteListener) task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getContext(),getString(R.string.str_check), Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getContext(), view.getContext().getString(R.string.error_error), Toast.LENGTH_SHORT).show();

                        }
                    });

            goBackSignIn(view);
        }
    }
}