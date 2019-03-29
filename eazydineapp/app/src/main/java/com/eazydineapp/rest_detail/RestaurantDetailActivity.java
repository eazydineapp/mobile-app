package com.eazydineapp.rest_detail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.activity.CartActivity;
import com.eazydineapp.model.Restaurant;
import com.eazydineapp.adapter.ViewPagerStateAdapter;

public class RestaurantDetailActivity extends AppCompatActivity {
    private static String EXTRA_REST_NAME = "restaurant_name";

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private String title;
    private TextView cartNotificationCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        initUi();
        setupViewPager();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rest_detail, menu);
        View cartActionView = menu.findItem(R.id.action_cart).getActionView();
        cartNotificationCount = ((TextView) cartActionView.findViewById(R.id.notification_count));
        cartActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
        setCartCount();
        return super.onCreateOptionsMenu(menu);
    }

    private void setCartCount() {
        int NOTIFICATION_COUNT = 1;
        if (cartNotificationCount != null) {
            if (NOTIFICATION_COUNT <= 0) {
                cartNotificationCount.setVisibility(View.GONE);
            } else {
                cartNotificationCount.setVisibility(View.VISIBLE);
                cartNotificationCount.setText(String.valueOf(NOTIFICATION_COUNT));
            }
        }
    }

    private void setupViewPager() {
        ViewPagerStateAdapter adapter = new ViewPagerStateAdapter(getSupportFragmentManager());
        adapter.addFrag(new CuisineFragment(), "Cuisine");
        adapter.addFrag(new ReviewFragment(), "Review");
        adapter.addFrag(new InfoFragment(), "Info");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initUi() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        appBarLayout = findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);

        title = getIntent().getStringExtra(EXTRA_REST_NAME);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        collapsingToolbarLayout.setTitle(" ");
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //userDetailsSummaryContainer.setVisibility(View.INVISIBLE);
                    collapsingToolbarLayout.setTitle(title);
                    isShow = true;
                } else if (isShow) {
                    //userDetailsSummaryContainer.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    public static Intent newIntent(Context context, Restaurant restaurant) {
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra(EXTRA_REST_NAME, restaurant.getName());
        return intent;
    }
}
