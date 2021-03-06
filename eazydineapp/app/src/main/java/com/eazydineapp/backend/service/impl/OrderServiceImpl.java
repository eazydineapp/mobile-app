package com.eazydineapp.backend.service.impl;


import com.eazydineapp.backend.dao.api.OrderDAO;
import com.eazydineapp.backend.dao.impl.OrderDAOImpl;
import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.ui.api.UIWaitlistService;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.Waitlist;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    public void addToCart(Order order) {
        try {
            orderDAO.addToCart(order);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getOrderByUser(String userId, UIOrderService uiOrderService) {
        try {
            orderDAO.getOrderByUser(userId, uiOrderService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getOrderByUserAndRestaurant(String userId, String restaurantId, UIOrderService uiOrderService) {
        try {
            orderDAO.getOrderByUserAndRestaurantId(userId, restaurantId, uiOrderService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCartByUser(String userId, UIOrderService uiOrderService) {
        try {
            orderDAO.getCartByUser(userId, uiOrderService);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrder(Order order) {
        try {
            orderDAO.updateOrder(order);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrderByUserAndRestaurant(String userId, String restaurantId) {
        try {
            orderDAO.updateOrderByUserAndRestaurant(userId, restaurantId);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeOrder(Order order) {
        try {
            orderDAO.removeOrder(order);
        } catch (ItemException e) {
            e.printStackTrace();
        }
    }
}
