package com.eazydineapp.backend.vo;

public class Waitlist {
    private String userId;
    private String restaurantId;
    private Long tableId;
    private WaitStatus status;
    private Integer numSeats;
    private String userName;

    public Waitlist() {}

    public Waitlist(String userId, String restaurantId, String userName, Long tableId, Integer numSeats, WaitStatus status) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.userName = userName;
        this.numSeats = numSeats;
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

    public Integer getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(Integer numSeats) {
        this.numSeats = numSeats;
    }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }
}
