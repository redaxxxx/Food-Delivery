package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.databinding.ActivityDetailMenuBinding;
import com.prof.reda.android.project.fooddelivery.models.EntityOrder;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import java.util.Objects;

public class DetailMenuActivity extends AppCompatActivity {

    private ActivityDetailMenuBinding binding;
    String userId;
    private int pic;
    private String foodName;
    private String restroName;
    private String price;
    private FoodDatabase mDB;
    private FirebaseFirestore db;
    private StorageReference storageReference;

    private DocumentReference orderRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_menu);

        mDB = FoodDatabase.getInstance(this);

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        getInfoFromIntent();

        binding.foodName.setText(foodName);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        binding.addToCardBtn.setOnClickListener(view -> {
            addToCart();
        });
    }

    private void addToCart(){
        Order order = new Order();
        order.setFoodName(foodName);
        order.setRestroName(restroName);
        order.setPrice(price);

        db.collection("users").document(userId)
                .collection("orders").add(order)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
    }


    private void getInfoFromIntent(){
        Intent intent = getIntent();
        pic = intent.getIntExtra("pic", 0);
        foodName = intent.getStringExtra("foodName");
        restroName = intent.getStringExtra("restroName");
        price = intent.getStringExtra("price");

    }

}