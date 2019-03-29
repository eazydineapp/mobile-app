package com.eazydineapp.backend.service.impl;


import com.eazydineapp.backend.dao.api.OrderDAO;
import com.eazydineapp.backend.dao.impl.OrderDAOImpl;
import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.vo.Order;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public void add(Order order) {
        try {
            orderDAO.add(order);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try {
            orderDAO.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readByCook(String cookId, UIOrderService uiOrderService) {
        try {
            orderDAO.readByCook(cookId, uiOrderService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readByUser(String userId, UIOrderService uiOrderService) {
        try {
            orderDAO.readByUser(userId, uiOrderService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order order) {
        try {
            orderDAO.update(order);
        } catch (ItemException e) {
            e.printStackTrace();
        }

    }
}
