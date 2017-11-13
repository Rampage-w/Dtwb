package com.sdhmw.dtwb.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sdhmw.dtwb.model.MyApp;
import com.sdhmw.dtwb.net.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
 * 维保电梯数量
 */

public class Fragment_sy_wbdtsl extends Fragment {

    public static String[]
            County = new String[]{"全市","东营区","经济开发区","东营港经济开放区","农业高新技术产业示范区","垦利区","广饶县","利津县"};



    private List<Float> list = new ArrayList<>();

    private ColumnChartView chart;
    private ColumnChartData data;

    private ProgressDialog progressDialog;

    private final String TAG = "维保电梯数量";

    private String unitid;  //单位id


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sy_wbdtsl, null);

        initView(rootView);

        Log.e(TAG, "onCreateView");

        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            Log.e(TAG, "显示");

            //加载数据
            MyApp app = (MyApp) getActivity().getApplicationContext();
            unitid = app.getUnitid();
            System.out.println(TAG + " unitid:" + unitid);


//            showProgressDialog("", "正在获取数据...");
            getDataThread thread = new getDataThread();
            thread.start();



        } else {
            Log.e(TAG, "隐藏");
        }

    }

    private void initData() {
        //把你获取到的或用到的数据填充到集合中
//        list.add((float) 2.5);
//        list.add((float) 3.5);
//        list.add((float) 4.5);
//        list.add((float) 5.5);
//        list.add((float) 6.5);
//        list.add((float) 7.5);
//        list.add((float) 8.5);
//        list.add((float) 9.5);

        setChart();

//        showProgressDialog("", "正在获取数据...");
//        pd.setCancelable(true);

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


                //动画
                setChart();

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



    private void setChart() {
        // 使用的 8列，每列1个subcolumn。
        int numSubcolumns = 1;
        int numColumns = County.length;

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
//                values.add(new SubcolumnValue(f, ChartUtils.pickColor()));

//                values.add(new SubcolumnValue(f, ChartUtils.pickColor()).setTarget((float) Math.random() * 100));
                values.add(new SubcolumnValue(0, ChartUtils.pickColor()).setTarget(f));

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

//        chart.cancelDataAnimation();


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


        chart.startDataAnimation(2000);
    }

    private void initView(View rootView) {

        chart = (ColumnChartView) rootView.findViewById(R.id.chart1_1);

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
