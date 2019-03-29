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
import com.eazydineapp.model.Restaurant;
import com.eazydineapp.rest_detail.RestaurantDetailActivity;

import java.util.ArrayList;

/**
 * Created by a_man on 22-01-2018.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Restaurant> dataList;

    public RestaurantAdapter(Context context) {
        this.context = context;
        this.dataList = new ArrayList<>();
        this.dataList.add(new Restaurant("Paradise Biryani", "Punjabi food, South indian, more", R.drawable.paradise));
        this.dataList.add(new Restaurant("Peacock", "Fast food, coffee, mexican", R.drawable.rest_res_2));
        this.dataList.add(new Restaurant("Ulavcharu", "Chinese food, Punjabi food, South indian, Gujrati food", R.drawable.rest_res_3));
        this.dataList.add(new Restaurant("Mcdonalds", "Fast food, coffee, mexican", R.drawable.rest_res_4));
        this.dataList.add(new Restaurant("Global fusion china", "Chinese food, Punjabi food, South indian, Gujrati food", R.drawable.rest_res_1));
        this.dataList.add(new Restaurant("Red chilly ", "Fast food, coffee, mexican", R.drawable.rest_res_2));
        this.dataList.add(new Restaurant("Mexican grill", "Chinese food, Punjabi food, South indian, Gujrati food", R.drawable.rest_res_3));
        this.dataList.add(new Restaurant("Dennys", "Fast food, coffee, mexican", R.drawable.rest_res_4));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_restaurant, parent, false));
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
        private TextView name, description, restMinOrderPrice;
        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restName);
            description = itemView.findViewById(R.id.restDesc);
            restMinOrderPrice = itemView.findViewById(R.id.restMinOrderPrice);
            imageView = itemView.findViewById(R.id.restRes);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(RestaurantDetailActivity.newIntent(context, dataList.get(getAdapterPosition())));
                }
            });
        }

        public void setData(Restaurant restaurant) {
            name.setText(restaurant.getName());
            description.setText(restaurant.getDescription());
            //restMinOrderPrice.setText(context.getString(R.string.rs) + " 300");
            restMinOrderPrice.setText("$10");
            Glide.with(context).load(restaurant.getImageRes()).into(imageView);
        }
    }
}
