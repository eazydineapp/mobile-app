package com.eazydineapp.model;

import java.io.Serializable;

/**
 * Created by a_man on 24-01-2018.
 */

public class CartItem implements Serializable {
    private String name;
    private String category;
    private float price, priceTotal;
    private int quantity;
    private String photoPath;
    private String itemId;
    private String itemStatus;

    public CartItem(){}

    public CartItem(String name, String category, float price, int quantity, String photoPath, String itemId) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.photoPath = photoPath;
        this.itemId = itemId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.priceTotal = this.price * this.quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public float getPriceTotal() {
        return priceTotal;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }
}
