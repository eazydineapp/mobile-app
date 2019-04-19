package com.eazydineapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.eazydineapp.activity.HistoryOrderDetailActivity;
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
    private Order orderObj;


    public  HistoryAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
//        this.dataList.add(new Order("14 May", "China gate chef", "Mulund, Mumbai", 1225, 0));
//        this.dataList.add(new Order("10 May", "Sugar and spice chef", "Vila parle, Mumbai", 980, 1));
//        this.dataList.add(new Order("9 May", "Old spice chef", "Mulund, Mumbai", 1258, 1));
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
                Intent intent = new Intent(context, HistoryOrderDetailActivity.class);
                intent.putExtra("Order", (Parcelable) orderObj);
                context.startActivity(intent);
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
        private TextView orderDate, orderPlaceName, orderPlaceAddress, orderTotal, orderStatus;
        private LinearLayout orderTab;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderPlaceName = itemView.findViewById(R.id.orderPlaceName);
            orderPlaceAddress = itemView.findViewById(R.id.orderPlaceAddress);
            orderTotal = itemView.findViewById(R.id.orderTotal);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderTab = itemView.findViewById(R.id.orderDatePlaceTotal);
        }

        public void setData(final Order order) {
            orderObj = order;
            Date date = new Date(order.getOrderDate());
            orderDate.setText(String.valueOf(date.getDate()) +" "+ new DateFormatSymbols().getMonths()[date.getMonth()]);
            orderPlaceAddress.setText(order.getRestaurantAddress());
            orderPlaceName.setText(order.getRestaurantName());
            orderTotal.setText(String.valueOf(order.getTotalPrice()));
            orderStatus.setText(order.getOrderStatus().toString());
        }
    }
}

