package com.eazydineapp.backend.vo;

public class Waitlist {
    private String userId;
    private Long restaurantId;
    private Long tableId;
    private WaitStatus status;

    public Waitlist() {}

    public Waitlist(String userId, Long restaurantId, WaitStatus status) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.tableId = tableId;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public WaitStatus getStatus() {
        return status;
    }

    public void setStatus(WaitStatus status) {
        this.status = status;
    }
}
