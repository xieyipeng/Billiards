package com.example.a13834598889.billiards.Tool.Adapter;

import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES = {"我的帖子","我的收藏"};
    private List<Fragment> fragments;

    public TabFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.fragments = list;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);//显示第几个页面
    }

    @Override
    public int getCount() {
        return fragments.size();//有几个页面
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
