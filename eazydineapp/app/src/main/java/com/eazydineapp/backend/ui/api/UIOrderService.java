package com.eazydineapp.backend.ui.api;



import com.eazydineapp.backend.vo.Order;

import java.util.List;

public interface UIOrderService {
    void displayAllOrders(List<Order> orders);
    void displayOrder(Order order);
}
