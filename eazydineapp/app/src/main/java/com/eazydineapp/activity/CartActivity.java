package com.eazydineapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.adapter.CartAdapter;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.api.WaitlistService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.service.impl.WaitlistServiceImpl;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.ui.api.UIWaitlistService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.vo.CartItem;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.backend.vo.WaitStatus;
import com.eazydineapp.backend.vo.Waitlist;
import com.eazydineapp.fragment.OrdersFragment;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Shriaithal
 * Place Order + Make Payment Screen
 * Load cart value, create order
 */

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecycler;
    CartAdapter cartAdapter;
    private ArrayList<CartItem> cartItems;
    private Order order;
    private TextView tv,orderPlaceName, orderPlaceAddress, orderTotal, serviceCharge, subTotal, tax, orderDate;
    private String userId, restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = findViewById(R.id.toolbarCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        cartRecycler = findViewById(R.id.cartRecycler);

        AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        userId = storagePrefUtil.getRegisteredUser(this);
        restaurantId = storagePrefUtil.getValue(this, "RESTAURANT_ID");

        loadCartValue();
        setupCartRecycler();
        tv = (TextView)findViewById(R.id.checkoutText);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("Place Order".equals(tv.getText())) {
                    placeOrder();
                }else {
                   /* Intent newIntent = new Intent(getApplicationContext(), RestaurantActivity.class);
                    newIntent.putExtra("eazydine-restaurantId", restaurantId);
                    startActivity(newIntent);*/
                   onBackPressed();
                }
            }
        });
    }

    private void placeOrder() {
        if(null != order) {
            WaitlistService waitlistService = new WaitlistServiceImpl();
            waitlistService.getWaitStatus(order.getRestaurantId(), order.getUserId(), new UIWaitlistService() {
                @Override
                public void displayWaitStatus(Waitlist user) {
                    if(null != user && WaitStatus.Assigned.equals(user.getStatus())) {
                        order.setOrderStatus(OrderStatus.Placed);
                        createOrder();
                    }else {
                        displayPreOrderDialog();
                    }
                }
            });
        }
    }

    private void createOrder() {
        OrderService orderService = new OrderServiceImpl();
        orderService.updateOrder(order);
       // loadOrdersFragment();

       // tv.setText("Continue To Order");
        //tv.setGravity(Gravity.CENTER);
        startActivity(new Intent(this, RestaurantActivity.class));
    }
    private void displayPreOrderDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setMessage("Thank your for letting  us know your order, Please wait to be seated");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        order.setOrderStatus(OrderStatus.PreOrder);
                        createOrder();
                        dialog.cancel();
                    }
                });

        builder.setNegativeButton(
                "Cancel Order",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();

    }

    private void setupCartRecycler() {
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, this.cartItems);
        cartRecycler.setAdapter(cartAdapter);
    }

    private void loadCartValue() {
        order = new Order();
        cartItems = new ArrayList<>();

        OrderService orderService = new OrderServiceImpl();
        orderService.getCartByUser(userId, new UIOrderService() {
            @Override
            public void displayAllOrders(List<Order> orders) {
            }

            @Override
            public void displayOrder(Order dbOrder) {
                order = dbOrder;
                if(null != dbOrder) {
                    orderPlaceName = findViewById(R.id.orderPlaceName);
                    orderPlaceName.setText(dbOrder.getRestaurantName());

                    orderPlaceAddress  = findViewById(R.id.orderPlaceAddress);
                    orderPlaceAddress.setText(dbOrder.getRestaurantAddress());

                    orderTotal  = findViewById(R.id.orderTotal);
                    orderTotal.setText("$"+String.valueOf(dbOrder.getTotalPrice()));

                    Date date = new Date(dbOrder.getOrderDate());
                    orderDate = findViewById(R.id.orderDate);
                    orderDate.setText(String.valueOf(date.getDate()) +" "+ new DateFormatSymbols().getMonths()[date.getMonth()]);

                    cartItems.addAll(dbOrder.getItemList());
                    cartAdapter.setCartItems(cartItems);
                }
            }
        });
    }

    private float round(float d) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
}
