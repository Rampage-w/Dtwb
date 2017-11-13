package com.sdhmw.dtwb.main;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.feezu.liuli.timeselector.TimeSelector;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.Utils;
import lecho.lib.hellocharts.view.PieChartView;

/**
 * Created by wanglingsheng on 2017/9/14.
 * 电梯故障率
 */

public class Fragment_sy_dtgzl extends Fragment {

    private Button dtgzl_search;

    private PieChartView pieChart;
    private PieChartData pieChardata;
    private List<SliceValue> values = new ArrayList<SliceValue>();
    private int[] data = {21, 20, 9, 2, 8, 33, 14, 12};


    //时间选择器
    private TextView startTime_tv;
    private TextView endTime_tv;

    private SpannableString ss_start;
    private SpannableString ss_end;
    private String txt_start = "开始时间\n2017-09-21";
    private String txt_end = "结束时间\n2017-09-21";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sy_dtgzl, null);

        initView(rootView);

        setPieChartData();
        initPieChart();

        System.out.println("data:" + data);
        System.out.println("values:" + values);


        Log.e("Fragment_sy_dtgzl", "onCreateView");

        return rootView;

    }

    private void initView(View rootView) {

        //查询
        dtgzl_search = (Button) rootView.findViewById(R.id.dtgzl_search);
        dtgzl_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //重新加载饼图
                Toast.makeText(getActivity(), "电梯故障率查询", Toast.LENGTH_SHORT).show();

            }
        });




        pieChart = (PieChartView) rootView.findViewById(R.id.pie_chart);
        //设置点击事件监听
        pieChart.setOnValueTouchListener(new PieChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int arcIndex, SliceValue value) {
                Toast.makeText(getActivity(), "Selected: " + value.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });



        //时间选择器
        startTime_tv = (TextView) rootView.findViewById(R.id.startTime_tv);


        ss_start = new SpannableString(txt_start);
        ss_start.setSpan(new AbsoluteSizeSpan(30),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        startTime_tv.setText(ss_start);

        startTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();

                        String startTim = time.substring(0,11);


                        txt_start = "开始时间\n" + startTim;
                        ss_start = new SpannableString(txt_start);
                        ss_start.setSpan(new AbsoluteSizeSpan(30),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        startTime_tv.setText(ss_start);

                    }
                },"2015-01-01 00:00","2018-12-31 23:59:59");


                timeSelector.setIsLoop(false);  //设置不循环

//                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
                timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
                timeSelector.show();


            }
        });

        endTime_tv = (TextView) rootView.findViewById(R.id.endTime_tv);


        ss_end = new SpannableString(txt_end);
        ss_end.setSpan(new AbsoluteSizeSpan(30),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        endTime_tv.setText(ss_end);

        endTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();


                        String endTime = time.substring(0,11);

                        txt_end = "结束时间\n" + endTime;
                        ss_end = new SpannableString(txt_end);
                        ss_end.setSpan(new AbsoluteSizeSpan(30),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        endTime_tv.setText(ss_end);

                    }
                },"2015-01-01 00:00","2018-12-31 23:59:59");

                timeSelector.setIsLoop(false);  //设置不循环

//                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
                timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
                timeSelector.show();
            }
        });

    }


    //获取数据
    private void setPieChartData() {
        for (int i = 0; i < data.length; ++i) {
            SliceValue sliceValue = new SliceValue((float) data[i], Utils.pickColor()); //是随机选择颜色的
            values.add(sliceValue);
        }

    }

    // 初始化
    private void initPieChart() {
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);//显示标签
        pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(false);//是否是环形显示
        pieChardata.setValues(values);//填充数据
//        pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
//        pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别
//        pieChardata.setCenterText1("饼图测试");//环形中间的文字1
//        pieChardata.setCenterText1Color(Color.BLACK);//文字颜色
//        pieChardata.setCenterText1FontSize(14);//文字大小
//
//        pieChardata.setCenterText2("饼图测试");
//        pieChardata.setCenterText2Color(Color.BLACK);
//        pieChardata.setCenterText2FontSize(18);

        /**这里也可以自定义你的字体   Roboto-Italic.ttf这个就是你的字体库*/
//      Typeface tf = Typeface.createFromAsset(this.getAssets(), "Roboto-Italic.ttf");
//      data.setCenterText1Typeface(tf);

        pieChart.setPieChartData(pieChardata);
        pieChart.setValueSelectionEnabled(true);//选择饼图某一块变大
        pieChart.setAlpha(0.9f);//设置透明度
        pieChart.setCircleFillRatio(1f);//设置饼图大小


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        values.clear();
    }

}
