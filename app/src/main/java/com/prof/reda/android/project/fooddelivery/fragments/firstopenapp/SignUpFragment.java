package com.prof.reda.android.project.fooddelivery.fragments.firstopenapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignUpBinding;
import com.prof.reda.android.project.fooddelivery.utils.Config;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private static final String USERS_KEY = "users";
    private DatabaseReference mDatabase;
    private String username;
    private String email;
    private String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        if (mDatabase == null){
            FirebaseDatabase database =FirebaseDatabase.getInstance();
            mDatabase = database.getReference(USERS_KEY);
        }

        binding.alreadyHaveAccount.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment);
        });

        binding.createBtn.setOnClickListener(view -> {
            getEnteredData();
            if (isValidUserName(username)){
                if (isValidEmail(email)){
                    if (isValidPassword(password)){
                        createAccountWithFirebase(username, email, password);
                        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_fillInBioFragment);
                    }
                }
            }

        });
        return binding.getRoot();
    }

    private void createAccountWithFirebase(String username, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            firebaseAuth.getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Log.d("SignUp" , "Success Registration");
                                                FirebaseUser firebaseUser =firebaseAuth.getCurrentUser();
                                                String userId = firebaseUser.getUid();
                                                Config.firebaseUSerID = userId;
                                                mDatabase =FirebaseDatabase.getInstance().
                                                        getReference("users")
                                                        .child("user-info")
                                                        .child(userId);
                                                HashMap<String, String> hashMap = new HashMap<>();
                                                hashMap.put("id", userId);
                                                hashMap.put("username", username);
                                                hashMap.put("email", email);

                                                mDatabase.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Toast.makeText(getActivity(), "Successful Registration! Login Please!",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }else {
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                            Log.e("LOG_TAG", "Error ******** Error reason : "+ task.getException().getMessage().toString());
                        }
                    }
                });
    }

    private void getEnteredData(){
        username = binding.usernameEditText.getText().toString();
        email = binding.emailEditText.getText().toString();
        password = binding.passwordEditText.getText().toString();
        if (TextUtils.isEmpty(username)){
            Toast.makeText(getActivity(), "Enter Username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidUserName(String userName) {
        if (userName.length() >= 8) {
            Toast.makeText(getActivity(), "valid username", Toast.LENGTH_SHORT).show();
            // or
            return true;
        } else {
            Toast.makeText(getActivity(), "Invalid username", Toast.LENGTH_SHORT).show();
            //or
            return false;
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