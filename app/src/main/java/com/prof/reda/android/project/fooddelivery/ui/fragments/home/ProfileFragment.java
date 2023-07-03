package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.FavoriteAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentProfileBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FavoriteAdapter favoriteAdapter;
    private List<Menu> menuList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private String token;
    private String userId;
    private String name;
    private String email;
    private String imgUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        sharedPreferences = getActivity().getApplicationContext()
                .getSharedPreferences("user", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);
        userId = sharedPreferences.getString("id", null);

        Log.d(Constants.TAG, "token is " + token);
        Log.d(Constants.TAG, "userId is " + userId);



        getProfileInfo();

        binding.nameOFMemberTv.setText(name);
        binding.emailOFMemberTv.setText(email);

        if (imgUrl != null){
            Picasso.with(requireContext()).load(imgUrl).into(binding.avatar);
        }else {
            binding.avatar.setImageResource(R.drawable.profilephoto);
        }

        menuList.add(new Menu(R.drawable.orderimg2, "Spacy fresh crab", "Waroenk kita", 35));
        menuList.add(new Menu(R.drawable.orderimg1, "Spacy fresh crab", "Waroenk kita", 35));
        menuList.add(new Menu(R.drawable.orderimg3, "Spacy fresh crab", "Waroenk kita", 35));

        prepareRecyclerView(menuList);
        return binding.getRoot();
    }

    private void prepareRecyclerView(List<Menu> menus){
        binding.menusRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.menusRv.setHasFixedSize(true);
        binding.menusRv.setItemAnimator(new DefaultItemAnimator());
        favoriteAdapter = new FavoriteAdapter(menus);
        binding.menusRv.setAdapter(favoriteAdapter);
    }

    private void getProfileInfo(){
        StringRequest request = new StringRequest(Request.Method.GET, Constants.PROFILE +"/"+ userId, response -> {

            try {
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getBoolean("status")){
                    JSONObject data = jsonObject.getJSONObject("data");
                    name = data.getString("name");
                    email = data.getString("email");
                    imgUrl = data.getString("avatar");

                    Log.d(Constants.TAG, "name is " + name);
                    Log.d(Constants.TAG, "email is " + email);
                    Log.d(Constants.TAG, "avatar is " + imgUrl);

                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            error.printStackTrace();
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                headers.put("Content-Type", "application/json");

                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(request);
    }
}