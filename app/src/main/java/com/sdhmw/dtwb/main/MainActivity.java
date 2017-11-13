package com.sdhmw.dtwb.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.sdhmw.dtwb.model.MyApp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/9/15.
 * 主页
 */

public class MainActivity extends FragmentActivity {


    private List mTableItemList;
    private String role;    //用户角色 //1:检验单位2:维保单位3:监察单位4:个人
    private String unitid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        MyApp myApp = (MyApp) getApplicationContext();
        unitid = myApp.getUnitid();
        role = myApp.getUnittype();


        System.out.println("unitid:" + unitid);
        System.out.println("role:" + role);

        initTabData();
        initTabHost();
    }


    //初始化Tab数据
    private void initTabData() {
        mTableItemList = new ArrayList<>();

        //1:检验单位2:维保单位3:监察单位4:个人
        if (role.equals("2")) {
            //维保
            //添加tab
            mTableItemList.add(new TabItem(R.drawable.sy1,R.drawable.sy2,R.string.sy, Fragment_sy.class));
            mTableItemList.add(new TabItem(R.drawable.wbgl1,R.drawable.wbgl2,R.string.wbgl, Fragment_wbgl.class));
            mTableItemList.add(new TabItem(R.drawable.bggl1,R.drawable.bggl2,R.string.bggl, Fragment_bggl.class));
            mTableItemList.add(new TabItem(R.drawable.wd1,R.drawable.wd2,R.string.wd, Fragment_wd.class));

        } else if (role.equals("3")) {
            //监察
            //添加tab
            mTableItemList.add(new TabItem(R.drawable.sy1,R.drawable.sy2,R.string.sy, Fragment_sy.class));
            mTableItemList.add(new TabItem(R.drawable.wbpj1,R.drawable.wbpj2,R.string.wbpj, Fragment_wbpj.class));
            mTableItemList.add(new TabItem(R.drawable.bggl1,R.drawable.bggl2,R.string.bggl, Fragment_bggl.class));
            mTableItemList.add(new TabItem(R.drawable.wd1,R.drawable.wd2,R.string.wd, Fragment_wd.class));

        } else if (role.equals("1")) {
            //检验
            //添加tab
            mTableItemList.add(new TabItem(R.drawable.sy1,R.drawable.sy2,R.string.sy, Fragment_sy.class));
//            mTableItemList.add(new TabItem(R.drawable.wbpj1,R.drawable.wbpj2,R.string.wbpj, Fragment_wbpj.class));
            mTableItemList.add(new TabItem(R.drawable.bggl1,R.drawable.bggl2,R.string.bggl, Fragment_bggl.class));
            mTableItemList.add(new TabItem(R.drawable.wd1,R.drawable.wd2,R.string.wd, Fragment_wd.class));

        } else if (role.equals("4")) {
            //业主
            mTableItemList.add(new TabItem(R.drawable.sy1,R.drawable.sy2,R.string.sy, Fragment_sy.class));
            mTableItemList.add(new TabItem(R.drawable.wbgz1,R.drawable.wbgz2,R.string.wbgz, Fragment_wbgz.class));
            mTableItemList.add(new TabItem(R.drawable.bggl1,R.drawable.bggl2,R.string.bggl, Fragment_bggl.class));
            mTableItemList.add(new TabItem(R.drawable.wd1,R.drawable.wd2,R.string.wd, Fragment_wd.class));
        }

    }



    //初始化主页选项卡视图
    private void initTabHost() {
        //实例化FragmentTabHost对象
//        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost_wb);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);

        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i<mTableItemList.size(); i++) {
            TabItem tabItem = (TabItem) mTableItemList.get(i);
            //实例化一个TabSpec,设置tab的名称和视图
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(tabItem.getTitleString()).setIndicator(tabItem.getView());
            fragmentTabHost.addTab(tabSpec,tabItem.getFragmentClass(),null);

            //给Tab按钮设置背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));

            //默认选中第一个tab
            if(i == 0) {
                tabItem.setChecked(true);
            }
        }

        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //重置Tab样式
                for (int i = 0; i< mTableItemList.size(); i++) {
                    TabItem tabitem = (TabItem) mTableItemList.get(i);
                    if (tabId.equals(tabitem.getTitleString())) {
                        tabitem.setChecked(true);
                    }else {
                        tabitem.setChecked(false);
                    }
                }
            }
        });
    }



    class TabItem {
        //正常情况下显示的图片
        private int imageNormal;
        //选中情况下显示的图片
        private int imagePress;
        //tab的名字
        private int title;
        private String titleString;

        //tab对应的fragment
        public Class<? extends Fragment> fragmentClass;

        public View view;
        public ImageView imageView;
        public TextView textView;

        public TabItem(int imageNormal, int imagePress, int title,Class<? extends Fragment> fragmentClass) {
            this.imageNormal = imageNormal;
            this.imagePress = imagePress;
            this.title = title;
            this.fragmentClass =fragmentClass;
        }

        public Class<? extends  Fragment> getFragmentClass() {
            return fragmentClass;
        }
        public int getImageNormal() {
            return imageNormal;
        }

        public int getImagePress() {
            return imagePress;
        }

        public int getTitle() {
            return  title;
        }

        public String getTitleString() {
            if (title == 0) {
                return "";
            }
            if(TextUtils.isEmpty(titleString)) {
                titleString = getString(title);
            }
            return titleString;
        }

        public View getView() {
            if(this.view == null) {
                this.view = getLayoutInflater().inflate(R.layout.view_tab_indicator, null);
                this.imageView = (ImageView) this.view.findViewById(R.id.tab_iv_image);
                this.textView = (TextView) this.view.findViewById(R.id.tab_tv_text);
                if(this.title == 0) {
                    this.textView.setVisibility(View.GONE);
                } else {
                    this.textView.setVisibility(View.VISIBLE);
                    this.textView.setText(getTitleString());
                }
                this.imageView.setImageResource(imageNormal);
            }
            return this.view;
        }

        //切换tab的方法
        public void setChecked(boolean isChecked) {
            if(imageView != null) {
                if(isChecked) {
                    imageView.setImageResource(imagePress);
                }else {
                    imageView.setImageResource(imageNormal);
                }
            }
            if(textView != null && title != 0) {
                if(isChecked) {
                    textView.setTextColor(getResources().getColor(R.color.tab_light_color));
                } else {
                    textView.setTextColor(getResources().getColor(R.color.tab_color));
                }
            }
        }
    }



    //监听键盘回退键


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            new AlertDialog.Builder(this)
                    .setMessage("确定要退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            LoginActivity2.finish.finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .show();




        }



        return super.onKeyDown(keyCode, event);
    }


}
