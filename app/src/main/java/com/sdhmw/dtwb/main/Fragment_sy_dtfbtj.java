package com.sdhmw.dtwb.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
 * Created by wanglingsheng on 2017/10/23.
 * 电梯分布统计
 */
public class Fragment_sy_dtfbtj extends Fragment {

    public static final String[]
            County = new String[]{"全市","东营区","经济开发区","东营港经济开放区","农业高新技术产业示范区","垦利区","广饶县","利津县"};

    private List<Float> list = new ArrayList<>();

    private ColumnChartView chart;
    private ColumnChartData data;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sy_dtfbtj, null);

        initView(rootView);
        initData();


        Log.e("Fragment_sy_dtfbtj", "onCreateView");


        return rootView;
    }


    private void initData() {
        //把你获取到的或用到的数据填充到集合中
        list.add((float) 2);
        list.add((float) 3);
        list.add((float) 4);
        list.add((float) 5);
        list.add((float) 6);
        list.add((float) 7);
        list.add((float) 8);
        list.add((float) 9);

        setChart();
    }

    private void setChart() {
        // 使用的 8列，每列1个subcolumn。
        int numSubcolumns = 1;
        int numColumns = 8;

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
        axisX.setHasTiltedLabels(true);     //斜体
        axisX.setMaxLabelChars(6);      //设置轴标签可显示的最大字符个数，范围在0-32之间。
//        axisX.setInside(true);

        //把X轴Y轴数据设置到ColumnChartData 对象中
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        //给表填充数据，显示出来
        chart.setColumnChartData(data);
    }

    private void initView(View rootView) {

        chart = (ColumnChartView) rootView.findViewById(R.id.chart_sy_dtfbtj);

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

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        list.clear();

    }
}





