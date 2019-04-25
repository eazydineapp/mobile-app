package com.eazydineapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.eazydineapp.R;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.backend.vo.CartItem;
import com.eazydineapp.model.RestaurantMenu;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Shriaithal
 * Display Menu Details, create cart object
 */
public class FoodDetailActivity extends AppCompatActivity {
    private static String EXTRA_REST_MENU = "restaurant_menu";

    private RestaurantMenu restaurantMenu;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView cartNotificationCount, itemQuantity;
    private ImageView itemQtyPlus, itemQtyMinus;
    private Toolbar toolbar;
    private Button button;
    private int cartItemCount;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restaurantMenu = getIntent().getParcelableExtra(EXTRA_REST_MENU);

        AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        userId = storagePrefUtil.getRegisteredUser(this);
        storagePrefUtil.getValue(this, "RESTAURANT_ID");

        initUi();
        initItemQtyButtons();

        button = findViewById(R.id.addToCart);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (count > 0) --count;
                itemQuantity.setText(count.toString());
            }
        });
    }

    private void addItemToCart() {
        ArrayList<CartItem> cartItems = new ArrayList<>();

        Integer quantity = Integer.parseInt(itemQuantity.getText().toString());
        String instr = ((EditText)findViewById(R.id.foodInstruction)).getText().toString();

        CartItem cartItem = new CartItem(restaurantMenu.getName(), restaurantMenu.getCategory(), restaurantMenu.getPrice(), quantity, restaurantMenu.getImagePath(), restaurantMenu.getId(), instr);
        cartItems.add(cartItem);

        Order order = new Order("order Id to be generated", OrderStatus.Cart, Calendar.getInstance().getTime().toString(), cartItem.getPriceTotal(), false,
                userId, restaurantMenu.getRestaurantId(), restaurantMenu.getRestaurantName(), restaurantMenu.getRestaurantAddress(), cartItems);

        OrderService orderService = new OrderServiceImpl();
        orderService.addToCart(order);
        cartItemCount++;
    }

    private void setItemDetailView() {
        TextView nameView = findViewById(R.id.foodName);
        nameView.setText(restaurantMenu.getName());

        TextView priceView = findViewById(R.id.foodPrice);
        priceView.setText(String.valueOf(restaurantMenu.getPrice()));

        TextView descriptionView = findViewById(R.id.foodDesc);
        descriptionView.setText(restaurantMenu.getDescription());

        ImageView imageView = findViewById(R.id.food_dtl_img);
        Glide.with(getApplicationContext()).load(restaurantMenu.getImagePath()).into(imageView);
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
        OrderService orderService = new OrderServiceImpl();
        orderService.getCartByUser(userId, new UIOrderService() {
            @Override
            public void displayAllOrders(List<Order> orders) {
            }

            @Override
            public void displayOrder(Order dbOrder) {
                int NOTIFICATION_COUNT = 0;
                if(null != dbOrder && dbOrder.getRestaurantId().equals(restaurantMenu.getRestaurantId())) {
                    NOTIFICATION_COUNT = cartItemCount = dbOrder.getItemList().size();
                }
                if (cartNotificationCount != null) {
                    if (NOTIFICATION_COUNT <= 0) {
                        cartNotificationCount.setVisibility(View.GONE);
                    } else {
                        cartNotificationCount.setVisibility(View.VISIBLE);
                        cartNotificationCount.setText(String.valueOf(NOTIFICATION_COUNT));
                    }
                }
            }
        });
    }

    public static Intent newIntent(Context context, RestaurantMenu restaurantMenu) {
        Intent intent = new Intent(context, FoodDetailActivity.class);
        intent.putExtra(EXTRA_REST_MENU, restaurantMenu);
        return intent;
    }
}
