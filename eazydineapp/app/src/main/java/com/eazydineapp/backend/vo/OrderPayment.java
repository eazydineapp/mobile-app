package com.eazydineapp.backend.vo;

import java.io.Serializable;

public class OrderPayment implements Serializable {

    float total;
    float subTotal;
    float tax;
    float serviceCharge;

    public OrderPayment(float total, float subTotal, float tax, float serviceCharge) {
        this.total = total;
        this.subTotal = subTotal;
        this.tax = tax;
        this.serviceCharge = serviceCharge;
    }

    public float getTotal() {
        return total;
    }

    public float getSubTotal() {
        return subTotal;
    }

    public float getTax() {
        return tax;
    }

    public float getServiceCharge() {
        return serviceCharge;
    }
}
