package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.CartAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentCartBinding;
import com.prof.reda.android.project.fooddelivery.models.Menu;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private FragmentCartBinding binding;
    private List<Menu> menuList = new ArrayList<>();
    private CartAdapter cartAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);

        menuList.add(new Menu(R.drawable.orderimg2, "Spacy fresh crab", "Waroenk kita", 35));
        menuList.add(new Menu(R.drawable.orderimg1, "Spacy fresh crab", "Waroenk kita", 35));
        menuList.add(new Menu(R.drawable.orderimg3, "Spacy fresh crab", "Waroenk kita", 35));
        menuList.add(new Menu(R.drawable.menu1, "Spacy fresh crab", "Waroenk kita", 35));

        prepareRecyclerView(menuList);

        binding.checkoutBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_cartFragment_to_orderDetailsFragment);
        });

        return binding.getRoot();
    }

    private void prepareRecyclerView(List<Menu> menus){
        binding.ordersRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.ordersRv.setHasFixedSize(true);
        binding.ordersRv.setItemAnimator(new DefaultItemAnimator());
        cartAdapter = new CartAdapter(menus);
        binding.ordersRv.setAdapter(cartAdapter);
    }
}