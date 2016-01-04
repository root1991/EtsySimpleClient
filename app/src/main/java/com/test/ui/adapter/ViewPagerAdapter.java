package com.test.ui.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.test.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrewkhristyan on 12/27/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<BaseFragment> fragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(BaseFragment fragment, String title) {
        fragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public BaseFragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
