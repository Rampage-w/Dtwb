package com.sdhmw.dtwb.utils;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.sdhmw.dtwb.main.R;

/**
 * Created by wanglingsheng on 2017/11/7.
 *
 * MPAndroidChart 图表属性
 *
 */

public class BarCharts {

    private String[] color = {"#33B5E5","#AA66CC","#99CC00","#FFBB33","#FF4444","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD","#C4FF8E","#FFF88D","#FFD38C","#8CEBFF","#FF8F9D","#6BF3AD"};

    public void showBarChart(BarChart barChart, BarData barData) {
        // 数据描述
        barChart.setDescription("");
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("You need to provide data for the chart.");
        // 是否显示表格颜色
        barChart.setDrawGridBackground(false);
        // 设置是否可以触摸
        barChart.setTouchEnabled(true);
        // 是否可以拖拽
        barChart.setDragEnabled(false);
        // 是否可以缩放
        barChart.setScaleEnabled(false);
        // 集双指缩放
        barChart.setPinchZoom(false);
        // 设置背景
        barChart.setBackgroundColor(Color.parseColor("#01000000"));
        // 如果打开，背景矩形将出现在已经画好的绘图区域的后边。
        barChart.setDrawGridBackground(false);
        // 集拉杆阴影
        barChart.setDrawBarShadow(false);
        // 图例
        barChart.getLegend().setEnabled(false);
        // 设置数据
        barChart.setData(barData);

        // 隐藏右边的坐标轴 (就是右边的0 - 100 - 200 - 300 ... 和图表中横线)
        barChart.getAxisRight().setEnabled(false);
        // 隐藏左边的左边轴 (同上)
//        barChart.getAxisLeft().setEnabled(false);

        // 网格背景颜色
        barChart.setGridBackgroundColor(Color.parseColor("#00000000"));
        // 是否显示表格颜色
        barChart.setDrawGridBackground(false);
        // 设置边框颜色
        barChart.setBorderColor(Color.parseColor("#00000000"));
        // 说明颜色
        barChart.setDescriptionColor(Color.parseColor("#00000000"));
        // 拉杆阴影
        barChart.setDrawBarShadow(false);
        // 打开或关闭绘制的图表边框。（环绕图表的线）
        barChart.setDrawBorders(false);


        //图标的下边的指示块  图例
        Legend mLegend = barChart.getLegend(); // 设置比例图标示
        // 设置窗体样式
        mLegend.setForm(LegendForm.CIRCLE);
        // 字体
        mLegend.setFormSize(4f);
        // 字体颜色
        mLegend.setTextColor(Color.parseColor("#00000000"));


        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);


        barChart.animateY(1000); // 立即执行的动画,Y轴
    }
    public BarData getBarData(String[] Country, List<Float> list) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < Country.length; i++) {
//            xValues.add(""+(i+1)+"周");// 设置每个壮图的文字描述
            xValues.add(Country[i]);

        }
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        for (int i = 0; i < Country.length; i++) {
//            float value = (float) (Math.random() * range/*100以内的随机数*/) + 3;
            float value = list.get(i);

            yValues.add(new BarEntry(value, i));
        }
        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, "测试饼状图");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int i = 0;i < Country.length ;i++){
            colors.add(Color.parseColor(color[i]));
        }
        barDataSet.setColors(colors);
        // 设置栏阴影颜色
        barDataSet.setBarShadowColor(Color.parseColor("#01000000"));
        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
        barDataSets.add(barDataSet);

        barDataSet.setValueTextColor(Color.parseColor("#696969"));  //柱状图顶部字体颜色
        barDataSet.setValueTextSize(15);    //柱状图顶部字体大小

        // 绘制值
        barDataSet.setDrawValues(true);
        BarData barData = new BarData(xValues, barDataSets);
        return barData;
    }



}
