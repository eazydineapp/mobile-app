package com.eazydineapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eazydineapp.R;
import com.eazydineapp.backend.vo.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shriaithal
 * Code to display Orders
 */

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder> {
    private Context context;
    private List<CartItem> dataList;

    public OrdersAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public void setOrderItems(List<CartItem> dataList) {
        this.dataList = new ArrayList<>(dataList);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order, parent, false));
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
        private TextView name, price, priceTotal, quantity, itemStatus;
        private ImageView itemImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            priceTotal = itemView.findViewById(R.id.itemPriceTotal);
            itemImage = itemView.findViewById(R.id.itemImage);
            price = itemView.findViewById(R.id.itemPrice);
            quantity = itemView.findViewById(R.id.itemQuantity);
            itemStatus = itemView.findViewById(R.id.itemStatus);
        }

        public void setData(CartItem cartItem) {
            //TODO set data values for list page
            name.setText(cartItem.getName());
            price.setText(" x " + context.getString(R.string.rs) + cartItem.getPrice());
            priceTotal.setText(context.getString(R.string.rs) + " " + cartItem.getPriceTotal());
            quantity.setText(String.valueOf(cartItem.getQuantity()));
            itemStatus.setText(String.valueOf(cartItem.getItemStatus()));
            Glide.with(context).load(cartItem.getPhotoPath()).into(itemImage);
        }

    }
}
