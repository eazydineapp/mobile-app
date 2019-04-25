package com.eazydineapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eazydineapp.R;
import com.eazydineapp.model.CuisineCategory;
import com.eazydineapp.model.RestaurantMenu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by a_man on 31-01-2018.
 */

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<CuisineCategory> dataList;
    HashMap<String,ArrayList<RestaurantMenu>> menus;
    private RestaurantMenuAdapter restaurantMenuAdapter;
    private CuisineListToggleListener listToggleListener;

    public CuisineAdapter(Context context, CuisineListToggleListener listToggleListener) {
        this.context = context;
        this.listToggleListener = listToggleListener;
        this.dataList = new ArrayList<>();
        this.menus = new HashMap<>();
    }

    public void setCategories(ArrayList<CuisineCategory> categories) {
        this.dataList = categories;
        notifyDataSetChanged();
    }

    public void setMenus(HashMap<String,ArrayList<RestaurantMenu>> menus) {
        this.menus = menus;
        notifyDataSetChanged();
    }

    public void setMenuItems(ArrayList<RestaurantMenu> menuItems) {
        restaurantMenuAdapter.setMenuItems(menuItems);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        restaurantMenuAdapter = new RestaurantMenuAdapter(context);
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cuisine_cat, parent, false));
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
        private LinearLayout rootLayout;
        private TextView cuisineCategory;
        private ImageView cuisineItemToggle;
        private RecyclerView cuisineItemList;

        public MyViewHolder(View itemView) {
            super(itemView);
            cuisineItemList = itemView.findViewById(R.id.cuisineItemList);
            cuisineCategory = itemView.findViewById(R.id.cuisineCategory);
            cuisineItemToggle = itemView.findViewById(R.id.cuisineItemToggle);
            rootLayout = itemView.findViewById(R.id.rootLayout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    CuisineCategory category = dataList.get(pos);
                    category.setSelected(!category.isSelected());
                    Collections.swap(dataList, 0, pos);
                    notifyItemMoved(category.isSelected() ? pos : 0, category.isSelected() ? 0 : pos);
                    listToggleListener.OnListExpanded(category.isSelected());
                    notifyItemChanged(0);
                }
            });
        }

        public void setData(CuisineCategory category) {
            cuisineCategory.setText(category.getTitle());
            cuisineItemToggle.setImageDrawable(ContextCompat.getDrawable(context, category.isSelected() ? R.drawable.ic_keyboard_arrow_up_accent_24dp : R.drawable.ic_keyboard_arrow_down_accent_24dp));

            cuisineItemList.setLayoutManager(new LinearLayoutManager(context));
            cuisineItemList.setAdapter(restaurantMenuAdapter);
            cuisineItemList.setVisibility(category.isSelected() ? View.VISIBLE : View.GONE);
            setMenuItems(menus.get(category.getTitle()));

            ViewGroup.LayoutParams layoutParams = rootLayout.getLayoutParams();
            layoutParams.height = category.isSelected() ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
            rootLayout.setLayoutParams(layoutParams);
        }
    }

    public interface CuisineListToggleListener {
        void OnListExpanded(boolean selected);
    }
}
