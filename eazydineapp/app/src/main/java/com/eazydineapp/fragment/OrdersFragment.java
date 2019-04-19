package com.eazydineapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.activity.CartActivity;
import com.eazydineapp.activity.OrderPaymentActivity;
import com.eazydineapp.activity.RestaurantMenuActivity;
import com.eazydineapp.adapter.OrdersAdapter;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.api.RestaurantService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.service.impl.RestaurantServiceImpl;
import com.eazydineapp.backend.ui.api.UIOrderService;
import com.eazydineapp.backend.ui.api.UIRestaurantService;
import com.eazydineapp.backend.util.AndroidStoragePrefUtil;
import com.eazydineapp.backend.util.AppUtil;
import com.eazydineapp.backend.vo.CartItem;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderPayment;
import com.eazydineapp.backend.vo.Restaurant;
import com.eazydineapp.rest_detail.RestaurantDetailActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.Math.round;

public class OrdersFragment extends Fragment {
    private RecyclerView recyclerOrders;
    private List<Order> dataList;
    private OrdersAdapter ordersAdapter;
    private String restaurantId, userId;
    private OrderPayment payment;
    private TextView orderPlaceName, orderPlaceAddress, orderTotal, serviceCharge, subTotal, tax, orderDate;

    public OrdersFragment() {
        this.dataList = new ArrayList<>();
    }

    private void loadOrdersForUser() {
        if(null == userId || null == restaurantId) return;

        OrderService orderService = new OrderServiceImpl();
        orderService.getOrderByUserAndRestaurant(userId, restaurantId, new UIOrderService() {
            @Override
            public void displayAllOrders(List<Order> orders) {
                if(null == orders || orders.isEmpty() || null == orders.get(0).getItemList()){
                    dataList = new ArrayList<>();
                    return;
                }
                dataList.addAll(orders);
                List<CartItem> cartItems = new ArrayList<>();
                float totalAmount = 0f;
                for(Order order : orders) {
                    for(CartItem cartItem : order.getItemList()) {
                        cartItem.setItemStatus(order.getOrderStatus().toString());
                        cartItems.add(cartItem);
                    }
                    totalAmount += order.getTotalPrice();
                }

                Order dbOrder = orders.get(0);
                if(null != getActivity()) {
                    orderPlaceName = getActivity().findViewById(R.id.orderPlaceNameFg);
                    orderPlaceName.setText(dbOrder.getRestaurantName());

                    orderPlaceAddress  = getActivity().findViewById(R.id.orderPlaceAddressFg);
                    orderPlaceAddress.setText(dbOrder.getRestaurantAddress());

                    float serviceChargeVal = AppUtil.round(0.04f*totalAmount);
                    serviceCharge  = getActivity().findViewById(R.id.serviceChargeFg);
                    serviceCharge.setText("$"+String.valueOf(serviceChargeVal));

                    float taxVal = AppUtil.round(0.1f*totalAmount);
                    tax = getActivity().findViewById(R.id.taxFg);
                    tax.setText("$"+String.valueOf(taxVal));

                    subTotal  = getActivity().findViewById(R.id.subTotalFg);
                    subTotal.setText("$"+String.valueOf(totalAmount));

                    float orderTotalVal = AppUtil.round(serviceChargeVal + taxVal + totalAmount);
                    orderTotal  = getActivity().findViewById(R.id.orderTotalFg);
                    orderTotal.setText("$"+String.valueOf(orderTotalVal));

                    payment = new OrderPayment(orderTotalVal, totalAmount, serviceChargeVal, taxVal);

                    Date date = new Date(dbOrder.getOrderDate());
                    orderDate = getActivity().findViewById(R.id.orderDateFg);
                    orderDate.setText(String.valueOf(date.getDate()) +" "+ new DateFormatSymbols().getMonths()[date.getMonth()]);
                }
                ordersAdapter.setOrderItems(cartItems);
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AndroidStoragePrefUtil storagePrefUtil = new AndroidStoragePrefUtil();
        userId = storagePrefUtil.getRegisteredUser(this);
        restaurantId = storagePrefUtil.getValue(this, "RESTAURANT_ID");
        loadOrdersForUser();

        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        setHasOptionsMenu(true);

        TextView checkOut = view.findViewById(R.id.checkoutText);
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
                //getActivity().startActivity(intent);
                loadRestaurantById(restaurantId);
            }
        });
        recyclerOrders = view.findViewById(R.id.recyclerOrders);
        return view;
    }

    private void loadRestaurantById(String restaurantId) {
        RestaurantService restaurantService = new RestaurantServiceImpl();
        final Gson gson = new Gson();
        restaurantService.getRestaurantById(getContext(), Long.parseLong(restaurantId), new UIRestaurantService() {
            @Override
            public void onSuccess(JSONObject jsonResponse) {
                Restaurant restaurant = gson.fromJson(jsonResponse.toString(), Restaurant.class);
                startActivity(RestaurantDetailActivity.newIntent(getContext(), restaurant));
            }

            @Override
            public void onSuccess(JSONArray jsonArray) { }

            @Override
            public void onError(String message) {
                Log.e("Get Restauarnts", message);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupOrdersRecycler();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_pay, menu);
        View payActionView = menu.findItem(R.id.action_pay).getActionView();
        payActionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(getContext(), OrderPaymentActivity.class);
                newIntent.putExtra("eazydineapp-payment", payment);
                startActivity(newIntent);
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    private void setupOrdersRecycler() {
        recyclerOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerOrders.setAdapter(ordersAdapter);
    }

}
