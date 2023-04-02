package com.prof.reda.android.project.fooddelivery.views.fragments.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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

    private Bitmap icon;
    private ColorDrawable background;

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

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


                icon = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.trashicon);

//                background = new ColorDrawable(Color.parseColor("#FEAD1D"));
                View itemView = viewHolder.itemView;
//
//                int top = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
//                int left = itemView.getWidth() - icon.getIntrinsicWidth() -
//                        (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
//                int right = itemView.getLeft() + icon.getIntrinsicHeight();
//                int bottom = itemView.getTop() + icon.getIntrinsicHeight();
//
//                if (dX < 0){
//                    background.setBounds(itemView.getRight() + (int)dX,
//                            itemView.getTop(), itemView.getRight(), itemView.getBottom());
//                    icon.setBounds(left, top, right, bottom);
//                } else if (dX > 0) {
//                    background.setBounds(itemView.getLeft() + (int)dX,
//                            itemView.getTop(), itemView.getLeft(), itemView.getBottom());
//                    icon.setBounds(left, top, right, bottom);
//                }
//
//                background.draw(c);
//                icon.draw(c);

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