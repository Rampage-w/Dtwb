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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.sdhmw.dtwb.model.MyApp;
import com.sdhmw.dtwb.net.WebServiceManager;
import com.sdhmw.dtwb.utils.BarCharts;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/11/7.
 * 维保及时率 MpAndroidChart
 */

public class Fragment_sy_wbjsl2 extends Fragment {


    private View rootView;

    //时间选择器
    private TextView startTime_tv;
    private TextView endTime_tv;
    private Button wbjsl2_search;   //查询

    private String startTim = "";    //开始时间
    private String endTime = "";    //结束时间

    private BarChart mBarChart ;
    private BarData mBarData;
    private BarCharts mBarCharts;

    private String unitid;  //单位id
    private List<Float> list = new ArrayList<>();
    public static String[]
            County = new String[]{};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sy_wbjsl2, container, false);

        initView(rootView);

        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

            startTim = "";
            endTime = "";
            startTime_tv.setText("");
            endTime_tv.setText("");

            //加载数据
            MyApp app = (MyApp) getActivity().getApplicationContext();
            unitid = app.getUnitid();

            getDataThread thread = new getDataThread();
            thread.start();

        } else {
            list.clear();
        }

    }

    class getDataThread extends Thread {

        @Override
        public void run() {
            super.run();

            WebServiceManager service = new WebServiceManager();
            String result = service.getWbdwWbjsl(startTim,endTime,unitid);

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

                JSONObject jo = (JSONObject) msg.obj;
                try {
                    JSONArray ja = jo.getJSONArray("Rows");

                    Log.e("ja", ja+"");

                    List<String> listX = new ArrayList<String>();
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo_item = ja.getJSONObject(i);

                        String type = jo_item.getString("type").equals("null")?"":jo_item.getString("type");
                        Float jsl =
                                Float.parseFloat(jo_item.getString("jsl").equals("null")?"":jo_item.getString("jsl"));

                        listX.add(type);
                        list.add((float) jsl);

                    }
                    County = listX.toArray(new String[0]);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                System.out.println("list:" + list);
                System.out.println("Country:" + County);
                System.out.println("startTim:" + startTim);
                System.out.println("endTime:" + endTime);



                //柱状图

                mBarCharts = new BarCharts();
                mBarChart  = (BarChart) rootView.findViewById(R.id.wbjsl2_mBarChart);

                mBarData = mBarCharts.getBarData(County, list);
                mBarCharts.showBarChart(mBarChart, mBarData);

            }
        }
    };



    private void initView(View rootView) {

        wbjsl2_search = (Button) rootView.findViewById(R.id.wbjsl2_search);
        wbjsl2_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.clear();

                System.out.println("startTim:" + startTim);
                System.out.println("endTime:" + endTime);

                getDataThread thread = new getDataThread();
                thread.start();


            }
        });


        //时间选择器
        startTime_tv = (TextView) rootView.findViewById(R.id.wbjsl2_startTime_tv);
        startTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();

                        startTim = time.substring(0,11);

                        startTime_tv.setText(startTim);

                    }
                },"2015-01-01 00:00","2018-12-31 23:59:59");


                timeSelector.setIsLoop(false);  //设置不循环

//                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
                timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
                timeSelector.show();


            }
        });

        endTime_tv = (TextView) rootView.findViewById(R.id.wbjsl2_endTime_tv);
        endTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();


                        endTime = time.substring(0,11);

                        endTime_tv.setText(endTime);

                    }
                },"2015-01-01 00:00","2018-12-31 23:59:59");

                timeSelector.setIsLoop(false);  //设置不循环

//                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
                timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
                timeSelector.show();
            }
        });

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        list.clear();
    }

}


