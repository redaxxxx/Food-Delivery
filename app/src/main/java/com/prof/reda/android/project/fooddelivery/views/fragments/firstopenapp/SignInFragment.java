package com.prof.reda.android.project.fooddelivery.views.fragments.firstopenapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignInBinding;
import com.prof.reda.android.project.fooddelivery.utils.Config;
import com.prof.reda.android.project.fooddelivery.views.activities.HomeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding binding;
    private FirebaseAuth auth;
    private String email;
    private String password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in,container, false);
        auth = FirebaseAuth.getInstance();
            binding.loginBtn.setOnClickListener(view -> {
                getEnteredData();
                if (isValidEmail(email)){
                    if (isValidPassword(password)){
                        loginFirebase(email, password);
                        Toast.makeText(getActivity(), "Success Login", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        return binding.getRoot();
    }

    private void loginFirebase(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            if (auth.getCurrentUser().isEmailVerified()){
                                FirebaseUser firebaseUser = auth.getCurrentUser();
                                String id = firebaseUser.getUid();
                                Config.firebaseUSerID = id;
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                                Log.d("Firebase Id", firebaseUser.getUid());
                            }else {
                                Toast.makeText(getActivity(), "Check Email", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void getEnteredData(){
        email = binding.emailEditText.getText().toString();
        password = binding.passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            Toast.makeText(getActivity(), "valid email address", Toast.LENGTH_SHORT).show();
            // or
            return true;
        } else {
            Toast.makeText(getActivity(), "Invalid email address", Toast.LENGTH_SHORT).show();
            //or
            return false;
        }
    }

    private boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


}