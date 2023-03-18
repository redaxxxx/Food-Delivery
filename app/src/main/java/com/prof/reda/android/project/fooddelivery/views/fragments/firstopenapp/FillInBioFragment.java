package com.prof.reda.android.project.fooddelivery.views.fragments.firstopenapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentFillInBioBinding;
import com.prof.reda.android.project.fooddelivery.utils.Config;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillInBioFragment extends Fragment {
    private FragmentFillInBioBinding binding;
    private DatabaseReference mDatabase;

    private FirebaseAuth auth;
    private String firstName;
    private String lastName;
    private String mobileNumber;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_in_bio, container, false);

        auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("user-info");


        binding.nextButton.setOnClickListener(view -> {
            if (isValidPhoneNumber(mobileNumber)) {
                addInfoToFirebaseDB();
            }
        });
        return binding.getRoot();
    }

    private void addInfoToFirebaseDB() {
        firstName = binding.firstNameEditText.getText().toString();
        lastName = binding.lastNameEditText.getText().toString();
        mobileNumber = binding.phoneNumberEditText.getText().toString();

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(mobileNumber)){

            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("firstname", firstName);
            hashMap.put("lastname",lastName);
            hashMap.put("mobileNUmber", mobileNumber);

            if (firebaseUser.getUid() !=null){

                Log.d("Firebase Id", firebaseUser.getUid());
                mDatabase.child(firebaseUser.getUid()).push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Success add data", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }

//    private void getEnteredData() {
//        firstName = binding.firstNameEditText.getText().toString();
//        lastName = binding.lastNameEditText.getText().toString();
//        mobileNumber = binding.phoneNumberEditText.getText().toString();
//        if (TextUtils.isEmpty(firstName)) {
//            Toast.makeText(getActivity(), "Enter Username", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(lastName)) {
//            Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (TextUtils.isEmpty(mobileNumber)) {
//            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
//            return;
//        }
//    }

    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("639[0-9]{9}");
    }
}