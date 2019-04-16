package com.eazydineapp.backend.ui.api;

import org.json.JSONArray;
import org.json.JSONObject;

public interface UIRestaurantService {

    void onSuccess(JSONObject jsonResponse);
    void onSuccess(JSONArray jsonArray);
    void onError(String message);
}
