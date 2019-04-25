package com.eazydineapp.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eazydineapp.R;
import com.eazydineapp.adapter.HistoryItemAdapter;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.vo.CartItem;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Harini on 14-04-2019.
 */

public class HistoryOrderDetailActivity extends AppCompatActivity {
    RecyclerView historyItemRecycler;
    String userId, restaurantId;
    TextView historyOrderDate, reorderBtn, historyOrderPlaceName, historyOrderPlaceAddress, subTotalTextView, serviceChargeTextView, totalAmountTextView;
    RecyclerView historyOrderItemsRecycler;
    HistoryItemAdapter historyItemAdapter;
    ArrayList<CartItem> items = new ArrayList <>();
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order_detail);
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
        historyItemRecycler = findViewById(R.id.historyOrderItemsRecycler);
        historyOrderDate = findViewById(R.id.historyOrderDate);
        historyOrderPlaceName = findViewById(R.id.historyOrderPlaceName);
        historyOrderPlaceAddress = findViewById(R.id.historyOrderPlaceAddress);
        subTotalTextView = findViewById(R.id.subTotalTextView);
        serviceChargeTextView = findViewById(R.id.serviceChargeTextView);
        totalAmountTextView = findViewById(R.id.totalAmountTextView);
        historyOrderItemsRecycler = findViewById(R.id.historyOrderItemsRecycler);

        AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        userId = storagePrefUtil.getRegisteredUser(this);
        restaurantId = storagePrefUtil.getValue(this, "RESTAURANT_ID");

        historyItemAdapter =  new HistoryItemAdapter(this, items);
        setupHistoryOrderItemsRecycler();

        // Get the order object from the History Fragment
        order = (Order) getIntent().getExtras().get("Order");

        reorderBtn = findViewById(R.id.reorderBtn);
        reorderBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                addItemToCart(order);
            }
        });

        subTotalTextView.setText(String.valueOf(order.getTotalPrice()));
        //Compute service fee and total price
        float total = order.getTotalPrice();
        int serviceFee = (int) (total * 0.15);
        serviceChargeTextView.setText(serviceFee);
        totalAmountTextView.setText((int) (total+serviceFee));
    }

    private void setupHistoryOrderItemsRecycler(){

        items.add(new CartItem("Ginger chicken curry", 1, 400, R.drawable.rest_res_1));
        items.add(new CartItem("Paneer khurchan", 1, 370, R.drawable.rest_res_2));


        LayoutInflater inflator = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflator.inflate(R.layout.history_item_row, null);
        historyItemAdapter.setHistoryOrderItems(items);
        historyItemAdapter.notifyDataSetChanged();
        historyOrderItemsRecycler.setLayoutManager(new LinearLayoutManager(this));
        historyOrderItemsRecycler.setAdapter(historyItemAdapter);
    }

    private void addItemToCart(Order dbOrder) {
        ArrayList<CartItem> cartItems = new ArrayList<>(dbOrder.getItemList());

        Order order = new Order("order Id to be generated", OrderStatus.Cart, Calendar.getInstance().getTime().toString(), dbOrder.getTotalPrice(), false,
                dbOrder.getUserId(), dbOrder.getRestaurantId(), dbOrder.getRestaurantName(), dbOrder.getRestaurantAddress(), cartItems);

        OrderService orderService = new OrderServiceImpl();
        orderService.addToCart(order);

        Intent newIntent = new Intent(this, RestaurantActivity.class);
        newIntent.putExtra("eazydine-restaurantId", dbOrder.getRestaurantId());
        this.startActivity(newIntent);
    }
}
