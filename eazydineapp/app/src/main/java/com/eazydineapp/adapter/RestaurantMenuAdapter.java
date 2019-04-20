package com.eazydineapp.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eazydineapp.R;
import com.eazydineapp.activity.FoodDetailActivity;
import com.eazydineapp.model.RestaurantMenu;

import java.util.ArrayList;

/**
 * Created by a_man on 24-01-2018.
 */

public class RestaurantMenuAdapter extends RecyclerView.Adapter<RestaurantMenuAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<RestaurantMenu> dataList;

    public RestaurantMenuAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
    }

    public void setMenuItems(ArrayList<RestaurantMenu> menuItems) {
        this.dataList = menuItems;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant_menu, parent, false));
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
        private TextView itemName, itemDescription, itemPrice;
        private ImageView itemImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.menuItemName);
            itemDescription = itemView.findViewById(R.id.menuItemDescription);
            itemPrice = itemView.findViewById(R.id.menuItemPrice);
            itemImage = itemView.findViewById(R.id.menuItemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(FoodDetailActivity.newIntent(context, dataList.get(getAdapterPosition())));
                }
            });
        }

        public void setData(RestaurantMenu restaurantMenu) {
            Glide.with(context).load(restaurantMenu.getImagePath()).into(itemImage);
            itemPrice.setText(String.valueOf(restaurantMenu.getPrice()));
            itemDescription.setText(restaurantMenu.getDescription());
            itemName.setText(restaurantMenu.getName());
        }
    }
}
