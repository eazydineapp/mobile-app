package com.eazydineapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.eazydineapp.R;
import com.eazydineapp.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 1/28/2018.
 */

public class LoginSignUpTabFragment extends Fragment {
    @BindView(R.id.auth_tabs)
    TabLayout mAuthTabs;
    @BindView(R.id.auth_viewpager)
    ViewPager mAuhViewPager;
    private int tabPosition;

    public LoginSignUpTabFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_signup_tab_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewPager();
        mAuthTabs.setupWithViewPager(mAuhViewPager);
    }

    public void setUpViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
       // adapter.addFragment(new SignInFragment(), "Sign in");
        adapter.addFragment(new RegisterFragment(), "Register");
        mAuhViewPager.setAdapter(adapter);
        mAuhViewPager.post(new Runnable() {
            @Override
            public void run() {
                mAuhViewPager.setCurrentItem(tabPosition);
            }
        });
    }

    public static LoginSignUpTabFragment newInstance(int tabPosition) {
        LoginSignUpTabFragment fragment = new LoginSignUpTabFragment();
        fragment.tabPosition = tabPosition;
        return fragment;
    }
}