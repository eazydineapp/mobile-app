package com.eazydineapp.activity;

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
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.vo.CartItem;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;
import com.eazydineapp.fragment.OrdersFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private Handler mHandler;
    private TextView tv,mp, orderPlaceName, orderPlaceAddress, orderTotal, serviceCharge, subTotal, tax;
    private String userId = "1", restaurantId;

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
        restaurantId = getIntent().getStringExtra("eazydine-restaurantId");
        loadCartValue();
        setupCartRecycler();
        mp = (TextView) findViewById(R.id.checkoutAmount);
        tv = (TextView)findViewById(R.id.checkoutText);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("Place Order".equals(tv.getText())) {
                    createOrder(); //TODO check if user has a table, if not pre-order
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
        Bundle bundle = new Bundle();
        bundle.putString("eazydine-restaurantId", restaurantId);

        final Fragment finalFragment = new OrdersFragment();
        finalFragment.setArguments(bundle);
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
        if(null != order) {
            order.setOrderStatus(OrderStatus.Placed);
            OrderService orderService = new OrderServiceImpl();
            orderService.updateOrder(order);
        }
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

                    float serviceChargeVal = round(0.04f*dbOrder.getTotalPrice());
                    serviceCharge  = findViewById(R.id.serviceCharge);
                    serviceCharge.setText("$"+String.valueOf(serviceChargeVal));

                    float taxVal = round(0.1f*dbOrder.getTotalPrice());
                    tax = findViewById(R.id.tax);
                    tax.setText("$"+String.valueOf(taxVal));

                    subTotal  = findViewById(R.id.subTotal);
                    subTotal.setText("$"+String.valueOf(dbOrder.getTotalPrice()));

                    float orderTotalVal = round(serviceChargeVal + taxVal + dbOrder.getTotalPrice());
                    orderTotal  = findViewById(R.id.orderTotal);
                    orderTotal.setText("$"+String.valueOf(orderTotalVal));

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
