package com.sdhmw.dtwb.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.sdhmw.dtwb.utils.RecyclerViewAdapter_bggl_dbrw;

import java.util.ArrayList;

/**
 * Created by wanglingsheng on 2017/10/16.
 * 代办任务
 */

public class Fragment_bggl_dbrw extends Fragment {

    private View rootView;
    private Button btn_plth;
    private Button btn_plsh;

    private ArrayList<String> mList = new ArrayList<String>();  //数据集

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter_bggl_dbrw mAdapter;
    private CheckBox select_checkbox_head;  //头部checkBox
    private boolean isHeadChecked = false;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_bggl_dbrw, container, false);

        initData();
        initView(rootView);

        return rootView;

    }

    private void initData() {
        mList.clear();

        for(int i = 0; i < 10 ; i++){
            mList.add("条目" + i );
        }

    }

    private void initView(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_wbgl_dbrw);

        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerViewAdapter_bggl_dbrw(getActivity(), mList);

        mRecyclerView.setAdapter(mAdapter);


        select_checkbox_head = (CheckBox) rootView.findViewById(R.id.select_checkbox_head);


        select_checkbox_head.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.e("onCheckedChanged", "isChecked:"+isChecked);

                //区分人为选中或者setChecked
                if(select_checkbox_head.isPressed()){

                    Log.e("isPressed", "isPressed");

                    mAdapter.getSelectedItemAll(isChecked);

                    //(单击全选的时候，实现全选功能)
                    mAdapter.notifyDataSetChanged();

                }else {

                    //把切换fragment取消全选和取消某一项的取消全选区分开
                    if(!isHeadChecked){

                        //切换fragment的时候取消全选按钮
                        if(isChecked){
                            select_checkbox_head.setChecked(false);
                        }
                    }

                }


            }
        });

        select_checkbox_head.setChecked(false); //重置false

        //item的点击
        mAdapter.setOnItemClickLitener(new RecyclerViewAdapter_bggl_dbrw.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), position+"	click", Toast.LENGTH_SHORT).show();


                //跳转到 报告管理Activity
                Intent intent = new Intent(getActivity(), BgglActivity.class);
                intent.putExtra("item","0");    //代办任务跳转
                startActivity(intent);


                //动画(老款)
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.animo_no);



            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void onItemCheckBoxClick(View view, int position) {

                //点击左侧的checkbox
                Toast.makeText(getActivity(), position+"	checkBox click", Toast.LENGTH_SHORT).show();

                //因为重新定义了checkbox的接口，后台的方法不走了，这里重新判断
                if(mAdapter.isItemChecked(position)){
                    mAdapter.setItemChecked(position, false);
                }else {
                    mAdapter.setItemChecked(position, true);
                }

                //当取消选中某一项，取消全选
                if(mAdapter.getSelectedItem().size() == mAdapter.getItemCount()){

                    isHeadChecked = true;

                    Log.e("----------", "-----------");
                    select_checkbox_head.setChecked(true);
                }else {
                    Log.e("==========", "============");
                    select_checkbox_head.setChecked(false);
                }


            }
        });


        btn_plth = (Button) rootView.findViewById(R.id.btn_plth);
        btn_plsh = (Button) rootView.findViewById(R.id.btn_plsh);

        //批量退回
        btn_plth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //显示dialog
                DialogFragment_bggl_plth mDialogFragment = new DialogFragment_bggl_plth();
                removeSameFragment("DialogFragment_bggl_plth");
                mDialogFragment.show(getFragmentManager(),"DialogFragment_bggl_plth");


            }
        });

        //批量审核
        btn_plsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }



    //移除相同 fragment
    private void removeSameFragment(String str){
        FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(str);
        //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
        if(fragment != null){
            mFragTransaction.remove(fragment);
        }
    }

}
