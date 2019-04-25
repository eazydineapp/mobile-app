package com.eazydineapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eazydineapp.R;
import com.eazydineapp.adapter.RestaurantMenuAdapter;

public class RestaurantMenuFragment extends Fragment {
    private RecyclerView recyclerMenu;


    public RestaurantMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_menu, container, false);
        recyclerMenu = view.findViewById(R.id.recyclerMenu);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRestaurantMenu();
    }

    private void setupRestaurantMenu() {
        recyclerMenu.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMenu.setAdapter(new RestaurantMenuAdapter(getContext()));
    }
}
