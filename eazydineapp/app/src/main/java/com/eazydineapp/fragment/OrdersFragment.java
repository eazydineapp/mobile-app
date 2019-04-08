package com.eazydineapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.activity.RestaurantMenuActivity;
import com.eazydineapp.adapter.OrdersAdapter;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.vo.Order;

import java.util.ArrayList;
import java.util.List;

public class OrdersFragment extends Fragment {
    private RecyclerView recyclerOrders;
    private List<Order> dataList;
    OrdersAdapter ordersAdapter;

    public OrdersFragment() {
        this.dataList = new ArrayList<>();
    }

    private void loadOrdersForUser() {
        OrderService orderService = new OrderServiceImpl();
        orderService.getOrderByUser("1", new UIOrderService() {
            @Override
            public void displayAllOrders(List<Order> orders) {
                dataList.addAll(orders);
                ordersAdapter.setOrders(dataList);
            }

            @Override
            public void displayOrder(com.eazydineapp.backend.vo.Order order) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordersAdapter = new OrdersAdapter(getContext());
        loadOrdersForUser();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        TextView checkOut = view.findViewById(R.id.checkoutText);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RestaurantMenuActivity.class);
                getActivity().startActivity(intent);
            }
        });
        recyclerOrders = view.findViewById(R.id.recyclerOrders);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOrdersRecycler();
    }

    private void setupOrdersRecycler() {
        recyclerOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerOrders.setAdapter(ordersAdapter);
    }

}
