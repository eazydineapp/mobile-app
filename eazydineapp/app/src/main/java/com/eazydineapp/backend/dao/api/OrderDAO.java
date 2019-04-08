package com.eazydineapp.backend.dao.api;


import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.vo.Order;

public interface OrderDAO {
    void addToCart(Order order) throws ItemException;
    void getOrderByUser(String userID, final UIOrderService UIOrderService) throws ItemException;
    void getCartByUser(String userID, final UIOrderService UIOrderService) throws ItemException;
    void updateOrder(Order order) throws ItemException;
}
