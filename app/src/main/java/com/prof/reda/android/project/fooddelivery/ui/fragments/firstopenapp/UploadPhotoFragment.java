package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

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
import com.prof.reda.android.project.fooddelivery.utils.Config;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

public class UploadPhotoFragment extends Fragment {

    private FragmentUploadPhotoBinding binding;
    private StorageReference storageReference;
    private FirebaseFirestore db;
    private SharedPreferences pref;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Uri profileImg;
    private Uri imgCapturedUrl;
    private Dialog dialog;

    private final ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    if (uri != null){
                        binding.uploadPhotoFrame.setVisibility(View.GONE);
                        binding.photoCardView.setVisibility(View.VISIBLE);
                        binding.profileImgView.setImageURI(uri);
//                        uploadFile(uri);
                        profileImg = uri;
                    }else {
                        Toast.makeText(getActivity(), "Failed in pick picture", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> mCameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {

                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null){
                        binding.uploadPhotoFrame.setVisibility(View.GONE);
                        binding.photoCardView.setVisibility(View.VISIBLE);

                        Bundle bundle = result.getData().getExtras();
                        imgCapturedUrl = (Uri) bundle.get("data");
                        Log.d(Constants.TAG, "imgCaptureUrl " + imgCapturedUrl.toString());
                        Bitmap bitmap = (Bitmap) bundle.get("data");

                        binding.profileImgView.setImageBitmap(bitmap);

                        saveData(imgCapturedUrl);

                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_upload_photo, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        pref = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);
        getInfoFromSharedPreference();

//        Bundle bundle = getArguments();
//        if (bundle != null){
//
//            email = bundle.getString(Constants.KEY_E_MMAIL);
//            firstName = bundle.getString(Constants.KEY_FIRST_NAME);
//            lastName = bundle.getString(Constants.KEY_SECOND_NAME);
//            phoneNumber = bundle.getString(Constants.KEY_PHONE_NUMBER);
//            username = bundle.getString(Constants.KEY_USERNAME);
//        }


        binding.gelleryCardView.setOnClickListener(view -> {
            mGetContent.launch("image/*");
        });

        binding.takePhotoCardView.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mCameraLauncher.launch(intent);
        });

        binding.nextBtn.setOnClickListener(view -> {
            uploadProfileImg(profileImg);
        });
        return binding.getRoot();
    }

    private void getInfoFromSharedPreference(){
        email = pref.getString(Constants.KEY_E_MMAIL, null);
        firstName = pref.getString(Constants.KEY_FIRST_NAME, null);
        lastName = pref.getString(Constants.KEY_SECOND_NAME, null);
        phoneNumber = pref.getString(Constants.KEY_PHONE_NUMBER, null);
        username = pref.getString(Constants.KEY_USERNAME, null);
    }

    private void uploadProfileImg(Uri imgUri){
        StorageReference imgRef = storageReference.child(imgUri.getLastPathSegment());
        imgRef.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            saveData(uri);
                        }
                    });
                }
            }
        });
    }

    private void saveData(Uri profileImgUrl){
        dialog.show();

        User user = new User();
        user.setFirstName(firstName);
        user.setSecondName(lastName);
        user.setEmail(email);

        user.setProfileImgUrl(profileImgUrl.toString());
        user.setPhoneNumber(phoneNumber);
        user.setUsername(username);

        db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                .collection("userInfo").add(user)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Constants.TAG, "Save data is Failed: " + e.getLocalizedMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dialog.dismiss();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.completeSignUpFrame, new SignUpSuccessFragment())
                                .commit();


                    }
                });
//                .set(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//
//                        getActivity().getSupportFragmentManager().beginTransaction()
//                                                .replace(R.id.frameAuthContainer, new SignUpSuccessFragment())
//                                                .commit();
//
//                        dialog.dismiss();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        e.printStackTrace();
//                        Log.d(Constants.TAG, e.getMessage());
//                    }
//                });
    }


}