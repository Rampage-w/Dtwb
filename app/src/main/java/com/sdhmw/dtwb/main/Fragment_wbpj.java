package com.sdhmw.dtwb.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by wanglingsheng on 2017/10/24.
 * 维保评价
 */

public class Fragment_wbpj extends Fragment {

    private View rootView;
    private Button wbpj_cx_btn; //查询


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_wbpj,container,false);

        initView(rootView);
        initListener();

        return rootView;

    }

    private void initListener() {

        wbpj_cx_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //跳转到 维保评价 Activity
                Intent intent = new Intent(getActivity(), WbpjActivity.class);
                startActivity(intent);

            }
        });
    }

    private void initView(View rootView) {

        wbpj_cx_btn = (Button) rootView.findViewById(R.id.wbpj_cx_btn);
    }

}
