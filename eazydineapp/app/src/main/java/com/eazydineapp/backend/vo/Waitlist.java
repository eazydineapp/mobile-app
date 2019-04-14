package com.eazydineapp.backend.vo;

public class Waitlist {
    private String userId;
    private String restaurantId;
    private Long tableId;
    private WaitStatus status;

    public Waitlist() {}

    public Waitlist(String userId, String restaurantId, Long tableId, WaitStatus status) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
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
