package com.eazydineapp.backend.vo;

import com.eazydineapp.backend.util.AppUtil;

import java.io.Serializable;
import java.text.DecimalFormat;

import static java.lang.Math.round;

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
    private String instr;

    public CartItem(String ginger_chicken_curry, int i, int i1, int rest_res_1){}

    public CartItem(String name, String category, float price, int quantity, String photoPath, String itemId, String instr) {
        this.name = name;
        this.category = category;
        this.price = AppUtil.round(price);
        this.quantity = quantity;
        this.priceTotal = AppUtil.round(this.price * this.quantity);
        this.photoPath = photoPath;
        this.itemId = itemId;
        this.instr = instr;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.priceTotal = AppUtil.round(this.price * this.quantity);
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

    public String getInstr() {
        return instr;
    }

    public void setInstr(String instr) {
        this.instr = instr;
    }
}
