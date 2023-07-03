package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prof.reda.android.project.fooddelivery.ui.activities.AuthActivity;
import com.prof.reda.android.project.fooddelivery.ui.activities.HomeActivity;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentLoginBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);

        binding.loginBtn.setOnClickListener(view -> {
            // validate fields first
            if (isValidate()){
                login();

            }
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
        return true;
    }

    private void login() {

        StringRequest request = new StringRequest(Request.Method.POST, Constants.LOGIN, response -> {
            //we get response if connection success
            try {

                JSONObject object = new JSONObject(response);
                if (object.getBoolean("status")){
                    progressDialog.setMessage("Logging");
                    progressDialog.show();
                    JSONObject user = object.getJSONObject("user");

                    //make shared preference user
                    SharedPreferences userPref = getActivity().getApplicationContext()
                            .getSharedPreferences("user", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = userPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("id", user.getString("id"));
                    editor.putString("name", user.getString("name"));
                    editor.putString("email", user.getString("email"));
                    editor.putString("avatar", user.getString("avatar"));
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    startActivity(new Intent((AuthActivity)getContext(), HomeActivity.class));

                    //if success
                    Toast.makeText(getContext(), "login success", Toast.LENGTH_SHORT).show();

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            progressDialog.dismiss();

        }, error -> {
            //error if connection failed
            error.printStackTrace();
            progressDialog.dismiss();
        }){
            //add parameters
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("email", binding.emailEditText.getText().toString());
                hashMap.put("password", binding.passwordEditText.getText().toString());

                return hashMap;
            }
        };


        //add this request to requestqueue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }

}
