package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.OrderAdapter;
import com.prof.reda.android.project.fooddelivery.database.FoodDatabase;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentOrderDetailsBinding;
import com.prof.reda.android.project.fooddelivery.models.EntityOrder;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModel;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModelFactory;

import java.util.List;

public class OrderDetailsFragment extends Fragment {

    private FragmentOrderDetailsBinding binding;
    private OrderAdapter orderAdapter;

    private int priceTotal = 0;
    private int mQuantity = 0;
    private int mPrice = 0;
    private FoodViewModel viewModel;
    private FoodDatabase mDB;

    private SharedPreferences sharedPreferences;
    private String token;
    private Bitmap icon;

    private List<Order> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);

        sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        token = sharedPreferences.getString("token", null);

        mDB = FoodDatabase.getInstance(getContext());

        FoodViewModelFactory factory = new FoodViewModelFactory(mDB);

        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        viewModel.getOrderDetails(getContext(), token).observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                orderList = orders;
                prepareOrderRV(orders);
            }
        });


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

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.trashicon);

                View itemView = viewHolder.itemView;

                Paint p = new Paint();
                p.setColor(Color.parseColor("#FEAD1D"));
                if (dX > 0) {
                    /* Set your color for positive displacement */

                    // Draw Rect with varying right side, equal to displacement dX
                    c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                            (float) itemView.getBottom(), p);

                } else {
                    /* Set your color for negative displacement */

                    // Draw Rect with varying left side, equal to the item's right side plus negative displacement dX
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                            (float) itemView.getRight(), (float) itemView.getBottom(), p);
                    //Set the image icon for Left swipe
                    c.drawBitmap(icon,
                            (float) itemView.getRight() - convertDpToPx(16) - icon.getWidth(),
                            (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight())/2,
                            p);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.orderDetailsRv);

        binding.placeMyOrderBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_orderDetailsFragment_to_fragmentPayments);
        });



//        binding.subTotalPriceTv.setText(String.valueOf(mPrice));
//        binding.subTotalPriceTv.append(" $");
//
//        int total = mPrice + 10-20;
//        binding.totalPriceTv.setText(String.valueOf(total));
//        binding.totalPriceTv.append(" $");

        binding.arrowBackBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_orderDetailsFragment_to_cartFragment);
        });

        return binding.getRoot();
    }
    private int convertDpToPx(int dp){
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
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