package com.eazydineapp.backend.vo;

public class User {
    String phoneNumber;
    UserStatus status;
    String activeRestaurantId;

    public User() {
    }

    public User(String phoneNumber, UserStatus status, String activeRestaurantId) {
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.activeRestaurantId = activeRestaurantId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getActiveRestaurantId() {
        return activeRestaurantId;
    }

    public void setActiveRestaurantId(String activeRestaurantId) {
        this.activeRestaurantId = activeRestaurantId;
    }
}
