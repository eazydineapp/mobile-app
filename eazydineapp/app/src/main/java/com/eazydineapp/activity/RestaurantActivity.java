package com.eazydineapp.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazydineapp.R;
import com.eazydineapp.adapter.FoodCategoryAdapter;
import com.eazydineapp.adapter.RestaurantAdapter;
import com.eazydineapp.backend.service.api.RestaurantService;
import com.eazydineapp.backend.service.impl.RestaurantServiceImpl;
import com.eazydineapp.backend.ui.api.UIRestaurantService;
import com.eazydineapp.backend.vo.Restaurant;
import com.eazydineapp.rest_detail.RestaurantDetailActivity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {

    private RestaurantService restaurantService = new RestaurantServiceImpl();
    private Gson gson = new Gson();
    private RecyclerView recyclerRestaurants;
    private RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        recyclerRestaurants = findViewById(R.id.recyclerRestaurants);
        setupRecyclerRestaurants();
        loadRestaurants();
    }


    private void setupRecyclerRestaurants() {
        recyclerRestaurants.setNestedScrollingEnabled(false);
        recyclerRestaurants.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        restaurantAdapter = new RestaurantAdapter(getApplicationContext());
        recyclerRestaurants.setAdapter(restaurantAdapter);
    }

    private void loadRestaurants() {
        String restaurantId = getIntent().getStringExtra("eazydine-restaurantId");
        if(null == restaurantId) {
            loadAllRestaurants();
        }else {
            loadRestaurantById(restaurantId);
        }
    }

    private void loadRestaurantById(String restaurantId) {
        restaurantService.getRestaurantById(getApplicationContext(), Long.parseLong(restaurantId), new UIRestaurantService() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                Restaurant restaurant = gson.fromJson(jsonResponse.toString(), Restaurant.class);
                startActivity(RestaurantDetailActivity.newIntent(getApplicationContext(), restaurant));
            }

            @Override
            public void onSuccess(JSONArray jsonArray) { }

            @Override
            public void onError(String message) {
                Log.e("Get Restauarnts", message);
            }
        });
    }

    private void loadAllRestaurants() {
        restaurantService.getAllRestaurants(getApplicationContext(), new UIRestaurantService() {
            @Override
            public void onSuccess(JSONObject jsonResponse) { }

            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    ArrayList<Restaurant> restaurants = new ArrayList<>();
                    for(int index=0; index < jsonArray.length(); index++) {
                        restaurants.add(gson.fromJson(jsonArray.getJSONObject(index).toString(), Restaurant.class));
                    }
                    restaurantAdapter.setRestaurants(restaurants);
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                Log.e("All Restauarnts", message);
            }
        });
    }
}
