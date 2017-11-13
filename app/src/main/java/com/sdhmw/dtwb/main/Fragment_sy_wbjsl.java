package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sdhmw.dtwb.utils.SegmentView;

import org.feezu.liuli.timeselector.TimeSelector;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * Created by wanglingsheng on 2017/9/14.
 *
 *
 * 维保电梯及时率
 */

public class Fragment_sy_wbjsl extends Fragment {

    public static final String[]
            County = new String[]{"半月","季度","半年","年度"};

    private List<Float> list = new ArrayList<Float>();

    private ColumnChartView chart;
    private ColumnChartData data;


    //隐藏不需要
    //segmentview
//    private SegmentView segmentview;

    //时间选择器
    private TextView startTime_tv;
    private TextView endTime_tv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sy_wbjsl, null);

        initView(rootView);
        initData();


        Log.e("Fragment_sy_wbjsl", "onCreateView");

        return rootView;
    }

    private void initData() {
        //把你获取到的或用到的数据填充到集合中
        list.add((float) 20.5);
        list.add((float) 10.5);
        list.add((float) 30.5);
        list.add((float) 40.5);

        setChart();
    }

    private void setChart() {
        // 使用的 8列，每列1个subcolumn。
        int numSubcolumns = 1;
        int numColumns = 4;

        //定义一个圆柱对象集合
        List<Column> columns = new ArrayList<Column>();

        //子列数据集合
        List<SubcolumnValue> values;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();

        //遍历列数numColumns
        for (int i = 0; i < numColumns; ++i) {
            values = new ArrayList<SubcolumnValue>();
            //遍历每一列的每一个子列
            for (int j = 0; j < numSubcolumns; ++j) {

                //为每一柱图添加颜色和数值
                float f = list.get(i);
                values.add(new SubcolumnValue(f, ChartUtils.pickColor()));

            }
            //创建Column对象
            Column column = new Column(values);

            //这一步是能让圆柱标注数据显示带小数的重要一步 让我找了好久问题
            ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(2);
            column.setFormatter(chartValueFormatter);

            //是否有数据标注
            column.setHasLabels(true);

            //是否是点击圆柱才显示数据标注
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);

            //给x轴坐标设置描述
            axisValues.add(new AxisValue(i).setLabel(County[i]));

        }


        //创建一个带有之前圆柱对象column集合的ColumnChartData
        data = new ColumnChartData(columns);

        //定义x轴y轴相应参数

        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("出场率(%)");//轴名称
        axisY.hasLines();//是否显示网格线
        axisY.setTextColor(R.color.tab_color);//颜色

        axisX.hasLines();
        axisX.setTextColor(R.color.tab_color);
        axisX.setValues(axisValues);
//        axisX.setHasTiltedLabels(true);     //斜体
        axisX.setMaxLabelChars(6);      //设置轴标签可显示的最大字符个数，范围在0-32之间。
//        axisX.setInside(true);

        //把X轴Y轴数据设置到ColumnChartData 对象中
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        //给表填充数据，显示出来
        chart.setColumnChartData(data);
    }

    private void initView(View rootView) {

        chart = (ColumnChartView) rootView.findViewById(R.id.chart1_2);

        chart.setZoomEnabled(false);//禁止手势缩放

        chart.setOnValueTouchListener(new ColumnChartOnValueSelectListener() {
            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                Toast.makeText(getActivity(), "Selected：" + value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        });


//        segmentview = (SegmentView) rootView.findViewById(R.id.segmentview);
//        segmentview.setOnSegmentViewClickListener(new SegmentView.onSegmentViewClickListener() {
//            @Override
//            public void onSegmentViewClick(View view, int postion) {
//                switch (postion) {
//                    case 0:
//                        Toast.makeText(getActivity(), "Selected：" + postion, Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 1:
//                        Toast.makeText(getActivity(), "Selected：" + postion, Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case 2:
//                        Toast.makeText(getActivity(), "Selected：" + postion, Toast.LENGTH_SHORT).show();
//
//                        break;
//
//                    case 3:
//                        Toast.makeText(getActivity(), "Selected：" + postion, Toast.LENGTH_SHORT).show();
//
//                        break;
//
//
//                }
//            }
//        });


        //时间选择器
        startTime_tv = (TextView) rootView.findViewById(R.id.startTime_tv);
        startTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();

                        String startTim = time.substring(0,11);

                        startTime_tv.setText(startTim);

                    }
                },"2015-01-01 00:00","2018-12-31 23:59:59");


                timeSelector.setIsLoop(false);  //设置不循环

//                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
                timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
                timeSelector.show();


            }
        });

        endTime_tv = (TextView) rootView.findViewById(R.id.endTime_tv);
        endTime_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();


                        String endTime = time.substring(0,11);

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
