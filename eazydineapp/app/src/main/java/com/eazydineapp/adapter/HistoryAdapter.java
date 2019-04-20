package com.eazydineapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.activity.MainActivity;
import com.eazydineapp.activity.RestaurantActivity;
import com.eazydineapp.backend.service.api.OrderService;
import com.eazydineapp.backend.service.impl.OrderServiceImpl;
import com.eazydineapp.backend.vo.CartItem;
import com.eazydineapp.backend.vo.Order;
import com.eazydineapp.backend.vo.OrderStatus;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Harini on 30-03-2019.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    private Context context;
    private List<Order> dataList;
    boolean showContent = true;

    public  HistoryAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();

        //loadOrdersForUser();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
        holder.orderTab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (showContent) {
                    holder.orderDetailsTab.setVisibility(View.VISIBLE);
                    showContent = false;
                } else {
                    holder.orderDetailsTab.setVisibility(View.GONE);
                    showContent = true;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setOrders(List<Order> orders) {
        dataList = orders;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView orderDate, orderPlaceName, orderPlaceAddress, orderTotal, orderStatus, reorderBtn;
        private LinearLayout orderTab;
        private CardView orderDetailsTab;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderPlaceName = itemView.findViewById(R.id.orderPlaceName);
            orderPlaceAddress = itemView.findViewById(R.id.orderPlaceAddress);
            orderTotal = itemView.findViewById(R.id.orderTotal);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderTab = itemView.findViewById(R.id.orderDatePlaceTotal);
            orderDetailsTab = itemView.findViewById(R.id.cardViewOrderDetails);
            reorderBtn = itemView.findViewById(R.id.reorderBtn);
        }

        public void setData(final Order order) {
            Date date = new Date(order.getOrderDate());
            orderDate.setText(String.valueOf(date.getDate()) +" "+ new DateFormatSymbols().getMonths()[date.getMonth()]);
            orderPlaceAddress.setText(order.getRestaurantAddress());
            orderPlaceName.setText(order.getRestaurantName());
            orderTotal.setText(String.valueOf(order.getTotalPrice()));
            orderStatus.setText(order.getOrderStatus().toString());
            orderDetailsTab.setVisibility(View.GONE);
            reorderBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    addItemToCart(order);
                }
            });

        }
    }

    private void addItemToCart(Order dbOrder) {
        ArrayList<CartItem> cartItems = new ArrayList<>(dbOrder.getItemList());

        Order order = new Order("order Id to be generated", OrderStatus.Cart, Calendar.getInstance().getTime().toString(), dbOrder.getTotalPrice(), false,
                dbOrder.getUserId(), dbOrder.getRestaurantId(), dbOrder.getRestaurantName(), dbOrder.getRestaurantAddress(), cartItems);

        OrderService orderService = new OrderServiceImpl();
        orderService.addToCart(order);

        Intent newIntent = new Intent(context, RestaurantActivity.class);
        newIntent.putExtra("eazydine-restaurantId", dbOrder.getRestaurantId());
        context.startActivity(newIntent);
    }
}

