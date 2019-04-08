package com.eazydineapp.backend.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eazydineapp.backend.ui.api.UIRestaurantService;

import org.json.JSONObject;

public class RestAPIUtil {

    private final String BASE_URL = "http://54.177.122.80:8080";//"http://192.168.1.109:8080"; //"http://192.168.0.33:8080";//"http://18.221.192.106:8080";

    public void executePostAPI(Context context, String uri, JSONObject jsonObject, final UIRestaurantService callback) {
        APIRequestQueue queue =  APIRequestQueue.getInstance(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL + uri, jsonObject, new Response.Listener<JSONObject> () {

            @Override
            public void onResponse(JSONObject jsonResponse) {
                callback.onSuccess(jsonResponse);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("RestApiClient", "Error: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
        queue.getRequestQueue().add(jsonObjectRequest);
    }

    public void executeGetAPI(Context context, String uri, final UIRestaurantService callback) {
        APIRequestQueue queue =  APIRequestQueue.getInstance(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,  BASE_URL + uri, null, new Response.Listener<JSONObject> () {

            @Override
            public void onResponse(JSONObject jsonResponse) {
                callback.onSuccess(jsonResponse);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("RestApiClient", "Error: " + error.getMessage());
                callback.onError(error.getMessage());
            }
        });
        queue.getRequestQueue().add(jsonObjectRequest);
    }
}
