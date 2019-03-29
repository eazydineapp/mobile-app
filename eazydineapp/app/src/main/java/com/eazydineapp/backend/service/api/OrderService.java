package com.eazydineapp.backend.service.api;


import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.vo.Order;

public interface OrderService {
    void add(Order order);
    void delete(String id);
    void readByCook(String cookId, UIOrderService uiOrderService);
    void readByUser(String userId, UIOrderService uiOrderService);
    void update(Order order);

}
