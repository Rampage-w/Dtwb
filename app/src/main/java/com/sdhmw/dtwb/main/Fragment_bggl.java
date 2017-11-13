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
import android.widget.ImageView;

import com.astuetz.PagerSlidingTabStrip;
import com.sdhmw.dtwb.model.MyApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/9/14.
 *
 * 报告管理
 */

public class Fragment_bggl extends Fragment {

    private View rootView;
    private ViewPager viewpager;
    private PagerSlidingTabStrip tabs;
    private Fragment f1,f2;
    private List<Fragment> pages = new ArrayList<Fragment>();

    private String strRole; //用户角色
    private String[] strTITLES = {"代办任务","查询报告"};


    private ImageView menu_iv;  //右上角的菜单

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //清空list，防止滑动tabhost的时候出现角标越界异常
        pages.clear();

        rootView = inflater.inflate(R.layout.fragment_bggl, container,false);

        initView(rootView);

        return rootView;
    }


    private void initView(View rootView) {

        //隐藏
        menu_iv = (ImageView) getActivity().findViewById(R.id.menu_iv);
        menu_iv.setVisibility(View.GONE);


        viewpager = (ViewPager) rootView.findViewById(R.id.bggl_dbrw_viewpager);
        if (f1 == null) {
            f1 = new Fragment_bggl_dbrw();
        }
        if (f2 == null){
            f2 = new Fragment_bggl_cxbg();
        }

        //角色不同 菜单显示不同
        MyApp app = (MyApp) getActivity().getApplicationContext();
        strRole = app.getUnittype();

        System.out.println("strRole---->" + strRole);


        if (strRole.equals("1")) {

            pages.add(f1);
            pages.add(f2);
        } else {

//            pages.add(f1);
            pages.add(f2);

            strTITLES = new String[]{"查询报告"};
        }

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
        tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.bggl_dbrw_tabs);
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

        private final String[] TITLES = strTITLES;
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
