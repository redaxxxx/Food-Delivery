package com.prof.reda.android.project.fooddelivery.viewModel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.prof.reda.android.project.fooddelivery.models.Food;
import com.prof.reda.android.project.fooddelivery.models.Cart;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.models.Restro;
import com.prof.reda.android.project.fooddelivery.utils.Config;
import com.prof.reda.android.project.fooddelivery.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class FoodViewModel extends ViewModel {

    private final MutableLiveData<List<Food>> foodMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Restro>> restaurantLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Cart>> cartsMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<Order>> ordersMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Order> deleteOrderMutableLiveData = new MutableLiveData<>();

    private Context context;
    private final FirebaseFirestore db;
    private List<Food> foods;
    private List<Restro> restroList;
    private List<Cart> cartList;
    private List<Order> orderList;
    private MutableLiveData<Boolean> processButtonClickable = new MutableLiveData<>();
    private MutableLiveData<Integer> processButtonColor = new MutableLiveData<>();


    public FoodViewModel(Context context){
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    public LiveData<List<Restro>> getRestaurant(){
        restroList = new ArrayList<>();
        db.collection(Constants.RESTAURANTS_COLLECTION).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d: docs){
                                Restro restro = d.toObject(Restro.class);
                                restroList.add(restro);
                            }
                            restaurantLiveData.setValue(restroList);
                        }else {
                            Log.d(Constants.TAG, "No data found in Database");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d(Constants.TAG, "Fail to get the data.");
                    }
                });
        return restaurantLiveData;
    }

    public LiveData<List<Food>> getFood(){
        foods = new ArrayList<>();
        db.collection(Constants.FOODS_COLLECTION).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d: docs){
                                Food food = d.toObject(Food.class);
                                foods.add(food);
                            }
                            foodMutableLiveData.setValue(foods);
                        }else {
                            Log.d(Constants.TAG, "No data found in Database");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Log.d(Constants.TAG, e.getMessage());
                        Log.d(Constants.TAG, "Fail to get the data.");
                    }
                });
        return foodMutableLiveData;
    }

    public LiveData<List<Cart>> getCartInfo(){
        cartList = new ArrayList<>();
        db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                .collection(Constants.CARTS_COLLECTION).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()){
                            List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d: docs){
                                Cart cart = d.toObject(Cart.class);
                                cartList.add(cart);
                            }
                            cartsMutableLiveData.setValue(cartList);
                        }else {
                            Log.d(Constants.TAG, "No data found in Database");
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

        return cartsMutableLiveData;
    }

    public LiveData<List<Order>> getOrders(){
        orderList = new ArrayList<>();
        db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                .collection(Constants.ORDERS_COLLECTION).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> docs = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d: docs){
                            Order order = d.toObject(Order.class);
                            orderList.add(order);
                        }

                        ordersMutableLiveData.setValue(orderList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return ordersMutableLiveData;
    }

    public void deleteItem(String orderId){
        db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                .collection(Constants.ORDERS_COLLECTION).document(orderId)
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Constants.TAG, "Delete Item is Failed " + e.getLocalizedMessage());
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Constants.TAG, "Delete item is success ");
                    }
                });
    }

}
