package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/10/18.
 * 报告管理 代办任务（维保管理的详情）
 */

public class Fragment_bggl_dbrw_detail extends Fragment {

    private View rootView;
    private ViewPager viewpager;
    private PagerSlidingTabStrip tabs;

    private Fragment f1,f2,f3,f4,f5,f6,f7,f8;
    private List<Fragment> pages = new ArrayList<Fragment>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_bggl_dbrw_detail,container,false);

        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {

        viewpager = (ViewPager) rootView.findViewById(R.id.bggl_dbrw_detail_viewpager);
        if(f1 == null){
            f1 = new Fragment_bggl_dbrw_detail_1();
        }
        if(f2 == null){
            f2 = new Fragment_bggl_dbrw_detail_2();
        }
        if(f3 == null){
            f3 = new Fragment_bggl_dbrw_detail_3();
        }
        if(f4 == null){
            f4 = new Fragment_bggl_dbrw_detail_4();
        }
        if(f5 == null){
            f5 = new Fragment_bggl_dbrw_detail_5();
        }
        if(f6 == null){
            f6 = new Fragment_bggl_dbrw_detail_6();
        }
        if(f7 == null){
            f7 = new Fragment_bggl_dbrw_detail_7();
        }
        if(f8 == null){
            f8 = new Fragment_bggl_dbrw_detail_8();
        }

        pages.add(f1);
        pages.add(f2);
        pages.add(f3);
        pages.add(f4);
        pages.add(f5);
        pages.add(f6);
        pages.add(f7);
        pages.add(f8);


        viewpager.setAdapter(new MyAdapter(getChildFragmentManager(),pages));       //Fragment中嵌套fragment使用这个getChildFragmentManager

        // 初始化 默认显示哪个
        viewpager.setCurrentItem(0);
        tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.bggl_dbrw_detail_tabs);
        tabs.setViewPager(viewpager);

    }

    public class MyAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = { "基本信息","机房","井道", "轿厢","地坑","拍照","备注","用户签名"};
        public FragmentManager fm;
        public List<Fragment> list;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyAdapter(FragmentManager fm,List<Fragment> list){
            super(fm);
            this.fm = fm;
            this.list = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//			return super.getPageTitle(position);
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            Log.i("sssssssssssss", "getItem");

            fragment = list.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("id", "" + position);
            fragment.setArguments(bundle);

            return fragment;
        }

        @Override
        public int getCount() {
            return list.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fm.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);

            Fragment fragment = list.get(position);
            fm.beginTransaction().hide(fragment).commit();
        }

    }


}
