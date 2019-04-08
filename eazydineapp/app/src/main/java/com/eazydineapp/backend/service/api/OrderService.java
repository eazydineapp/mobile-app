package com.eazydineapp.backend.service.api;


import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.vo.Order;

public interface OrderService {
    void addToCart(Order order);
    void getOrderByUser(String userId, UIOrderService uiOrderService);
    void getOrderByUserAndRestaurant(String userId, String restaurantId, UIOrderService uiOrderService);
    void getCartByUser(String userId, UIOrderService uiOrderService);
    void updateOrder(Order order);

}
