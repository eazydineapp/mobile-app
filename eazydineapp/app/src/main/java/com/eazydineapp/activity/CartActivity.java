package com.eazydineapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.eazydineapp.R;
import com.eazydineapp.adapter.CartAdapter;
import com.eazydineapp.checkout.CheckoutActivity;
import com.eazydineapp.model.CartItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by a_man on 23-01-2018.
 */

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecycler;
    private ArrayList<CartItem> cartItems;

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
        findViewById(R.id.checkout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                intent.putExtra("cartItems", cartItems);
                startActivity(intent);
            }
        });
    }

    private void setupCartRecycler() {
        cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        CartAdapter cartAdapter = new CartAdapter(this, this.cartItems);
        cartRecycler.setAdapter(cartAdapter);
    }

    private void loadCartValue() {
        ArrayList<CartItem> cartItems = new ArrayList<>();
        cartItems.add(new CartItem("Ginger chicken curry", "Entree", 400, 1, "", "1"));
        cartItems.add(new CartItem("Paneer khurchan", "Entree", 370,  1, "", "2"));
        this.cartItems = cartItems;
    }
}
