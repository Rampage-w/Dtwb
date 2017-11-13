package com.sdhmw.dtwb.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.sdhmw.dtwb.net.WebServiceManager;
import com.sdhmw.dtwb.utils.ConnectTimeoutDialog;
import com.sdhmw.dtwb.utils.RecyclerViewAdapter_wbgl_list;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglingsheng on 2017/9/26.
 * 维保管理 维保基本信息
 */

public class Fragment_wbgl_wb_1 extends Fragment {


    private final String TAG = "维保管理 维保基本信息";

    private View rootView;
    private ProgressDialog progressDialog;
    private ArrayList<Object> mList = new ArrayList<Object>();
    private ArrayList<Object> mListAll = new ArrayList<Object>();   //总得list

    private String getZcdm;    //注册代码
    private AwesomeTextView zcdm;   //
    private AwesomeTextView name;   //
    private AwesomeTextView quxian;   //
    private AwesomeTextView sbsydw;   //
    private AwesomeTextView sbsydd;   //
    private AwesomeTextView dwnbbh;   //
    private AwesomeTextView ccbh;   //
    private AwesomeTextView scwbrq;   //
    private AwesomeTextView xcjyrq;   //
    private AwesomeTextView scwbwt;   //

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            Bundle bundle = getArguments();
            getZcdm = bundle.getString("zcdm");

            System.out.println(TAG + "    getZcdm:" + getZcdm);

            showProgressDialog("", "正在加载...");

            getDataThread thread = new getDataThread();
            thread.start();

        } else {
            mList.clear();
            mListAll.clear();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbgl_wb_1, container, false);

//        initView(rootView);

        return rootView;


    }

    private void initView(View rootView) {

        zcdm = (AwesomeTextView) rootView.findViewById(R.id.zcdm);
        name = (AwesomeTextView) rootView.findViewById(R.id.name);
        quxian = (AwesomeTextView) rootView.findViewById(R.id.quxian);
        sbsydw = (AwesomeTextView) rootView.findViewById(R.id.sbsydw);
        sbsydd = (AwesomeTextView) rootView.findViewById(R.id.sbsydd);
        dwnbbh = (AwesomeTextView) rootView.findViewById(R.id.dwnbbh);
        ccbh = (AwesomeTextView) rootView.findViewById(R.id.ccbh);
        scwbrq = (AwesomeTextView) rootView.findViewById(R.id.scwbrq);  //无
        xcjyrq = (AwesomeTextView) rootView.findViewById(R.id.xcjyrq);
        scwbwt = (AwesomeTextView) rootView.findViewById(R.id.scwbwt);  //无
    }


    class getDataThread extends Thread {

        @Override
        public void run() {
            super.run();

            WebServiceManager service = new WebServiceManager();
            String result = service.getElexq(getZcdm);

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
                        Map<String, Object> tempMap = new HashMap<String, Object>();
                        tempMap.put("zcdm", jo1.getString("zcdm").equals("null")?"":jo1.getString("zcdm"));
                        tempMap.put("sbsydd", jo1.getString("sbsydd").equals("null")?"":jo1.getString("sbsydd"));
                        tempMap.put("Create_date", jo1.getString("Create_date").equals("null")?"":jo1.getString("Create_date"));
                        tempMap.put("id", jo1.getString("id").equals("null")?"":jo1.getString("id"));
                        mList.add(tempMap);
                    }

                    initView(rootView);
                    JSONObject jo1 = ja.getJSONObject(0);

                    zcdm.setText(jo1.getString("zcdm").equals("null")?"":jo1.getString("zcdm"));
                    name.setText(jo1.getString("name").equals("null")?"":jo1.getString("name"));
                    quxian.setText(jo1.getString("quxian").equals("null")?"":jo1.getString("quxian"));
                    sbsydw.setText(jo1.getString("sbsydw").equals("null")?"":jo1.getString("sbsydw"));
                    sbsydd.setText(jo1.getString("sbsydd").equals("null")?"":jo1.getString("sbsydd"));
                    dwnbbh.setText(jo1.getString("dwnbbh").equals("null")?"":jo1.getString("dwnbbh"));
                    ccbh.setText(jo1.getString("ccbh").equals("null")?"":jo1.getString("ccbh"));
//                    scwbrq.setText(jo1.getString("scwbrq").equals("null")?"":jo1.getString("scwbrq"));
                    xcjyrq.setText(jo1.getString("xcjyrq").equals("null")?"":jo1.getString("xcjyrq"));
//                    scwbwt.setText(jo1.getString("scwbwt").equals("null")?"":jo1.getString("scwbwt"));



                    mListAll.clear();
                    mListAll.addAll(mList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }







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


}
