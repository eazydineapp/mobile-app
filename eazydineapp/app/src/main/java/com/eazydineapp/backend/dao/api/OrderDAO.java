package com.eazydineapp.backend.dao.api;


import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.vo.Order;

public interface OrderDAO {
    void add(Order order) throws ItemException;
    void readByCook(String cookId, final UIOrderService uiOrderService) throws ItemException;
    void readByUser(String userID, final UIOrderService UIOrderService) throws ItemException;
    void delete(String id) throws Exception;
    void update(Order order) throws ItemException;
}
