package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.sdhmw.dtwb.model.MenuBean;
import com.sdhmw.dtwb.utils.RecyclerViewAdapter_wd;
import com.sdhmw.dtwb.utils.TitleItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/9/14.
 *
 * 我的
 *
 */

public class Fragment_wd extends Fragment {


    private View rootView;
    private RecyclerView mRecyclerView;
    private List<MenuBean> mDatas;
    private RecyclerViewAdapter_wd mAdapter;


    private ImageView menu_iv;  //右上角的菜单

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wd, null);

        initData();
        initView(rootView);

        return rootView;
    }

    private void initData() {
        mDatas = new ArrayList<MenuBean>();
        mDatas.add(new MenuBean(" ","修改密码",R.drawable.ic_pwd));
        mDatas.add(new MenuBean(" ","重新登录",R.drawable.ic_main));
        mDatas.add(new MenuBean(" ","退出登录",R.drawable.ic_exit));
    }


    private void initView(View rootView) {

        //隐藏
        menu_iv = (ImageView) getActivity().findViewById(R.id.menu_iv);
        menu_iv.setVisibility(View.GONE);


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        mAdapter = new RecyclerViewAdapter_wd(getActivity(), mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        // 设置item分割线
        mRecyclerView.addItemDecoration(new TitleItemDecoration(getActivity(), mDatas));

        //如果add两个，那么按照先后顺序，依次渲染。
//			mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//			mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL));	//添加默认分割线：高度为2px，颜色为灰色
//			mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL, R.drawable.divider_mileage));	//添加自定义分割线：可自定义分割线drawable
//			mRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.divide_gray_color)));	//添加自定义分割线：可自定义分割线高度和颜色

        // 设置item动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        //选中点击的事件
        mAdapter.setOnItemClickLitener(new RecyclerViewAdapter_wd.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),position+" click",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),position+" long click",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
