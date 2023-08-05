package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentUploadPhotoBinding;
import com.prof.reda.android.project.fooddelivery.models.User;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UploadPhotoFragment extends Fragment {

    private FragmentUploadPhotoBinding binding;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String userId;
    private Uri imgUrl;

    private Dialog dialog;

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    if (uri != null){
                        binding.uploadPhotoFrame.setVisibility(View.GONE);
                        binding.photoCardView.setVisibility(View.VISIBLE);
                        binding.profileImgView.setImageURI(uri);
                        imgUrl = uri;
                        uploadFile(uri);
                    }else {
                        Toast.makeText(getActivity(), "Failed in pick picture", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload_photo, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        Bundle bundle = getArguments();
        if (bundle != null){

            email = bundle.getString(Constants.KEY_E_MMAIL);
            firstName = bundle.getString(Constants.KEY_FIRST_NAME);
            lastName = bundle.getString(Constants.KEY_SECOND_NAME);
            phoneNumber = bundle.getString(Constants.KEY_PHONE_NUMBER);
            username = bundle.getString(Constants.KEY_USERNAME);
        }


        binding.gelleryCardView.setOnClickListener(view -> {
            mGetContent.launch("image/*");
        });

        binding.nextBtn.setOnClickListener(view -> {
            saveData(imgUrl);
        });
        return binding.getRoot();
    }

    private void uploadFile(Uri imgUri){
        StorageReference imageRef = storageReference.child(userId + imgUri.getLastPathSegment());
        imageRef.putFile(imgUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (task.isSuccessful()){
                    throw Objects.requireNonNull(task.getException());
                }
                return imageRef.getDownloadUrl();
            }
        }).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void saveData(Uri imgUrl){
        dialog.show();

        User user = new User();
        user.setFirstName(firstName);
        user.setSecondName(lastName);
        user.setEmail(email);
        user.setProfileImgUrl(imgUrl.toString());
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);

        db.collection("users").document(userId).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.frameAuthContainer, new SignUpSuccessFragment())
                                                .commit();

                        dialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d(Constants.TAG, e.getMessage());
                    }
                });
    }


}