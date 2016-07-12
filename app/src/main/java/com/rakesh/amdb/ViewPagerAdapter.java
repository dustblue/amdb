package com.rakesh.amdb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    CharSequence Titles[];
    int NumbOfTabs;
    Boolean sort;

    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, boolean sort) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.sort = sort;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            MoviesTab moviesTab = new MoviesTab();
            moviesTab.sort = sort;
            return moviesTab;
        } else {
            SeriesTab seriesTab = new SeriesTab();
            seriesTab.sort = sort;
            return seriesTab;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}