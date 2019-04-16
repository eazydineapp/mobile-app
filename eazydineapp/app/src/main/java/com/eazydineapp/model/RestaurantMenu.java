package com.eazydineapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by a_man on 24-01-2018.
 */

public class RestaurantMenu implements Parcelable{
    private String name, description, id, category, imagePath, restaurantId, restaurantName, restaurantAddress;
    private boolean added;
    private float price;

    public RestaurantMenu(String id, String name, String description, String category, float price,String imagePath, String restaurantId, String restaurantName, String restaurantAddress) {
        this.id =  id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imagePath=imagePath;
        this.restaurantId = restaurantId;
        this.restaurantName =  restaurantName;
        this.restaurantAddress = restaurantAddress;
    }

    protected RestaurantMenu(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        category = in.readString();
        added = in.readByte() != 0;
        price = in.readFloat();
        imagePath = in.readString();
        restaurantId = in.readString();
        restaurantName = in.readString();
        restaurantAddress = in.readString();
    }

    public static final Creator<RestaurantMenu> CREATOR = new Creator<RestaurantMenu>() {
        @Override
        public RestaurantMenu createFromParcel(Parcel in) {
            return new RestaurantMenu(in);
        }

        @Override
        public RestaurantMenu[] newArray(int size) {
            return new RestaurantMenu[size];
        }
    };

    public String getImagePath() {
        return imagePath;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isAdded() {
        return added;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() { return category; }

    public String getId() { return id; }

    public String getRestaurantId() { return restaurantId; }

    public String getRestaurantName() { return restaurantName; }

    public String getRestaurantAddress() { return restaurantAddress; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(category);
        parcel.writeByte((byte) (added ? 1 : 0));
        parcel.writeFloat(price);
        parcel.writeString(imagePath);
        parcel.writeString(restaurantId);
        parcel.writeString(restaurantName);
        parcel.writeString(restaurantAddress);
    }
}
