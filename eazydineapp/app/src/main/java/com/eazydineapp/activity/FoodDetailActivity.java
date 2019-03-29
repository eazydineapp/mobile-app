package com.eazydineapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.model.RestaurantMenu;

public class FoodDetailActivity extends AppCompatActivity {
    private static String EXTRA_REST_MENU = "restaurant_menu";

    private RestaurantMenu restaurantMenu;

    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView cartNotificationCount;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restaurantMenu = getIntent().getParcelableExtra(EXTRA_REST_MENU);
        initUi();

    }

    private void initUi() {
        appBarLayout = findViewById(R.id.appBarLayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);

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

        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.BLACK);
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
                    collapsingToolbarLayout.setTitle(restaurantMenu.getName());
                    isShow = true;
                } else if (isShow) {
                    //userDetailsSummaryContainer.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_home, menu);
        View cartActionView = menu.findItem(R.id.action_cart).getActionView();
        cartNotificationCount = ((TextView) cartActionView.findViewById(R.id.notification_count));
        cartActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
        setCartCount();
        super.onCreateOptionsMenu(menu);
        return true;
    }

    private void setCartCount() {
        int NOTIFICATION_COUNT = 10;
        if (cartNotificationCount != null) {
            if (NOTIFICATION_COUNT <= 0) {
                cartNotificationCount.setVisibility(View.GONE);
            } else {
                cartNotificationCount.setVisibility(View.VISIBLE);
                cartNotificationCount.setText(String.valueOf(NOTIFICATION_COUNT));
            }
        }
    }

    public static Intent newIntent(Context context, RestaurantMenu restaurantMenu) {
        Intent intent = new Intent(context, FoodDetailActivity.class);
        intent.putExtra(EXTRA_REST_MENU, restaurantMenu);
        return intent;
    }
}
