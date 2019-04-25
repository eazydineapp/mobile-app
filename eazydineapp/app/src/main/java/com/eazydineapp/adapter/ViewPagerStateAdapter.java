package com.eazydineapp.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a_man on 24-01-2018.
 */

public class ViewPagerStateAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentsList = new ArrayList<>();
    private final List<String> fragmentsTitleList = new ArrayList<>();

    public ViewPagerStateAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsTitleList.get(position);
    }

    public void addFrag(@NonNull Fragment fragment, @NonNull String title) {
        fragmentsList.add(fragment);
        fragmentsTitleList.add(title);
    }
}
