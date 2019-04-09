package com.eazydineapp.backend.vo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Restaurant implements Serializable {

    private Long id;

    private String name;
    private String tagline;

    private Boolean allowpreorder;
    private  Long avgprice;
    private String firebaseId;
    private int numoftables;
    private String restaurantbgimage;
    private String phonenumber;

    private int zipcode;
    private String country;

    private String address1;
    private String address2;
    private String city;
    private String state;

    private String uuid;

    private String cuisine;


    private Set<Menu> menus = new HashSet<>();

    private Set<Category> categories = new HashSet<>();


    private Set<Table> tables = new HashSet<>();

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getNumoftables() {
        return numoftables;
    }

    public void setNumoftables(int numoftables) {
        this.numoftables = numoftables;
    }

    public Boolean getAllowpreorder() {
        return allowpreorder;
    }

    public void setAllowpreorder(Boolean allowpreorder) {
        this.allowpreorder = allowpreorder;
    }

    public Long getAvgprice() {
        return avgprice;
    }

    public void setAvgprice(Long avgprice) {
        this.avgprice = avgprice;
    }


    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


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

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getCuisine() { return cuisine; }

    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    public Set<Table> getTables() {
        return tables;
    }

    public void setTables(Set<Table> tables) {
        this.tables = tables;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getRestaurantbgimage() {
        return restaurantbgimage;
    }

    public void setRestaurantbgimage(String restaurantbgimage) {
        this.restaurantbgimage = restaurantbgimage;
    }
}
