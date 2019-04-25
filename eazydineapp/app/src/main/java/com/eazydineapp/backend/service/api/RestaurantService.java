package com.eazydineapp.backend.service.api;

import android.content.Context;

import com.eazydineapp.backend.ui.api.UIRestaurantService;

public interface RestaurantService {

    void getAllRestaurants(Context context, UIRestaurantService uiRestaurantService);
    void getRestaurantById(Context context, Long restaurantId, UIRestaurantService uiRestaurantService);
    void getCategoriesByRestaurantId(Context context, Long restaurantId, UIRestaurantService uiRestaurantService);
    void getMenuByRestaurantId(Context context, Long restaurantId, UIRestaurantService uiRestaurantService);
    void searchRestaurantByZipCode(Context context, int zipCode, UIRestaurantService uiRestaurantService);
    void searchRestaurant(Context context, String searchText, UIRestaurantService uiRestaurantService);
}
