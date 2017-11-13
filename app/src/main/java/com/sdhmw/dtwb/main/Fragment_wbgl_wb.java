package com.sdhmw.dtwb.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import static java.util.jar.Manifest.*;

/**
 * Created by wanglingsheng on 2017/9/22.
 */

public class Fragment_wbgl_wb extends Fragment {


    private final String TAG = "Fragment_wbgl_wb";

    private static final int REQUESTCODE = 1;
    private View rootView;
    private ViewPager viewpager;
    private PagerSlidingTabStrip tabs;

    private Fragment f1,f2,f3,f4,f5,f6,f7,f8;

    private List<Fragment> pages = new ArrayList<Fragment>();

    private Button wbgl_save_btn;

    private ProgressDialog progressdialog;//等待进度条   (点击保存的时候显示)


    private String zcdm;
    private String sbsydd;
    private String Create_date;
    private String dt;
    private String dtWb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbgl_wb, container, false);

        Bundle bundle = getArguments();
        zcdm = bundle.getString("zcdm");
        sbsydd = bundle.getString("sbsydd");
        Create_date = bundle.getString("Create_date");

        dt = bundle.getString("dt");
        dtWb = bundle.getString("dtWb");

        System.out.println(TAG + " zcdm:" + zcdm);
        System.out.println(TAG + " sbsydd:" + sbsydd);
        System.out.println(TAG + " Create_date:" + Create_date);

        System.out.println(TAG + " dt:" + dt);
        System.out.println(TAG + " dtWb:" + dtWb);



        initView(rootView);

        return  rootView;

    }

    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    public void onCall(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.CALL_PHONE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
                return;
            }else{
                //拨号方法
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
                startActivity(intent);
            }
        } else {
            //拨号方法
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"));
            startActivity(intent);
        }
    }


    private void initView(View rootView) {

        //完成按钮
        wbgl_save_btn = (Button) rootView.findViewById(R.id.wbgl_save_btn);
        wbgl_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String[] items = {"审核人","审核人","审核人","审核人"};
                //弹窗选择审核人员
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("选择审核人");
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(getActivity(), items[i], Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();


//                progressdialog = ProgressDialog.show(getActivity(),"请稍后...","正在保存数据...");
//                progressdialog.setCancelable(true);

//                onCall();
            }
        });





        viewpager = (ViewPager) rootView.findViewById(R.id.wbgl_wb_viewpager);
        if(f1 == null){
            f1 = new Fragment_wbgl_wb_1();
        }
        if(f2 == null){
            f2 = new Fragment_wbgl_wb_2();
        }
        if(f3 == null){
            f3 = new Fragment_wbgl_wb_3();
        }
        if(f4 == null){
            f4 = new Fragment_wbgl_wb_4();
        }
        if(f5 == null){
            f5 = new Fragment_wbgl_wb_5();
        }
        if(f6 == null){
            f6 = new Fragment_wbgl_wb_6();
        }
        if(f7 == null){
            f7 = new Fragment_wbgl_wb_7();
        }
        if(f8 == null){
            f8 = new Fragment_wbgl_wb_8();
        }

        pages.add(f1);
        pages.add(f2);
        pages.add(f3);
        pages.add(f4);
        pages.add(f5);
        pages.add(f6);
        pages.add(f7);
        pages.add(f8);


//        viewpager.setAdapter(new MyAdapter(getSupportFragmentManager(),pages));   //Activity中使用这个
        viewpager.setAdapter(new MyAdapter(getChildFragmentManager(),pages));       //Fragment中嵌套fragment使用这个getChildFragmentManager

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
        tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.wbgl_wb_tabs);
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
            switch (position) {

                case 0:
                    Bundle bundle0 = new Bundle();
                    bundle0.putString("zcdm", zcdm);
                    f1.setArguments(bundle0);
                    return f1;
                case 1:
                    return f2;
                case 2:
                    return f3;
                case 3:
                    return f4;
                case 4:
                    return f5;
                case 5:
                    return f6;
                case 6:
                    return f7;
                case 7:
                    return f8;


//                Fragment fragment = null;
//                fragment = list.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putString("id", "" + position);
//                fragment.setArguments(bundle);
//                return fragment;
            }
            throw new IllegalStateException("No fragment at position " + position);
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
