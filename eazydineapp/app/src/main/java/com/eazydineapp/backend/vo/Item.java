package com.eazydineapp.backend.vo;

import java.io.Serializable;

/**
 * Created by ravisha on 4/26/18.
 */

public class Item implements Serializable {
    private Long id;
    private String name;
    private String description;
    private float price;
    private int serves;
    private String imagepath;
    private Category category;
    private Menu menu;


    public Menu getMenu() { return menu; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }

    public int getServes() {
        return serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }
}
