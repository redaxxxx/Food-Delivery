package com.prof.reda.android.project.fooddelivery.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class GetFoodImageFromStorage {
    private Context mContext;
    private ImageView imageView;
    private String foodImage;

    public GetFoodImageFromStorage(Context mContext, ImageView imageView, String foodImage) {
        this.mContext = mContext;
        this.imageView = imageView;
        this.foodImage = foodImage;
    }
    public void getFoodImage(){
        StorageReference foodImgRef = FirebaseStorage.getInstance().getReference(Constants.FOODS_STORAGE_REF
                + "/"  + foodImage);

        try {
            File file = File.createTempFile("food", "png");
            foodImgRef.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Glide.with(mContext).load(file)
                            .into(imageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
