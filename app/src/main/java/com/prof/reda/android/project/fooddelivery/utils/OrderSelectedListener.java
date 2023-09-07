package com.prof.reda.android.project.fooddelivery.utils;

import com.prof.reda.android.project.fooddelivery.models.Order;
import com.prof.reda.android.project.fooddelivery.models.OrderEntity;

public interface OrderSelectedListener {
    void orderItem(Order order, int quantity);
}
