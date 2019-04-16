package com.eazydineapp.backend.service.impl;

import android.content.Context;

import com.eazydineapp.backend.exception.ItemException;
import com.eazydineapp.backend.service.api.RestaurantService;
import com.eazydineapp.backend.ui.api.UIRestaurantService;
import com.eazydineapp.backend.util.RestAPIUtil;

public class RestaurantServiceImpl implements RestaurantService {

    RestAPIUtil restAPIUtil = new RestAPIUtil();

    @Override
    public void getAllRestaurants(Context context, UIRestaurantService uiRestaurantService) {
        try {
            String uri = "/api/restaurants/";
            restAPIUtil.executeGetListAPI(context, uri, uiRestaurantService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getRestaurantById(Context context, Long restaurantId, UIRestaurantService uiRestaurantService) {
        try {
            String uri = "/api/restaurants/" + restaurantId;
            restAPIUtil.executeGetAPI(context, uri, uiRestaurantService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getCategoriesByRestaurantId(Context context, Long restaurantId, UIRestaurantService uiRestaurantService) {
        try {
            String uri = "/api/categories/?restaurantId="+restaurantId;
            restAPIUtil.executeGetListAPI(context, uri, uiRestaurantService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMenuByRestaurantId(Context context, Long restaurantId, UIRestaurantService uiRestaurantService) {
        try {
            String uri = "/api/menus/?restaurantId="+restaurantId;
            restAPIUtil.executeGetListAPI(context, uri, uiRestaurantService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
