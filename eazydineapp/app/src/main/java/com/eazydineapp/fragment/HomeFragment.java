package com.eazydineapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.activity.CartActivity;
import com.eazydineapp.activity.RefineActivity;
import com.eazydineapp.activity.RestaurantActivity;
import com.eazydineapp.adapter.FoodCategoryAdapter;
import com.eazydineapp.adapter.RestaurantAdapter;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerFood, recyclerRestaurants;
    private EditText searchTab;
    private ImageView nfc;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       /* inflater.inflate(R.menu.menu_home, menu);
        View cartActionView = menu.findItem(R.id.action_cart).getActionView();
        cartNotificationCount = ((TextView) cartActionView.findViewById(R.id.notification_count));
        cartActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CartActivity.class));
            }
        });
        setCartCount();
        super.onCreateOptionsMenu(menu, inflater); */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        searchTab = view.findViewById(R.id.search_tab);
        nfc = view.findViewById(R.id.nfcTag);
        //recyclerFood = view.findViewById(R.id.recyclerFood);
        // recyclerRestaurants = view.findViewById(R.id.recyclerRestaurants);
        view.findViewById(R.id.refine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RefineActivity.class));
            }
        });

        searchTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RestaurantActivity.class));
            }
        });

        nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(getContext(), RestaurantActivity.class);
                newIntent.putExtra("restaurantId", "76");
                startActivity(newIntent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // setupRecyclerFood();
//      setupRecyclerRestaurants();
    }

    private void setupRecyclerRestaurants() {
        recyclerRestaurants.setNestedScrollingEnabled(false);
        recyclerRestaurants.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerRestaurants.setAdapter(new RestaurantAdapter(getContext()));
    }

    private void setupRecyclerFood() {
        recyclerFood.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerFood.setAdapter(new FoodCategoryAdapter(getContext()));
    }
}
