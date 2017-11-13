package com.sdhmw.dtwb.main;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Window;

import com.sdhmw.dtwb.utils.CrashHandler;

/**
 * Created by wanglingsheng on 2017/9/21.
 * <p>
 * 维保管理 总得Activity（里面的fragment建立在这个之上）
 */

@SuppressWarnings("ResourceType")
public class WbglActivity extends FragmentActivity {


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        CrashHandler.addActivity(WbglActivity.this);


        //允许使用transitions
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        //进入动画
//        Slide slide = new Slide();
//        slide.setDuration(1000);
//        getWindow().setEnterTransition(slide);

//        Explode explode = new Explode();
//        explode.setDuration(500);
//        getWindow().setEnterTransition(explode);

//        Fade fade = new Fade();
//        fade.setDuration(200);
//        getWindow().setEnterTransition(fade);



        //Fragment重叠解决方案
        //弹出原来保存的所有Fragment状态信息，重新加载
        if (savedInstanceState != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStackImmediate(null, 1);
        }


        setContentView(R.layout.activity_wbgl);


        String sbsydw = getIntent().getStringExtra("sbsydw");
        String zcdm = getIntent().getStringExtra("zcdm");
        String quxian = getIntent().getStringExtra("quxian");


//        Fragment_wbgl_list fragment = new Fragment_wbgl_list();
        Fragment_wbgl_list2 fragment = new Fragment_wbgl_list2();

        Bundle bundle = new Bundle();
        bundle.putString("sbsydw", sbsydw);
        bundle.putString("zcdm", zcdm);
        bundle.putString("quxian", quxian);
        fragment.setArguments(bundle);


        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.wbgl_Fragment, fragment);

        // addToBackStack添加到回退栈,addToBackStack与ft.add(R.id.fragment, new
        // MyFragment())效果相当
        // ft.addToBackStack("test");

        ft.commit();

    }


    //结束动画
    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(0, R.anim.slide_out_right);
    }
}
