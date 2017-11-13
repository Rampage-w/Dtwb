package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.sdhmw.dtwb.model.MyApp;
import com.sdhmw.dtwb.net.WebServiceManager;
import com.sdhmw.dtwb.utils.BarCharts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/11/7.
 * MpAndroidChart
 */

public class Fragment_sy_wbdtsl2 extends Fragment {

    private View rootView;

    private BarChart mBarChart ;
    private BarData mBarData;
    private BarCharts mBarCharts;

    private final String TAG = "Fragment_sy_wbdtsl2";

    private String unitid;  //单位id
    private List<Float> list = new ArrayList<>();
    public static String[]
            County = new String[]{};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_sy_wbdtsl2, container, false);

        System.out.println(TAG+"    onCreateView");

//        initView(rootView);

        return rootView;
    }


    //fragment 可见的时候执行 在onCreateView前面
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.e(TAG, "显示");
            Log.e(TAG, "list:" + list);


            //加载数据
            MyApp app = (MyApp) getActivity().getApplicationContext();
            unitid = app.getUnitid();
            System.out.println(TAG + " unitid:" + unitid);

//            showProgressDialog("", "正在获取数据...");
            getDataThread thread = new getDataThread();
            thread.start();

        } else {
            Log.e(TAG, "隐藏");

            list.clear();
        }


    }

    private void initView(View rootView) {

        mBarCharts = new BarCharts();
        mBarChart  = (BarChart) rootView.findViewById(R.id.wbdtsl2_mBarChart);

        mBarData = mBarCharts.getBarData(County, list);
        mBarCharts.showBarChart(mBarChart, mBarData);

    }


    class getDataThread extends Thread {

        @Override
        public void run() {
            super.run();


            WebServiceManager service = new WebServiceManager();
            String result = service.getWbdwIndex(unitid);

            Log.e("result", result+"");

            try {
                JSONObject jo = new JSONObject(result);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = jo;
                handler.sendMessage(msg);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {

//                hideProgressDialog();

                System.out.println("handler--><><><>");

                JSONObject jo = (JSONObject) msg.obj;


                try {
                    JSONArray ja = jo.getJSONArray("Rows");


                    Log.e("ja", ja+"");

                    List<String> quxian_list = new ArrayList<String>();
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo_item = ja.getJSONObject(i);

                        String quxian = jo_item.getString("quxian").equals("null")?"":jo_item.getString("quxian");
                        Float quxian_count =
                                Float.parseFloat(jo_item.getString("quxian_count").equals("null")?"":jo_item.getString("quxian_count"));


                        quxian_list.add(quxian);
                        list.add((float) quxian_count);

                    }
                    County = quxian_list.toArray(new String[0]);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                System.out.println("list:" + list);
                System.out.println("Country:" + County);


                //柱状图


                mBarCharts = new BarCharts();
                mBarChart  = (BarChart) rootView.findViewById(R.id.wbdtsl2_mBarChart);

                mBarData = mBarCharts.getBarData(County, list);
                mBarCharts.showBarChart(mBarChart, mBarData);

            }
        }
    };

}
