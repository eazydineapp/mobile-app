package com.eazydineapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.eazydineapp.R;
import com.eazydineapp.adapter.ViewPagerStateAdapter;
import com.eazydineapp.fragment.RestaurantMenuFragment;
import com.eazydineapp.model.Restaurant;
import com.google.android.material.tabs.TabLayout;

public class RestaurantMenuActivity extends AppCompatActivity {

    private static String EXTRA_REST_NAME = "restaurant_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra(EXTRA_REST_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);

        ViewPagerStateAdapter adapter = new ViewPagerStateAdapter(getSupportFragmentManager());
        adapter.addFrag(new RestaurantMenuFragment(), "Popular");
        adapter.addFrag(new RestaurantMenuFragment(), "Main course");
        adapter.addFrag(new RestaurantMenuFragment(), "Appetizer");
        adapter.addFrag(new RestaurantMenuFragment(), "Breakfast");
        adapter.addFrag(new RestaurantMenuFragment(), "Dessert");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    public static Intent newIntent(Context context, Restaurant restaurant) {
        Intent intent = new Intent(context, RestaurantMenuActivity.class);
        intent.putExtra(EXTRA_REST_NAME, restaurant.getName());
        return intent;
    }
}
