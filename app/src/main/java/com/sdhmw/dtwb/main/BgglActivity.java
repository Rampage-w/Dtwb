package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by wanglingsheng on 2017/10/18.
 */

public class BgglActivity extends FragmentActivity {


    private String item;    //代办任务 or 查询报告 跳转的

    private ImageView menu_iv;  //右上角小菜单

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        //Fragment重叠解决方案
        //弹出原来保存的所有Fragment状态信息，重新加载
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStackImmediate(null, 1);
        }

        setContentView(R.layout.activity_bggl);

        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        item = getIntent().getStringExtra("item");

        System.out.println("item---> " + item);

        if (item.equals("0")) {

            ft.add(R.id.bggl_Fragment, new Fragment_bggl_dbrw_detail());
        } else if (item.equals("1")) {

            ft.add(R.id.bggl_Fragment, new Fragment_bggl_cxbg_list());
        }



        ft.commit();


        //隐藏右上角小菜单
        menu_iv = (ImageView) findViewById(R.id.menu_iv);
        menu_iv.setVisibility(View.GONE);

    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(0, R.anim.slide_out_right);
    }
}
