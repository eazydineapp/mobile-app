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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.model.CartItem;
import com.eazydineapp.model.RestaurantMenu;

import java.util.ArrayList;
import java.util.Calendar;

public class FoodDetailActivity extends AppCompatActivity {
    private static String EXTRA_REST_MENU = "restaurant_menu";

    private CartItem cartItem;
    private RestaurantMenu restaurantMenu;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView cartNotificationCount, itemQuantity;
    private ImageView itemQtyPlus, itemQtyMinus;
    private Toolbar toolbar;
    private Button button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restaurantMenu = getIntent().getParcelableExtra(EXTRA_REST_MENU);
        initUi();
        initItemQtyButtons();

        button = findViewById(R.id.addToCart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Add to cart clicked ######################################################################################");
                addItemToCart();
            }
        });

    }

    private void initItemQtyButtons() {
        itemQuantity = findViewById(R.id.itemQuantity);

        itemQtyPlus = findViewById(R.id.itemQuantityPlus);
        itemQtyPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer count = Integer.parseInt(itemQuantity.getText().toString());
                ++count;
                itemQuantity.setText(count.toString());
            }
        });

        itemQtyMinus = findViewById(R.id.itemQuantityMinus);
        itemQtyMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer count = Integer.parseInt(itemQuantity.getText().toString());
                if(count > 0) --count;
                itemQuantity.setText(count.toString());
            }
        });
    }

    private void addItemToCart() {
        ArrayList<CartItem> cartItems = new ArrayList<>();

        cartItem.setQuantity(Integer.parseInt(itemQuantity.getText().toString()));
        cartItems.add(cartItem);

        Order order = new Order("order Id to be generated", OrderStatus.Cart, Calendar.getInstance().getTime().toString(), cartItem.getPriceTotal(), true,
                "Anu", "1","1", "Peacock Indian Cuisine", "Fremont, CA", cartItems);

        OrderService orderService = new OrderServiceImpl();
        orderService.addToCart(order);
    }

    private void setItemDetailView() {
        cartItem = new CartItem("Paneer Kurchan", "Side", 3, 1, "", "1");

        TextView nameView = findViewById(R.id.foodName);
        nameView.setText(cartItem.getName());

        TextView priceView = findViewById(R.id.foodPrice);
        priceView.setText(String.valueOf(cartItem.getPrice()));
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
        setItemDetailView();
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
