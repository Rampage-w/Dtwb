package com.sdhmw.dtwb.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;


import com.sdhmw.dtwb.model.MyApp;
import com.sdhmw.dtwb.net.WebServiceManager;
import com.sdhmw.dtwb.utils.ConnectTimeoutDialog;
import com.sdhmw.dtwb.utils.RecycleViewDivider;
import com.sdhmw.dtwb.utils.RecyclerViewAdapter_wbgl_list;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wanglingsheng on 2017/11/8.
 * 维保管理-查询的-list (RecycleView list )  Fragment_wbgl_list 撇弃
 */

public class Fragment_wbgl_list2 extends Fragment {


    private ProgressDialog progressDialog;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager layoutManager;
    private RecyclerViewAdapter_wbgl_list mAdapter;


    private final String TAG = "Fragment_wbgl_list";

    private View rootView;

    private static final Map<String, String> mMap = new LinkedHashMap<>();  //保存插入的顺序

    private LinearLayout.LayoutParams mLayoutParams = null;
    private LinearLayout mLinearLayout;
    private TableLayout mTableLayout;

    private ArrayList<Object> mList = new ArrayList<Object>();
    private ArrayList<Object> mListAll = new ArrayList<Object>();   //总得list

    private String unitid;
    private String sbsydw;
    private String zcdm;
    private String quxian;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbgl_list2, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_wbgl_list);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        //添加默认分割线：高度为2px，颜色为灰色
        mRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));

        //添加自定义分割线：可自定义分割线drawable
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(
//                getActivity(), LinearLayoutManager.VERTICAL, R.drawable.divider_mileage));

        //添加自定义分割线：可自定义分割线高度和颜色
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(
//                getActivity(), LinearLayoutManager.VERTICAL, 10, getResources().getColor(R.color.bootstrap_gray)));


        initData();

        return rootView;

    }

    private void initData() {
        mList.clear();
        mListAll.clear();

        MyApp app = (MyApp) getActivity().getApplicationContext();
        unitid = app.getUnitid();

        //拿到传值
        Bundle bundle = getArguments();
        sbsydw = bundle.getString("sbsydw");
        zcdm = bundle.getString("zcdm");
        quxian = bundle.getString("quxian");


        System.out.println("unitid:" + unitid);
        System.out.println("sbsydw:" + sbsydw);
        System.out.println("zcdm:" + zcdm);
        System.out.println("quxian:" + quxian);



        //进度条
        showProgressDialog("","正在加载...");

        getDataThread thread = new getDataThread();
        thread.start();

    }



    class getDataThread extends Thread {

        @Override
        public void run() {
            super.run();

            WebServiceManager service = new WebServiceManager();
            String result = service.getWbdwEle(unitid,quxian,sbsydw,zcdm);

            Log.e("result", result+"");


            //判断超时，提示网络异常，直接退出跳到登录页面。
            if(result.equals("timeout") || result.equals("netError")){

                Message msg = new Message();
                msg.what = 0;
                handler.sendMessage(msg);

            }else{

                try {
                    JSONObject jo = new JSONObject(result);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = jo;
                    handler.sendMessage(msg);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
    }


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                hideProgressDialog();

                try {
                    JSONObject jo = (JSONObject) msg.obj;
                    JSONArray ja = jo.getJSONArray("Rows");

                    Log.e("ja", ja+"");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject jo1 = ja.getJSONObject(i);
//                Map<String,Object> tempMap = new LinkedHashMap<>();
                        Map<String, Object> tempMap = new HashMap<String, Object>();
                        tempMap.put("zcdm", jo1.getString("zcdm").equals("null")?"":jo1.getString("zcdm"));
                        tempMap.put("sbsydd", jo1.getString("sbsydd").equals("null")?"":jo1.getString("sbsydd"));
                        tempMap.put("Create_date", jo1.getString("Create_date").equals("null")?"":jo1.getString("Create_date"));
                        tempMap.put("id", jo1.getString("id").equals("null")?"":jo1.getString("id"));
                        mList.add(tempMap);
                    }
                    mListAll.clear();
                    mListAll.addAll(mList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //填充数据

                mAdapter = new RecyclerViewAdapter_wbgl_list(getActivity(), mListAll);
                mRecyclerView.setAdapter(mAdapter);
                //item的点击
                mAdapter.setOnItemClickLitener(new RecyclerViewAdapter_wbgl_list.OnItemClickLitener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity(), position+"	click", Toast.LENGTH_SHORT).show();

                        if (mListAll.size() != 0) {     //如果adapter绑定的数据不为空

                            if (mList.size() != position) {

                                Map map = (Map) mList.get(position);

                                String zcdm = (String) map.get("zcdm");
                                String sbsydd = (String) map.get("sbsydd");
                                String Create_date = (String) map.get("Create_date");

                                System.out.println("zcdm:" + zcdm);
                                System.out.println("sbsydd:" + sbsydd);
                                System.out.println("Create_date:" + Create_date);


                                DialogFragment_wbgl_wbzl mDialogFragment = new DialogFragment_wbgl_wbzl();

                                Bundle bundle = new Bundle();
                                bundle.putString("zcdm", zcdm);
                                bundle.putString("sbsydd", sbsydd);
                                bundle.putString("Create_date", Create_date);
                                mDialogFragment.setArguments(bundle);

                                removeSameFragment("DialogFragment_wbgl_wbzl");
                                mDialogFragment.show(getFragmentManager(),"DialogFragment_wbgl_wbzl");



                            } else {

                            }

                        }


                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });


            } else if (msg.what == 0) {

                hideProgressDialog();
                //连接超时，请重新登录！
                ConnectTimeoutDialog.TimeoutDialog(getActivity());

            }
        }
    };



    /*
     * 提示加载
     */
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {

            progressDialog = ProgressDialog.show(getActivity(), title,
                    message, true, false);

        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
        }
        progressDialog.show();
        progressDialog.setCancelable(true);
    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.e(TAG, "onDestroyView");

        mList.clear();
        mListAll.clear();
    }

}
