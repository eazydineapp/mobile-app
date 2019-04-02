package com.eazydineapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.adapter.CartAdapter;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.checkout.CheckoutActivity;
import com.eazydineapp.fragment.OrdersFragment;
import com.eazydineapp.model.CartItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by a_man on 23-01-2018.
 */

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecycler;
    private ArrayList<CartItem> cartItems;
    private Handler mHandler;
    private TextView tv,mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
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
        cartRecycler = findViewById(R.id.cartRecycler);
        loadCartValue();
        setupCartRecycler();
        mp = (TextView) findViewById(R.id.checkoutAmount);
        tv = (TextView)findViewById(R.id.checkoutText);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO : create orders, delete cart object, navigate to MyOrders screen
                if("Place Order".equals(tv.getText())) {
                    createOrder();
                    loadOrdersFragment();
                    tv.setText("Continue to Order");
                    tv.setGravity(Gravity.CENTER);
                    mp.setText("Make Payment");
                }else {
                    onBackPressed();
                }
            }
        });
    }

    private void loadOrdersFragment() {
        final Fragment finalFragment = new OrdersFragment();
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.mainFrame, finalFragment, "My Orders");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        getSupportActionBar().setTitle("My Orders");

        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.post(mPendingRunnable);
    }

    private void createOrder() {
        Double totalPrice = 0.0;
        for (CartItem item : cartItems) {
            totalPrice += item.getPriceTotal();
        }

        Order order = new Order("order Id to be generated", OrderStatus.Placed, Calendar.getInstance().getTime().toString(), totalPrice, true, "Anu", "1",
                "Peacock Indian Cuisine", "Fremont, CA", cartItems);

        OrderService orderService = new OrderServiceImpl();
        orderService.add(order);
    }

    private void setupCartRecycler() {
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        CartAdapter cartAdapter = new CartAdapter(this, this.cartItems);
        cartRecycler.setAdapter(cartAdapter);
    }

    private void loadCartValue() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Ginger chicken curry", "Entree", 400, 1, "", "1"));
        cartItems.add(new CartItem("Paneer khurchan", "Entree", 370, 1, "", "2"));
        this.cartItems = cartItems;
    }
}
