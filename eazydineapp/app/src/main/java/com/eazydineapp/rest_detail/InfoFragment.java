package com.eazydineapp.rest_detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eazydineapp.R;
import com.eazydineapp.backend.vo.Restaurant;

public class InfoFragment extends Fragment {

    private Restaurant restaurant;

    public InfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        if(restaurant != null) {
            TextView city = view.findViewById(R.id.city);
            city.setText(restaurant.getCity());

            TextView zipCode = view.findViewById(R.id.zipCodeStr);
            zipCode.setText(String.valueOf(restaurant.getZipcode()));

            TextView cuisine = view.findViewById(R.id.cuisine);
            cuisine.setText(restaurant.getCuisine());

            TextView phoneNumber = view.findViewById(R.id.phoneNumber);
            phoneNumber.setText(restaurant.getPhonenumber());
        }
        return view;
    }

    public void setRestaurantDetails(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
