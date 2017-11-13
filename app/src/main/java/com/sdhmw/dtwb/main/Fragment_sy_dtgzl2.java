package com.sdhmw.dtwb.main;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.sdhmw.dtwb.model.MyApp;
import com.sdhmw.dtwb.net.WebServiceManager;
import com.sdhmw.dtwb.utils.BarCharts;
import com.sdhmw.dtwb.utils.PieCharts;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/11/7.
 */

public class Fragment_sy_dtgzl2 extends Fragment {

    private View rootView;
    private PieChart mPiechart;
    private PieData mPieData;
    private PieCharts mPieCharts;

    private TextView startTime_tv;
    private TextView endTime_tv;
    private Button dtgzl2_search;   //查询

    private String startTim = "";    //开始时间
    private String endTime = "";    //结束时间


    private String unitid;  //单位id
    private List<Float> list = new ArrayList<Float>();
    public static String[]
            Country = new String[]{"半月维保","季度维保","半年维保","年度维保"};


    private String[] color = {"#33B5E5","#AA66CC","#99CC00","#FFBB33","#FF4444","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_sy_dtgzl2, container, false);

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
            String result = service.getDtGzl(startTim,endTime,unitid);

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

                        String type = jo_item.getString("leibie").equals("null")?"":jo_item.getString("leibie");

                        Float jsl =
                                Float.parseFloat(jo_item.getString("jsl").equals("null")?"":jo_item.getString("jsl"));

                        listX.add(type);
                        list.add((float) jsl);

                    }
                    Country = listX.toArray(new String[0]);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println("list:" + list);
                System.out.println("Country:" + Country);

                //饼图
                mPiechart = (PieChart) rootView.findViewById(R.id.dtgzl2_pie_chart);

                //此方法不行 撇弃
//        mPieData = mPieCharts.getPieData(4, 100, getResources());
//        mPieCharts.showPieChart(mPiechart, mPieData);

                mPieData = getPieData(Country, list);
                showPieChart(mPiechart, mPieData);

            }
        }
    };

    private void initView(View rootView) {
//        mPiechart = (PieChart) rootView.findViewById(R.id.dtgzl2_pie_chart);
//
//        //此方法不行 撇弃
////        mPieData = mPieCharts.getPieData(4, 100, getResources());
////        mPieCharts.showPieChart(mPiechart, mPieData);
//
//        mPieData = getPieData(Country, list);
//        showPieChart(mPiechart, mPieData);


        dtgzl2_search = (Button) rootView.findViewById(R.id.dtgzl2_search);
        dtgzl2_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.clear();

                System.out.println("startTim:" + startTim);
                System.out.println("endTime:" + endTime);

                //开启线程
                getDataThread thread = new getDataThread();
                thread.start();


            }
        });


        //时间选择器
        startTime_tv = (TextView) rootView.findViewById(R.id.dtgzl2_startTime_tv);
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

        endTime_tv = (TextView) rootView.findViewById(R.id.dtgzl2_endTime_tv);
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



    private void showPieChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription("饼状图");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("电梯故障率");  //饼状图中间的文字
        pieChart.setCenterTextSize(15);
        pieChart.setCenterTextColor(Color.parseColor("#696969"));
        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }


    private PieData getPieData(String[] Country, List<Float> list) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        for (int i = 0; i < Country.length; i++) {
//            xValues.add("Quarterly" + (i + 1));  //饼块上显示成 Quarterly1, Quarterly2, Quarterly3, Quarterly4

            xValues.add(Country[i]);
        }

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */

//        float quarterly1 = 14;
//        float quarterly2 = 14;
//        float quarterly3 = 34;
//        float quarterly4 = 38;
//
//        yValues.add(new Entry(quarterly1, 0));
//        yValues.add(new Entry(quarterly2, 1));
//        yValues.add(new Entry(quarterly3, 2));
//        yValues.add(new Entry(quarterly4, 3));


        for (int i=0;i<Country.length;i++) {

            yValues.add(new Entry(list.get(i), i));

        }





        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "电梯分类"/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
//        colors.add(Color.rgb(205, 205, 205));
//        colors.add(Color.rgb(114, 188, 223));
//        colors.add(Color.rgb(255, 123, 124));
//        colors.add(Color.rgb(57, 135, 200));

        for(int i=0;i<Country.length;i++) {
            colors.add(Color.parseColor(color[i]));
        }


        pieDataSet.setColors(colors);
        pieDataSet.setValueTextColor(Color.parseColor("#000000"));
        pieDataSet.setValueTextSize(12);

        DisplayMetrics metrics = getResources().getDisplayMetrics();

        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        list.clear();

    }
}
