package com.eazydineapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harini on 30-03-2019.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {


    private Context context;
    private List<Order> dataList;
    private LinearLayout orderTab;
    private CardView orderDetailsTab;
    boolean showContent = true;

    public  HistoryAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new Order("14 May", "China gate chef", "Mulund, Mumbai", 1225, 0));
        this.dataList.add(new Order("10 May", "Sugar and spice chef", "Vila parle, Mumbai", 980, 1));
        this.dataList.add(new Order("9 May", "Old spice chef", "Mulund, Mumbai", 1258, 1));
        //loadOrdersForUser();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_history, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView orderDate, orderPlaceName, orderPlaceAddress, orderTotal, orderStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.orderDate);
            orderPlaceName = itemView.findViewById(R.id.orderPlaceName);
            orderPlaceAddress = itemView.findViewById(R.id.orderPlaceAddress);
            orderTotal = itemView.findViewById(R.id.orderTotal);
            orderStatus = itemView.findViewById(R.id.orderStatus);
            orderTab = itemView.findViewById(R.id.orderDatePlaceTotal);
            orderDetailsTab = itemView.findViewById(R.id.cardViewOrderDetails);
        }

        public void setData(Order order) {
            order.setDate(order.getDate().replace(" ", "\n"));
            orderDate.setText(order.getDate());
            orderPlaceAddress.setText(order.getPlaceAddress());
            orderPlaceName.setText(order.getPlaceName());
            orderTotal.setText(String.valueOf(order.getOrderTotal()));
            orderStatus.setText(order.getStatus() == 0 ? "Delivered" : "Delivered");
            orderStatus.setBackground(ContextCompat.getDrawable(context, order.getStatus() == 0 ? R.drawable.round_primary : R.drawable.round_primary));
            orderDetailsTab.setVisibility(View.GONE);
            // Toggle the card view on click
            orderTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showContent) {
                        orderDetailsTab.setVisibility(View.VISIBLE);
                        showContent = false;
                    } else {
                        orderDetailsTab.setVisibility(View.GONE);
                        showContent = true;
                    }
                }
            });

        }
    }
}
