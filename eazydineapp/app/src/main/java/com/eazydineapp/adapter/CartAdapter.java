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
import com.eazydineapp.model.CartItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a_man on 24-01-2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CartItem> dataList;

    public CartAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        //this.dataList.add(new CartItem("Ginger chicken curry", 1, 400, R.drawable.rest_res_1));
        //this.dataList.add(new CartItem("Paneer khurchan", 1, 370, R.drawable.rest_res_2));
    }

    public CartAdapter(Context context, ArrayList<CartItem> dataList) {
        this.context = context;
        this.dataList = new ArrayList<>(dataList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false));
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
        private TextView name, price, priceTotal, quantity;
        private ImageView itemImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            priceTotal = itemView.findViewById(R.id.itemPriceTotal);
            quantity = itemView.findViewById(R.id.itemQuantity);
            itemImage = itemView.findViewById(R.id.itemImage);

            itemView.findViewById(R.id.itemQuantityMinus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (dataList.get(pos).getQuantity() > 1) {
                        dataList.get(pos).setQuantity(dataList.get(pos).getQuantity() - 1);
                        notifyItemChanged(pos);
                    }
                }
            });
            itemView.findViewById(R.id.itemQuantityPlus).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    dataList.get(pos).setQuantity(dataList.get(pos).getQuantity() + 1);
                    notifyItemChanged(pos);
                }
            });
        }

        public void setData(CartItem cartItem) {
            name.setText(cartItem.getName());
            price.setText(context.getString(R.string.rs) + " x " + cartItem.getPrice());
            priceTotal.setText(context.getString(R.string.rs) + " " + cartItem.getPriceTotal());
            quantity.setText(String.valueOf(cartItem.getQuantity()));
            //Glide.with(context).load(cartItem.getImageRes()).into(itemImage);
        }
    }


}
