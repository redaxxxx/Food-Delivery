package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentLoginBinding;
import com.prof.reda.android.project.fooddelivery.ui.activities.ForgotPasswordActivity;
import com.prof.reda.android.project.fooddelivery.ui.activities.HomeActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.utils.DataSource;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private FirebaseAuth firebaseAuth;
    private Dialog dialog;
    private DataSource dataSource;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        SharedPreferences preferences = getActivity().getSharedPreferences(Constants.USERS_PREF, Context.MODE_PRIVATE);

        dataSource = new DataSource(preferences);

        binding.loginBtn.setOnClickListener(view -> {
            // validate fields first
            if (isValidate()){
                login(binding.emailEt.getText().toString(),
                        binding.passwordEt.getText().toString());
            }
        });

        binding.forgetPasswordTV.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ForgotPasswordActivity.class));
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

        binding.emailOutline.setErrorEnabled(false);
        binding.passwordOutline.setErrorEnabled(false);

        return true;
    }

    private void login(String email, String password) {
        dialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();

                    if (firebaseAuth.getCurrentUser().isEmailVerified()){

                        startActivity(new Intent(getActivity(), HomeActivity.class));

                    } else {
                        Toast.makeText(getActivity(), "Verify your Email", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Log.d(Constants.TAG, "Login Failed: " + task.getException());
                    Toast.makeText(getActivity(), "Login Failed " + task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}
