package com.eazydineapp.backend.ui.api;

import org.json.JSONObject;

public interface UIRestaurantService {

    void onSuccess(JSONObject jsonResponse);
    void onError(String message);
}
