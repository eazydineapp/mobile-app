package com.eazydineapp.backend.service.api;


import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.ui.api.UIWaitlistService;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.Waitlist;

public interface OrderService {
    void addToCart(Order order);
    void getOrderByUser(String userId, UIOrderService uiOrderService);
    void getOrderByUserAndRestaurant(String userId, String restaurantId, UIOrderService uiOrderService);
    void getCartByUser(String userId, UIOrderService uiOrderService);
    void updateOrder(Order order);
    void updateOrderByUserAndRestaurant(String userId, String restaurantId);
    void removeOrder(Order order);
}
