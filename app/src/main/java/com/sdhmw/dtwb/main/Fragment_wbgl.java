package com.sdhmw.dtwb.main;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.sdhmw.dtwb.net.WebServiceManager;

import org.feezu.liuli.timeselector.TimeSelector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;

/**
 * Created by Carson_Ho on 16/5/23.
 * 维保管理
 */
public class Fragment_wbgl extends Fragment {

    private View rootView;
//    private TextView wbgl_startTime_tv;
//    private TextView wbgl_endTime_tv;


//    private SpannableString ss_start;
//    private SpannableString ss_end;
//    private String txt_start = "维保开始时间\n2017-09-21";
//    private String txt_end = "维保结束时间\n2017-09-21";

    //查询按钮
    private Button wbgl_search_btn;


    private EditText wbgl_sbsydw_et;
    private EditText wbgl_zcdm_et;

    private TextView wbgl_szqy_tv;  //所在区域 点击 dialog列表
    private String szqy = "";;

    //区县
    private static String[] quxian = new String[]{};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wbgl,container,false);


        System.out.println("szqy:" + szqy);

        initView(rootView);
        initListener();

        return rootView;
    }

    private void initListener() {

        wbgl_szqy_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //获取区县 线程
                getSzqyThread  quxianThread = new getSzqyThread();
                quxianThread.start();

            }
        });
    }

    private void initView(View rootView) {

        wbgl_sbsydw_et = (EditText) rootView.findViewById(R.id.wbgl_sbsydw_et);
        wbgl_zcdm_et = (EditText) rootView.findViewById(R.id.wbgl_zcdm_et);
        //区县
        wbgl_szqy_tv = (TextView) rootView.findViewById(R.id.wbgl_szqy_tv);

//        wbgl_startTime_tv = (TextView) rootView.findViewById(R.id.wbgl_startTime_tv);
//        wbgl_endTime_tv = (TextView) rootView.findViewById(R.id.wbgl_endTime_tv);
//
//        ss_start = new SpannableString(txt_start);
//        ss_start.setSpan(new AbsoluteSizeSpan(30),0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wbgl_startTime_tv.setText(ss_start);

//        wbgl_startTime_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
//                    @Override
//                    public void handle(String time) {
//                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
//
//                        String startTim = time.substring(0,11);
//
//
//                        txt_start = "维保开始时间\n" + startTim;
//                        ss_start = new SpannableString(txt_start);
//                        ss_start.setSpan(new AbsoluteSizeSpan(30),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                        wbgl_startTime_tv.setText(ss_start);
//
//                    }
//                },"2015-01-01 00:00","2018-12-31 23:59:59");
//
//
//                timeSelector.setIsLoop(false);  //设置不循环
//
////                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
//                timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
//                timeSelector.show();
//
//
//            }
//        });
//
//
//        ss_end = new SpannableString(txt_end);
//        ss_end.setSpan(new AbsoluteSizeSpan(30),0,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        wbgl_endTime_tv.setText(ss_end);
//
//        wbgl_endTime_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
//                    @Override
//                    public void handle(String time) {
//                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();
//
//
//                        String endTime = time.substring(0,11);
//
//                        txt_end = "维保结束时间\n" + endTime;
//                        ss_end = new SpannableString(txt_end);
//                        ss_end.setSpan(new AbsoluteSizeSpan(30),0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                        wbgl_endTime_tv.setText(ss_end);
//
//                    }
//                },"2015-01-01 00:00","2018-12-31 23:59:59");
//
//                timeSelector.setIsLoop(false);  //设置不循环
//
////                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
//                timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
//                timeSelector.show();
//            }
//        });



        //查询按钮
        wbgl_search_btn = (Button) rootView.findViewById(R.id.wbgl_search_btn);
        wbgl_search_btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(getActivity(), WbglActivity.class);
                intent.putExtra("sbsydw", wbgl_sbsydw_et.getText().toString().trim());    //使用单位
                intent.putExtra("zcdm", wbgl_zcdm_et.getText().toString().trim());    //注册代码
                intent.putExtra("quxian", szqy);    //所在区域
                startActivity(intent);

                //动画(老款)
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.animo_no);

                //5.0可以用
//                startActivity(intent, makeSceneTransitionAnimation(getActivity()).toBundle());

            }
        });
    }


    //区县
    class getSzqyThread extends Thread {

        @Override
        public void run() {
            super.run();

            WebServiceManager service = new WebServiceManager();
            String result = service.getQuxian();

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

                        String id = jo_item.getString("id").equals("null")?"":jo_item.getString("id");
                        String quxianname = jo_item.getString("quxianname").equals("null")?"":jo_item.getString("quxianname");

                        listX.add(quxianname);

                    }
                    quxian = listX.toArray(new String[0]);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                final String[] items = {"东营","广饶","垦利","河口"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("标题");
                dialog.setSingleChoiceItems(quxian, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), quxian[i], Toast.LENGTH_SHORT).show();

                        szqy = quxian[i];
                    }
                });

                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        wbgl_szqy_tv.setText(szqy);

                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();

            }
        }
    };



}
