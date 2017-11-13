package com.sdhmw.dtwb.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sdhmw.dtwb.main.Fragment1;

import java.util.List;

/**
 * Created by wanglingsheng on 2017/9/15.
 *
 * Viewpager设置适配器
 */

public class TabAdapter extends FragmentPagerAdapter {



    private List<Fragment> fragments;

    public TabAdapter(FragmentManager fm,List<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }




    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //设置tablayout标题
    @Override
    public CharSequence getPageTitle(int position) {
//        return super.getPageTitle(position);
        return Fragment1.tabTitle[position];
    }
}
