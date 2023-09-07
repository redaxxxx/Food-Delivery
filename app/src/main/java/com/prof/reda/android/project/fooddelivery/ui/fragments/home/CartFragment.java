package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.CartAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentCartBinding;
import com.prof.reda.android.project.fooddelivery.models.Cart;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.utils.Config;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.utils.OnProcessOrderItemListener;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModel;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModelFactory;

import java.util.List;

public class CartFragment extends Fragment implements OnProcessOrderItemListener{
    private FragmentCartBinding binding;
    private FoodViewModel viewModel;
    private FirebaseFirestore db;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);

        FoodViewModelFactory factory = new FoodViewModelFactory(getActivity());
        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        db = FirebaseFirestore.getInstance();
        viewModel.getCartInfo().observe(getViewLifecycleOwner(), new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> cartList) {
                prepareRecyclerView(cartList);
            }
        });

        binding.completeOrder.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_orderDetailsFragment);
        });

        return binding.getRoot();
    }

    private void prepareRecyclerView(List<Cart> cartList){
        binding.ordersRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.ordersRv.setHasFixedSize(true);
        binding.ordersRv.setItemAnimator(new DefaultItemAnimator());
        CartAdapter cartAdapter = new CartAdapter(getActivity(), cartList,this);
        binding.ordersRv.setAdapter(cartAdapter);
    }

    @Override
    public void processOrder(Cart cart) {
        Order order = new Order();
        order.setOrderId(cart.getCartId());
        order.setFoodName(cart.getFoodName());
        order.setRestroName(cart.getRestroName());
        order.setPrice(cart.getPrice());
        order.setFoodImage(cart.getFoodImage());

        db.collection(Constants.USERS_COLLECTION).document(Config.firebaseUSerID)
                .collection(Constants.ORDERS_COLLECTION).document(cart.getCartId()).set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(Constants.TAG, "Order is added");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(Constants.TAG, "Failed in add order: " + e.getLocalizedMessage());
                    }
                });
    }
}