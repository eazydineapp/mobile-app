package com.eazydineapp.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazydineapp.R;
import com.eazydineapp.adapter.FoodCategoryAdapter;
import com.eazydineapp.adapter.RestaurantAdapter;

public class RestaurantActivity extends AppCompatActivity {
    private RecyclerView recyclerRestaurants;

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
    }


    private void setupRecyclerRestaurants() {
        recyclerRestaurants.setNestedScrollingEnabled(false);
        recyclerRestaurants.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerRestaurants.setAdapter(new RestaurantAdapter(getApplicationContext()));
    }
}
