package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdhmw.dtwb.utils.TabAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment1 extends Fragment{

    private View rootView;

    private TabLayout tab;
    private ViewPager viewpager;
    private TabAdapter adapter;
    public static final String[] tabTitle = new String[]{"维保电梯数", "维保及时率", "电梯故障率"};


    private Fragment_sy_wbdtsl f1 = new Fragment_sy_wbdtsl();
    private Fragment_sy_wbjsl f2 = new Fragment_sy_wbjsl();
    private Fragment_sy_dtgzl f3 = new Fragment_sy_dtgzl();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        System.out.println("onCreateView--------->");

        rootView = inflater.inflate(R.layout.fragment1,container,false);

//        if (null == rootView) {
//        }
//        ViewGroup parent = (ViewGroup) rootView.getParent();
//        if (parent != null) {
//            parent.removeView(rootView);
//        }



        initviews(rootView);

        return rootView;
    }

    private void initviews(View rootView) {
        tab = (TabLayout) rootView.findViewById(R.id.tab);
        viewpager = (ViewPager) rootView.findViewById(R.id.viewpager);

        viewpager.setOffscreenPageLimit(3); //设置缓存页面的个数

        List<Fragment> fragments = new ArrayList<>();

        System.out.println("fragments:"+fragments);




        fragments.add(0,f1);
        fragments.add(1,f2);
        fragments.add(2,f3);


//        for (int i = 0; i < tabTitle.length; i++) {
//            fragments.add(TabLayoutFragment.newInstance(i + 1));      //一个fragment多用
//        }

//        adapter = new TabAdapter(getFragmentManager(), fragments);
        adapter = new TabAdapter(getChildFragmentManager(), fragments);    //fragment嵌套时需要用到getChildFragmentManager



        //给ViewPager设置适配器
        viewpager.setAdapter(adapter);
        //将TabLayout和ViewPager关联起来。
        tab.setupWithViewPager(viewpager);
        //设置可以滑动
//        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

//        System.out.println("f1:"+f1);
//        System.out.println("f2:"+f2);
//        System.out.println("f3:"+f3);

        System.out.println("onDestroyView Fragment1");

    }
}

