package com.prof.reda.android.project.fooddelivery.ui.fragments.home;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prof.reda.android.project.fooddelivery.R;
import com.prof.reda.android.project.fooddelivery.adapters.OrderAdapter;
import com.prof.reda.android.project.fooddelivery.databinding.FragmentOrderDetailsBinding;
import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.utils.Constants;
import com.prof.reda.android.project.fooddelivery.utils.PriceOrderSelectedListener;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModel;
import com.prof.reda.android.project.fooddelivery.viewModel.FoodViewModelFactory;

import java.util.List;

public class OrderDetailsFragment extends Fragment implements PriceOrderSelectedListener{

    private FragmentOrderDetailsBinding binding;
    private OrderAdapter orderAdapter;

    private int subTotal = 0;
//    private int price;
    private FoodViewModel viewModel;
    private Bitmap icon;
    private List<Order> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_details, container, false);

        FoodViewModelFactory factory = new FoodViewModelFactory(getActivity());
        viewModel = new ViewModelProvider(this, factory).get(FoodViewModel.class);

        setItemtouchCallback();
        viewModel.getOrders().observe(getViewLifecycleOwner(), new Observer<List<Order>>() {
            @Override
            public void onChanged(List<Order> orders) {
                orderList = orders;
                prepareOrderRV(orders);
            }
        });

        binding.placeMyOrderBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_orderDetailsFragment_to_fragmentPayments);
        });

        binding.arrowBackBtn.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_orderDetailsFragment_to_cartFragment);
        });
        binding.subTotalPriceTv.setText(String.valueOf(subTotal));

        return binding.getRoot();
    }

    @Override
    public void orderPrice(int totalPrice) {
        Log.d(Constants.TAG, "totalPrice = " + totalPrice);

    }

    private int convertDpToPx(int dp){
        return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void prepareOrderRV(List<Order> orders) {
        binding.orderDetailsRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));

        binding.orderDetailsRv.setHasFixedSize(true);
        binding.orderDetailsRv.setItemAnimator(new DefaultItemAnimator());
        orderAdapter = new OrderAdapter(getActivity(), orders, this);
        binding.orderDetailsRv.setAdapter(orderAdapter);
    }
    private void setItemtouchCallback(){
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Order order = orderList.remove(viewHolder.getAdapterPosition());
                viewModel.deleteItem(order.getOrderId());
//                orderList.remove(viewHolder.getAdapterPosition());
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
    }
}