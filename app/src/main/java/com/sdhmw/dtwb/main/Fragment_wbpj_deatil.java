package com.sdhmw.dtwb.main;

import android.app.Activity;
import android.content.Context;
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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import com.astuetz.PagerSlidingTabStrip;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/10/26.
 * 维保评价-评价
 */
public class Fragment_wbpj_deatil extends Fragment {

    private View rootView;
    private ViewPager viewpager;
    private PagerSlidingTabStrip tabs;
    private Fragment f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11;
    private List<Fragment> pages = new ArrayList<Fragment>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //清空list，防止滑动tabhost的时候出现角标越界异常
        pages.clear();

        rootView = inflater.inflate(R.layout.fragment_wbpj_detail, container,false);

        initView(rootView);

        return rootView;
    }


    private void initView(View rootView) {

        viewpager = (ViewPager) rootView.findViewById(R.id.wbpj_pj_viewpager);

        if (f1 == null) {
            f1 = new Fragment_wbpj_deatil_1();
        }
        if (f2 == null){
            f2 = new Fragment_wbpj_deatil_2();
        }
        if (f3 == null) {
            f3 = new Fragment_wbpj_deatil_3();
        }
        if (f4 == null) {
            f4 = new Fragment_wbpj_deatil_4();
        }
        if (f5 == null) {
            f5 = new Fragment_wbpj_deatil_5();
        }
        if (f6 == null) {
            f6 = new Fragment_wbpj_deatil_6();
        }
        if (f7 == null) {
            f7 = new Fragment_wbpj_deatil_7();
        }
        if (f8 == null) {
            f8 = new Fragment_wbpj_deatil_8();
        }
        if (f9 == null) {
            f9 = new Fragment_wbpj_deatil_9();
        }
        if (f10 == null) {
            f10 = new Fragment_wbpj_deatil_10();
        }
        if (f11 == null) {
            f11 = new Fragment_wbpj_deatil_11();
        }

        pages.add(f1);
        pages.add(f2);
        pages.add(f3);
        pages.add(f4);
        pages.add(f5);
        pages.add(f6);
        pages.add(f7);
        pages.add(f8);
        pages.add(f9);
        pages.add(f10);
        pages.add(f11);

        //Fragment中嵌套fragment使用这个getChildFragmentManager
        viewpager.setAdapter(new MyAdapter(getChildFragmentManager(),pages));

        //切换fragment 隐藏软键盘
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //切换fragment 隐藏软键盘
                hideIputKeyboard(getContext());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // 初始化 默认显示哪个
        viewpager.setCurrentItem(0);
        tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.wbpj_pj_tabs);
        tabs.setViewPager(viewpager);


    }


    //隐藏软键盘
    public void hideIputKeyboard(final Context context) {
        final Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                InputMethodManager mInputKeyBoard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (activity.getCurrentFocus() != null) {
                    mInputKeyBoard.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),        InputMethodManager.HIDE_NOT_ALWAYS);
                    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                }
            }
        });

    }


    public class MyAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"基本信息","许可管理","人员管理","办公场所","维保项目和周期","维保质量体系",
                "维保记录和档案","维保自行检查","定期检验","事故、故障和应急救援","日常安全监察违"};

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

