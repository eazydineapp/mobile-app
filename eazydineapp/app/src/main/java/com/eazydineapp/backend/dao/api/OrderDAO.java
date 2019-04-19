package com.eazydineapp.backend.dao.api;


import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.Waitlist;

public interface OrderDAO {
    void addToCart(Order order) throws ItemException;
    void getOrderByUser(String userID, final UIOrderService UIOrderService) throws ItemException;
    void getCartByUser(String userID, final UIOrderService UIOrderService) throws ItemException;
    void getOrderByUserAndRestaurantId(String userId, final String restaurantId, final UIOrderService UIOrderService) throws ItemException;
    void updateOrder(Order order) throws ItemException;
    void updateOrderByUserAndRestaurant(String userId, String id) throws ItemException;
}
