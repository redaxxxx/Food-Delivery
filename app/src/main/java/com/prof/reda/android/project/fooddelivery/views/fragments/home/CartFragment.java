package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.OrderAdapter;
import com.prof.reda.android.project.fooddelivery.adapters.PopularMenuAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentCartBinding;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.models.Restaurants;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);

        orderList.add(new Order(R.drawable.orderimg1, "Spacy fresh crab", "Waroenk kita", 35,1));
        orderList.add(new Order(R.drawable.orderimg2, "Spacy fresh crab", "Waroenk kita", 35,1));
        orderList.add(new Order(R.drawable.orderimg3, "Spacy fresh crab", "Waroenk kita", 35,1));

        prepareOrderRV(orderList);

        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                orderList.remove(viewHolder.getAdapterPosition());
                orderAdapter.notifyDataSetChanged();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.orderDetailsRv);

        return binding.getRoot();
    }

    private void prepareOrderRV(List<Order> orders) {
        binding.orderDetailsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.orderDetailsRv.setHasFixedSize(true);
        binding.orderDetailsRv.setItemAnimator(new DefaultItemAnimator());
        orderAdapter = new OrderAdapter(orders);
        binding.orderDetailsRv.setAdapter(orderAdapter);
    }
}