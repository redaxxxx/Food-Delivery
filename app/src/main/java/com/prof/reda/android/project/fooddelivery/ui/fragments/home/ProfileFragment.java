package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.FavoriteAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentProfileBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;
import com.prof.reda.android.project.fooddelivery.utils.Config;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private FirebaseFirestore db;
    private FavoriteAdapter favoriteAdapter;
    private List<Menu> menuList = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private String imgUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        db = FirebaseFirestore.getInstance();

        getProfileInfo();

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

    private void getProfileInfo() {

        db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                .collection("userInfo").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot doc: task.getResult()){
                                firstName = doc.getString("firstName");
                                lastName = doc.getString("secondName");
                                email = doc.getString("email");
                                imgUrl = doc.getString("profileImgUrl");

                                name = firstName + " " + lastName;
//
                                binding.nameOFMemberTv.setText(name);
                                binding.emailOFMemberTv.setText(email);

                                Glide.with(getActivity()).load(imgUrl)
                                        .into(binding.avatar);
                            }
                        }else {
                            Log.d(Constants.TAG, "Failed in get userInfo" + task.getException());
                        }
                    }
                });
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()){
//                            DocumentSnapshot doc = task.getResult();
//                                firstName = doc.getString("firstName");
//                                lastName = doc.getString("secondName");
//                                email = doc.getString("email");
//                                imgUrl = doc.getString("profileImgUrl");
//
//                                name = firstName + " " + lastName;
//
//                                binding.nameOFMemberTv.setText(name);
//                                binding.emailOFMemberTv.setText(email);
//
//                                Glide.with(getActivity()).load(imgUrl)
//                                        .into(binding.avatar);
//
//                        }else {
//                            Log.d(Constants.TAG, "userId is" + Config.firebaseUSerID);
//                            Log.d(Constants.TAG, task.getException().toString());
//                        }
//                    }
//                });
    }
}