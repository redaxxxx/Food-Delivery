package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignUpBinding;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private Dialog dialog;

    private SharedPreferences pref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        pref = getActivity().getSharedPreferences("users", Context.MODE_PRIVATE);

        binding.createBtn.setOnClickListener(view -> {
            // validate fields first
            if (isValidate()){
                register(binding.emailEt.getText().toString(),
                        binding.passwordEt.getText().toString());
            }
        });

        binding.alreadyHaveAccount.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isAlreadySignup", false);

            Fragment fragment = new LoginFragment();

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameAuthContainer, fragment).commit();
        });

        return binding.getRoot();
    }

    private boolean isValidate(){

        if (TextUtils.isEmpty(binding.emailEt.getText())){
            binding.emailOutline.setError("Email is required");
            binding.emailOutline.setErrorEnabled(true);
            return false;
        }
        if (TextUtils.isEmpty(binding.passwordEt.getText())){
            binding.passwordOutline.setError("Password Required");
            binding.passwordOutline.setErrorEnabled(true);
            return false;
        }
        if (TextUtils.isEmpty(binding.usernameEt.getText())){
            binding.usernameOutline.setError("username is required");
            binding.usernameOutline.setErrorEnabled(true);
        }

        binding.emailOutline.setErrorEnabled(false);
        binding.passwordOutline.setErrorEnabled(false);
        binding.usernameOutline.setErrorEnabled(false);

        return true;
    }

    private void register(String email, String password){
        dialog.show();
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
                                        dialog.dismiss();
                                        Bundle bundle = new Bundle();
                                        bundle.putString(Constants.KEY_E_MMAIL, email);
                                        bundle.putString(Constants.KEY_USERNAME, binding.usernameEt.getText().toString());
                                        Fragment fragment = new FillInBioFragment();
                                        fragment.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .replace(R.id.frameAuthContainer, fragment)
                                                .commit();
                                    }else{
                                        Log.d(Constants.TAG, "task is not success");
                                    }
                                }
                            });

                }else {
                    Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_LONG).show();
                    Log.e("LOG_TAG", "Error ******** Error reason : "+ task.getException().getMessage().toString());
                    dialog.dismiss();
                }
            }
        });
    }

}
