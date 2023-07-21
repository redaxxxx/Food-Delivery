package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentFillInBioBinding;
import com.prof.reda.android.project.fooddelivery.ui.activities.HomeActivity;
import com.prof.reda.android.project.fooddelivery.ui.activities.LoginActivity;

import java.util.HashMap;
import java.util.Map;


public class FillInBioFragment extends Fragment {
    private FragmentFillInBioBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String email;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_in_bio, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        assert getArguments() != null;
        email = getArguments().getString("email");

        binding.nextButton.setOnClickListener(view -> {
            if (isValide()){
                completeRegister();
            }
        });
        return binding.getRoot();
    }

    private void completeRegister() {

        Map<String, String> hashMap = new HashMap<>();
//        userIfno.put("firstName", binding.firstNameEditText.getText().toString());
//        userIfno.put("lastName", binding.lastNameEditText.getText().toString());
        hashMap.put("email", email);
        hashMap.put("phoneNumber", binding.phoneNumberEditText.getText().toString());

        String userId = auth.getCurrentUser().getUid();
        db.collection("users").document(userId).set(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Map<String, String> username = new HashMap<>();
                        username.put("firstName", binding.firstNameEditText.getText().toString());
                        username.put("lastName", binding.lastNameEditText.getText().toString());

                        db.collection("users").document(userId).collection("username")
                                .add(username).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        // Post document added successfully

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean("isLoggedIn", true);
                                        editor.apply();

                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Handle post document addition failure
                                        e.printStackTrace();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

    }

    private boolean isValide(){
        if (binding.phoneNumberEditText.getText().toString() == null){
            binding.phoneNumberEditText.setError("phone number required");
        }

        if (TextUtils.isEmpty(binding.firstNameEditText.getText().toString())){
            binding.firstNameEditText.setError("first name is required");
        }

        if (TextUtils.isEmpty(binding.lastNameEditText.getText().toString())){
            binding.lastNameEditText.setError("last name is required");
        }

        return true;
    }
}