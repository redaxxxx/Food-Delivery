package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentFillInBioBinding;
import com.prof.reda.android.project.fooddelivery.models.User;
import com.prof.reda.android.project.fooddelivery.utils.Config;
import com.prof.reda.android.project.fooddelivery.utils.Constants;


public class FillInBioFragment extends Fragment {
    private FragmentFillInBioBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String email;
    private SharedPreferences sharedPreferences;
    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fill_in_bio, container, false);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        sharedPreferences = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

//        Bundle bundle = getArguments();

        binding.nextButton.setOnClickListener(view -> {
            if (isValidate()){
//                if (bundle != null){
//                    bundle.putString(Constants.KEY_FIRST_NAME, binding.firstNameEt.getText().toString());
//                    bundle.putString(Constants.KEY_SECOND_NAME, binding.lastNameEt.getText().toString());
//                    bundle.putString(Constants.KEY_PHONE_NUMBER, binding.phoneNumberEt.getText().toString());
//                }
//
//                Fragment fragment = new PaymentMethodsFragment();
//                fragment.setArguments(bundle);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.KEY_FIRST_NAME, binding.firstNameEt.getText().toString());
                editor.putString(Constants.KEY_SECOND_NAME, binding.lastNameEt.getText().toString());
                editor.putString(Constants.KEY_PHONE_NUMBER, binding.phoneNumberEt.getText().toString());
                editor.apply();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fillBioFrame, new PaymentMethodsFragment()).commit();

            }
        });
        return binding.getRoot();
    }

    private boolean isValidate(){
        if (binding.phoneNumberEt.getText() == null){
            binding.phoneNumberOutline.setError("phone number required");
            binding.phoneNumberOutline.setErrorEnabled(true);
        }

        if (TextUtils.isEmpty(binding.firstNameEt.getText())){
            binding.firstNameOutline.setError("first name is required");
            binding.firstNameOutline.setErrorEnabled(true);
        }

        if (TextUtils.isEmpty(binding.lastNameEt.getText())){
            binding.lastNameOutline.setError("last name is required");
            binding.lastNameOutline.setErrorEnabled(true);
        }

        binding.phoneNumberOutline.setErrorEnabled(false);
        binding.firstNameOutline.setErrorEnabled(false);
        binding.lastNameOutline.setErrorEnabled(false);
        return true;
    }

    private void saveData(){
        dialog.show();

        User user = new User();
        user.setFirstName(binding.firstNameEt.getText().toString());
        user.setSecondName(binding.lastNameEt.getText().toString());
        user.setPhoneNumber(binding.phoneNumberEt.getText().toString());

        db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                .collection("userInfo").add(user)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Constants.TAG, "Fill Bio is Failed: "
                                + e.getMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dialog.dismiss();

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fillBioFrame, new PaymentMethodsFragment()).commit();
                    }
                });
    }
}