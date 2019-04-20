package com.eazydineapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.eazydineapp.R;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.vo.OrderPayment;
import com.eazydineapp.fragment.HistoryFragment;
import com.eazydineapp.fragment.OrdersFragment;
import com.paypal.android.sdk.payments.PayPalConfiguration;

public class OrderTrackerActivity extends AppCompatActivity {

    private String userId, restaurantId;
    private AndroidStoragePrefUtil storagePrefUtil;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tracker);
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

        storagePrefUtil = new AndroidStoragePrefUtil();
        userId = storagePrefUtil.getRegisteredUser(this);
        restaurantId = storagePrefUtil.getValue(this, "RESTAURANT_ID");

        String loadScreen = (String)getIntent().getSerializableExtra("eazydineapp-screen");

        if("MY ORDERS".equalsIgnoreCase(loadScreen)) {
            loadOrdersFragment();
        }else {
            loadHistoryFragment();
        }
    }

    private void loadHistoryFragment() {
        final HistoryFragment historyFragment = new HistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("orderTrackerRestaurantId", restaurantId);
        historyFragment.setArguments(bundle);

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.mainFrame, historyFragment, "Order History");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        getSupportActionBar().setTitle("Order History");

        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.post(mPendingRunnable);
    }

    private void loadOrdersFragment() {
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.mainFrame, new OrdersFragment(), "My Orders");
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        getSupportActionBar().setTitle("My Orders");

        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.post(mPendingRunnable);
    }
}
