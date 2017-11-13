package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wanglingsheng on 2017/10/26.
 * 评价详情—维保项目和周期
 */

public class Fragment_wbpj_deatil_5 extends Fragment {


    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbpj_detail_5, container, false);

        initView(rootView);
        initListener();

        return rootView;
    }

    private void initListener() {

    }

    private void initView(View rootView) {

    }
}
