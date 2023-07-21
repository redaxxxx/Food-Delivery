package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignUpBinding;
import com.prof.reda.android.project.fooddelivery.ui.activities.AuthActivity;
import com.prof.reda.android.project.fooddelivery.ui.activities.LoginActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private ProgressDialog progressDialog;
    private String username;
    private String password;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        username = binding.usernameEditText.getText().toString();
        password = binding.passwordEditText.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        binding.createBtn.setOnClickListener(view -> {
            // validate fields first
            if (isValidate()){
                register(binding.emailEditText.getText().toString(),
                        binding.passwordEditText.getText().toString());
            }
        });

        binding.alreadyHaveAccount.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });

        return binding.getRoot();
    }

    private boolean isValidate(){

        if (TextUtils.isEmpty(binding.emailEditText.getText().toString())){
            binding.emailEditText.setError("Email is required");
            return false;
        }
        if (TextUtils.isEmpty(binding.passwordEditText.getText().toString())){
            binding.passwordEditText.setError("Required at least 8 characters");
            return false;
        }
        if (TextUtils.isEmpty(binding.usernameEditText.getText().toString())){
            binding.emailEditText.setError("username is required");
        }
        return true;
    }

    private void register(String email, String password){
        progressDialog.setMessage("registering");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseAuth.getCurrentUser()
                            .sendEmailVerification()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                        String userId = firebaseUser.getUid();
                                        Map<String, String> emailAddress = new HashMap<>();
                                        emailAddress.put("email", binding.emailEditText.getText().toString());


//                                        db.collection("users").document(userId).set(emailAddress)
//                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void unused) {
//
//                                                            }
//                                                        }).addOnFailureListener(new OnFailureListener() {
//                                                    @Override
//                                                    public void onFailure(@NonNull Exception e) {
//                                                        e.printStackTrace();
//                                                    }
//                                                });

                                        progressDialog.dismiss();
                                    }else{
                                        Log.d(Constants.TAG, "task is not success");

                                    }

                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", binding.emailEditText.getText().toString());
                                    Fragment fragment = new FillInBioFragment();
                                    fragment.setArguments(bundle);
                                    getActivity().getSupportFragmentManager().beginTransaction()
                                            .replace(R.id.frameAuthContainer, fragment)
                                            .commit();
                                }
                            });
                }else {
                    Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                    Log.e("LOG_TAG", "Error ******** Error reason : "+ task.getException().getMessage().toString());
                    progressDialog.dismiss();
                }
            }
        });
    }

}
