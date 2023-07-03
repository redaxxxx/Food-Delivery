package com.prof.reda.android.project.fooddelivery.ui.fragments.firstopenapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentSignUpBinding;
import com.prof.reda.android.project.fooddelivery.utils.Constants;


import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;
import java.util.HashMap;
import java.util.Map;
public class SignupFragment extends Fragment {
    private FragmentSignUpBinding binding;
    private ProgressDialog progressDialog;
    private String username;
    private String password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        username = binding.usernameEditText.getText().toString();
        password = binding.passwordEditText.getText().toString();


        binding.createBtn.setOnClickListener(view -> {
            // validate fields first
            if (isValidate()){
                register();
            }
        });

        binding.alreadyHaveAccount.setOnClickListener(view -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameAuthContainer,
                    new LoginFragment()).commit();
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

    private void register(){

        StringRequest request = new StringRequest(Request.Method.POST, Constants.REGISTER, response -> {
            //we get response if connection success
            try {
                JSONObject object = new JSONObject(response);

                if (object.getBoolean("status")){

                    progressDialog.setMessage("Registering");
                    progressDialog.show();

                    JSONObject data = object.getJSONObject("data");

                    //make shared preference data
                    SharedPreferences dataPref = getActivity().getApplicationContext()
                            .getSharedPreferences("user", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = dataPref.edit();
                    editor.putString("token", object.getString("token"));
                    editor.putString("name", data.getString("name"));
                    editor.putString("email", data.getString("email"));
                    editor.putString("password", data.getString("password"));
                    editor.putString("mobile", data.getString("mobile"));
                    editor.putBoolean("isLoggedIn", true);
                    editor.apply();

                    //if success
                    Toast.makeText(getContext(), "register success", Toast.LENGTH_SHORT).show();

                }else{
                    Log.d(Constants.TAG, "status of registers is false");
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_signup,
                        new FillInBioFragment()).commit();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            progressDialog.dismiss();

        }, error -> {
            //error if connection failed
            error.printStackTrace();
            Log.d(Constants.TAG, error.toString());
            progressDialog.dismiss();
        }){
            //add parameters


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String accessToken = "602|xgBtN1K20f2QBCjVinM5oKagOIGqfMtxgAoRyknE";
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + accessToken);
                headers.put("User-Agent", "PostmanRuntime/7.32.2");
                headers.put("Content-Type", "multipart/form-data");
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("email", binding.emailEditText.getText().toString());
                params.put("password", binding.passwordEditText.getText().toString());
                params.put("name", binding.usernameEditText.getText().toString());

                return params;
            }
        };


        //add this request to requestqueue
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        requestQueue.add(request);
    }

}
