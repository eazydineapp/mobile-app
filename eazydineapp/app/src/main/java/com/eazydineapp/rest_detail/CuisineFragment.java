package com.eazydineapp.rest_detail;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eazydineapp.model.CuisineCategory;
import com.eazydineapp.model.RestaurantMenu;
import com.eazydineapp.util.MyLinearLayoutManager;
import com.eazydineapp.R;
import com.eazydineapp.adapter.CuisineAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CuisineFragment extends Fragment {
    private RecyclerView cuisineRecycler;
    private MyLinearLayoutManager linearLayoutManager;
    private CuisineAdapter cuisineAdapter;

    public CuisineFragment() {
        // Required empty public constructor
        //TODO load menu here - Actual items to be set on restaurant adapter, cuisine adapter to have only categories
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuisine, container, false);
        cuisineRecycler = view.findViewById(R.id.cuisineRecycler);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupCuisineRecycler();
    }

    private void setupCuisineRecycler() {
        linearLayoutManager = new MyLinearLayoutManager(getContext());
        cuisineRecycler.setLayoutManager(linearLayoutManager);
        cuisineAdapter = new CuisineAdapter(getContext(), new CuisineAdapter.CuisineListToggleListener() {
            @Override
            public void OnListExpanded(final boolean selected) {
                if (selected) {
                    cuisineRecycler.scrollToPosition(0);
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        linearLayoutManager.setScrollEnabled(!selected);
                    }
                }, 500);
            }
        });
        cuisineRecycler.setAdapter(cuisineAdapter);
    }

    public void setCategory(ArrayList<CuisineCategory>categories) {
        cuisineAdapter.setCategories(categories);
    }

    public void  setMenu( HashMap<String,ArrayList<RestaurantMenu>> menus) {
        cuisineAdapter.setMenus(menus);
    }
}
