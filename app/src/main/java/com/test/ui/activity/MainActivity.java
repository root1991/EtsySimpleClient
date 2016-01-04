package com.test.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.test.R;
import com.test.ui.adapter.ViewPagerAdapter;
import com.test.ui.fragment.FeaturedFragment;
import com.test.ui.fragment.SearchFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final int OFFSCREEN_PAGE_LIMIT = 1;
    @Bind(R.id.tab_layout)
    TabLayout mTabLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_example);
        ButterKnife.bind(this);
        setupViewPager();
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        fillAdapter(adapter);
        mViewPager.setOffscreenPageLimit(OFFSCREEN_PAGE_LIMIT);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void fillAdapter(ViewPagerAdapter adapter) {
        adapter.addFragment(SearchFragment.newInstance(), getString(R.string.str_tab_search));
        adapter.addFragment(FeaturedFragment.newInstance(), getString(R.string.str_tab_featured));
    }

}
