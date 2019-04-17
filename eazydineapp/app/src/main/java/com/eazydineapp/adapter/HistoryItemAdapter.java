package com.eazydineapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.backend.vo.CartItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryItemAdapter extends ArrayAdapter<CartItem> {

    private ViewHolder viewHolder;

    private static class ViewHolder {
        private TextView itemName, itemPrice;
    }

    public HistoryItemAdapter(Context context, int textViewResourceId, ArrayList<CartItem> items) {
        super(context, textViewResourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.history_item_row, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.historyItemName);
            viewHolder.itemPrice = (TextView) convertView.findViewById(R.id.historyItemPrice);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CartItem item = getItem(position);
        if (item!= null) {
            viewHolder.itemName.setText(item.getName()+"("+item.getQuantity()+")");
            viewHolder.itemPrice.setText(String.valueOf(item.getPriceTotal()));
        }

        return convertView;
    }
}
