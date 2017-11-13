package com.sdhmw.dtwb.main;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.sdhmw.dtwb.model.MyApp;
import com.sdhmw.dtwb.net.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglingsheng on 2017/9/22.
 * 维保管理-查询的-list
 */

@SuppressWarnings("ResourceType")
public class Fragment_wbgl_list extends Fragment {

    private final String TAG = "Fragment_wbgl_list";


    private View rootView;

    private static final Map<String, String> mMap = new LinkedHashMap<>();  //保存插入的顺序

    private LinearLayout.LayoutParams mLayoutParams = null;
    private LinearLayout mLinearLayout;
    private TableLayout mTableLayout;

    private ArrayList<Object> mList = new ArrayList<Object>();
    private ArrayList<Object> mListAll = new ArrayList<Object>();   //总得list


    private String unitid;
    private String sbsydw;
    private String zcdm;
    private String quxian;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbgl_list, container, false);


        System.out.println(TAG+"    onCreateView");


        initView(rootView);
//        setTable();
        initData();

        return rootView;

    }

    private void initData() {

        MyApp app = (MyApp) getActivity().getApplicationContext();
        unitid = app.getUnitid();

        //拿到传值
        Bundle bundle = getArguments();
        sbsydw = bundle.getString("sbsydw");
        zcdm = bundle.getString("zcdm");
        quxian = bundle.getString("quxian");


        System.out.println("unitid:" + unitid);
        System.out.println("sbsydw:" + sbsydw);
        System.out.println("zcdm:" + zcdm);
        System.out.println("quxian:" + quxian);


        getDataThread thread = new getDataThread();
        thread.start();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Log.e(TAG, "onDestroyView");

        mList.clear();
        mListAll.clear();
    }


    class getDataThread extends Thread {

        @Override
        public void run() {
            super.run();

            WebServiceManager service = new WebServiceManager();
            String result = service.getWbdwEle(unitid,quxian,sbsydw,zcdm);

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

                try {
                    JSONObject jo = (JSONObject) msg.obj;
                    JSONArray ja = jo.getJSONArray("Rows");

                    Log.e("ja", ja+"");

                    for (int i = 0; i < ja.length(); i++) {

                        JSONObject jo1 = ja.getJSONObject(i);
//                Map<String,Object> tempMap = new LinkedHashMap<>();
                        Map<String, Object> tempMap = new HashMap<String, Object>();
                        tempMap.put("zcdm", jo1.getString("zcdm").equals("null")?"":jo1.getString("zcdm"));
                        tempMap.put("sbsydd", jo1.getString("sbsydd").equals("null")?"":jo1.getString("sbsydd"));
                        tempMap.put("Create_date", jo1.getString("Create_date").equals("null")?"":jo1.getString("Create_date"));
                        tempMap.put("id", jo1.getString("id").equals("null")?"":jo1.getString("id"));
                        mList.add(tempMap);
                    }
                    mListAll.clear();
                    mListAll.addAll(mList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                for (int j = 0; j < mListAll.size(); j++) {

                    Map map = (Map) mListAll.get(j);
                    mLinearLayout.addView(getTable(map,j), mLayoutParams);
                }



            }
        }
    };




    private void initView(View rootView) {
        //表格
        mLinearLayout = (LinearLayout)rootView.findViewById(R.id.wbgl_LinearLayout);
    }

    private void setTable() {
        String result =  "{\"Rows\":[{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "1" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "2" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "3" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "4" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "5" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "6" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "7" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "1" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "1" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "1" + "\",\"sbzl\":\"压力容器\"},{\"unitname\":\"" + "王灵生" + "\",\"id\":\"" + "1" + "\",\"sbzl\":\"压力容器\"}],\"Total\":\"1\"}";

        try {
            JSONObject jo = new JSONObject(result);
            JSONArray ja = jo.getJSONArray("Rows");
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo1 = ja.getJSONObject(i);
//                Map<String,Object> tempMap = new LinkedHashMap<>();
                Map<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("zcdm", jo1.getString("unitname").equals("null")?"":jo1.getString("unitname"));
                tempMap.put("sydw", jo1.getString("id").equals("null")?"":jo1.getString("id"));
                tempMap.put("sydd", jo1.getString("sbzl").equals("null")?"":jo1.getString("sbzl"));
                tempMap.put("wbrq", jo1.getString("sbzl").equals("null")?"":jo1.getString("sbzl"));
                tempMap.put("wb", jo1.getString("sbzl").equals("null")?"":jo1.getString("sbzl"));
                mList.add(tempMap);
            }
            mListAll.clear();
            mListAll.addAll(mList);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        mLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        mLayoutParams.bottomMargin = dip2px(30);    //单独表格item的位置 不是整个表格
//        mLayoutParams.topMargin = dip2px(10);
//        mLayoutParams.leftMargin = dip2px(10);
//        mLayoutParams.rightMargin = dip2px(10);

        for (int j = 0; j < mListAll.size(); j++) {

            Map map = (Map) mListAll.get(j);
            mLinearLayout.addView(getTable(map,j), mLayoutParams);
        }
    }


    private TableLayout getTable(final Map map,int j) {
        mTableLayout = new TableLayout(getActivity());
        mTableLayout.setLayoutParams(mLayoutParams);
        mTableLayout.setStretchAllColumns(true);

        String zcdm = (String) map.get("zcdm");
        String sbsydd = (String) map.get("sbsydd");
        String Create_date = (String) map.get("Create_date");
        String id = (String) map.get("id");

        TableRow tableRow = new TableRow(getActivity());
        View view = getView(zcdm, sbsydd, Create_date, id, j, mListAll.size());
        tableRow.addView(view);
        mTableLayout.addView(tableRow);

        return mTableLayout;
    }


    public View getView(final String msg,final String msg2, final String msg3,final String msg4, final int current_Id, int totle_Num) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.wbgl_table_item, null);

        view.setId(Integer.parseInt(msg4)); //id 要知道后面点的是哪一个

        TextView zcdm_tv = (TextView) view.findViewById(R.id.zcdm_tv);
        TextView sydd_tv = (TextView) view.findViewById(R.id.sydd_tv);
        TextView wbrq_tv = (TextView) view.findViewById(R.id.wbrq_tv);

        zcdm_tv.setText(msg);
        sydd_tv.setText(msg2);
        wbrq_tv.setText(msg3);


        view.setBackgroundDrawable(new BitmapDrawable());
        view.setFocusable(true);
        view.setClickable(true);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("v.getID():"+v.getId());
                Toast.makeText(getActivity(),"id:"+v.getId(),Toast.LENGTH_SHORT).show();



                DialogFragment_wbgl_wbzl mDialogFragment = new DialogFragment_wbgl_wbzl();
                removeSameFragment("DialogFragment_wbgl_wbzl");
                mDialogFragment.show(getFragmentManager(),"DialogFragment_wbgl_wbzl");


            }
        });



        // 只有一项
        if (totle_Num == 1) {
            view.setBackgroundResource(R.drawable.default_selector);
            return view;
        }
        // 第一项
        else if (current_Id == 0) {
            view.setBackgroundResource(R.drawable.default_selector);
        }
        // 最后一项
        else if (current_Id == totle_Num - 1) {
            view.setBackgroundResource(R.drawable.default_selector);
            setViewPadding(view);
        } else {
            view.setBackgroundResource(R.drawable.default_selector);
            setViewPadding(view);
        }

        linearLayout.addView(view);

        return linearLayout;
    }


    private void setViewPadding(View view) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = -3;     //单独表格的顶部横线的位置
        view.setLayoutParams(layoutParams);
    }


    /**
     * 转换dp
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }



    //移除相同 fragment
    private void removeSameFragment(String str){
        FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentByTag(str);
        //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
        if(fragment != null){
            mFragTransaction.remove(fragment);
        }
    }

}
