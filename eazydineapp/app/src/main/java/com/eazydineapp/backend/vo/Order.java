package com.eazydineapp.backend.vo;

import com.eazydineapp.model.CartItem;

import java.util.List;

public class Order {
    private String orderId;
    private OrderStatus orderStatus;
    private String orderDate;
    private Double totalPrice;
    private boolean isPaid;
    private String userName;
    private String userId;
    private String restaurantName;
    private String restaurantAddress;
    private List<CartItem> itemList;

    public Order() {

    }

    public Order(String orderId, OrderStatus orderStatus, String orderDate, Double totalPrice, boolean isPaid, String userName, String userId, String restaurantName, String restaurantAddress, List<CartItem> itemList) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.isPaid = isPaid;
        this.userName = userName;
        this.userId = userId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.itemList = itemList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantName() { return restaurantName; }

    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getRestaurantAddress() { return restaurantAddress; }

    public void setRestaurantAddress(String restaurantAddress) { this.restaurantAddress = restaurantAddress; }

    public List<CartItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<CartItem> itemList) {
        this.itemList = itemList;
    }
}
