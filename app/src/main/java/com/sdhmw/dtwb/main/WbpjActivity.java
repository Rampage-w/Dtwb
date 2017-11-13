package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by wanglingsheng on 2017/10/26.
 * 维保评价Activity
 *
 */

public class WbpjActivity extends FragmentActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Fragment重叠解决方案
        //弹出原来保存的所有Fragment状态信息，重新加载
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStackImmediate(null, 1);
        }

        setContentView(R.layout.activity_wbpj);

        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.wbpj_Fragment, new Fragment_wbpj_list());

        ft.commit();

    }
}
