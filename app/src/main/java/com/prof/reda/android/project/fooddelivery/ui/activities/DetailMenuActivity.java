package com.prof.reda.android.project.fooddelivery.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.databinding.ActivityDetailMenuBinding;
import com.prof.reda.android.project.fooddelivery.models.Cart;
import com.prof.reda.android.project.fooddelivery.utils.Config;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

public class DetailMenuActivity extends AppCompatActivity {

    private ActivityDetailMenuBinding binding;
    String userId;
    private String pic;
    private String foodName;
    private String restroName;
    private String price;
    private DocumentReference docId;
    private FirebaseFirestore db;
    private StorageReference storageReference;

    private DocumentReference orderRef;

    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_menu);

        db = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_bar);
        dialog.setCanceledOnTouchOutside(false);

        binding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            }
        });
        getInfoFromIntent();

        binding.foodName.setText(foodName);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        binding.addToCardBtn.setOnClickListener(view -> {
            addToCart();
        });
    }

    private void addToCart(){
        dialog.show();
        Cart cart = new Cart();
        cart.setFoodName(foodName);
        cart.setRestroName(restroName);
        cart.setPrice(price);
        cart.setFoodImage(pic);

        docId = db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                        .collection(Constants.CARTS_COLLECTION).document();

        cart.setCartId(docId.getId());

        db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                .collection(Constants.CARTS_COLLECTION).document(docId.getId()).set(cart)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        dialog.dismiss();
                        Toast.makeText(DetailMenuActivity.this, "Add to Cart", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d(Constants.TAG, e.getMessage());
                    }
                });

//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        dialog.dismiss();
//                        Toast.makeText(DetailMenuActivity.this, "Add to Cart", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        e.printStackTrace();
//                        Log.d(Constants.TAG, e.getMessage());
//                    }
//                });
    }


    private void getInfoFromIntent(){
        Intent intent = getIntent();
        pic = intent.getStringExtra(Constants.KEY_IMAGE);
        foodName = intent.getStringExtra(Constants.KEY_FOOD_NAME);
        restroName = intent.getStringExtra(Constants.KEY_RESTRO_NAME);
        price = intent.getStringExtra(Constants.KEY_PRICE);

    }

}