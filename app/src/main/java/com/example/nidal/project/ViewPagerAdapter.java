package com.example.nidal.project;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position ==0) {
            return new AccidentFragment();
        } else if (position == 1) {
            return new WeatherFragment();
        } else if (position == 2) {
            return new LocationFragment();
        }
        else return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
