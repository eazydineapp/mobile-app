package com.eazydineapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eazydineapp.R;
import com.eazydineapp.backend.vo.CartItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.MyViewHolder>{

//    private ViewHolder viewHolder;

//    private static class ViewHolder {
//        private TextView itemName, itemPrice;
//    }
//
//    public HistoryItemAdapter(Context context, int textViewResourceId, ArrayList<CartItem> items) {
//        super(context, textViewResourceId, items);
//    }

//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.history_item_row, parent, false);
//
//            viewHolder = new ViewHolder();
//            viewHolder.itemName = (TextView) convertView.findViewById(R.id.historyItemName);
//            viewHolder.itemPrice = (TextView) convertView.findViewById(R.id.historyItemPrice);
//
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//        CartItem item = getItem(position);
//        if (item!= null) {
//            viewHolder.itemName.setText(item.getName()+"("+item.getQuantity()+")");
//            viewHolder.itemPrice.setText(String.valueOf(item.getPriceTotal()));
//        }
//
//        return convertView;
//    }

    private Context context;
    private ArrayList<CartItem> dataList;

    public HistoryItemAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        //this.dataList.add(new CartItem("Ginger chicken curry", 1, 400, R.drawable.rest_res_1));
        //this.dataList.add(new CartItem("Paneer khurchan", 1, 370, R.drawable.rest_res_2));
    }

    public HistoryItemAdapter(Context context, ArrayList<CartItem> dataList) {
        this.context = context;
        this.dataList = new ArrayList<>(dataList);
    }

    public void setHistoryOrderItems(List<CartItem> dataList) {
        this.dataList = new ArrayList<>(dataList);
        notifyDataSetChanged();
    }

    @Override
    public HistoryItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HistoryItemAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.history_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(HistoryItemAdapter.MyViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView itemName, itemPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.historyItemName);
            itemPrice = itemView.findViewById(R.id.historyItemPrice);
        }

        public void setData(CartItem cartItem) {
            itemName.setText(cartItem.getName());
            itemPrice.setText(String.valueOf(cartItem.getPrice()));
        }
    }

}
