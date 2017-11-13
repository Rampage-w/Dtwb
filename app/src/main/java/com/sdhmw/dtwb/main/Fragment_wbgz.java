package com.sdhmw.dtwb.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.sdhmw.dtwb.utils.PopupMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by wanglingsheng on 2017/10/26.
 * 维保关注(我关注的list列表)
 */
@SuppressWarnings("ResourceType")
public class Fragment_wbgz extends Fragment {


    private View rootView;

    private static final Map<String, String> mMap = new LinkedHashMap<>();  //保存插入的顺序

    private LinearLayout.LayoutParams mLayoutParams = null;
    private LinearLayout mLinearLayout;
    private TableLayout mTableLayout;

    private ArrayList<Object> mList = new ArrayList<Object>();
    private ArrayList<Object> mListAll = new ArrayList<Object>();   //总得list

    private ImageView menu_iv; //右上角菜单
    private PopupMenu popupMenu;    //右上角菜单


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbgz, container, false);

        mList.clear();


        initView(rootView);
        initListener();
        setTable();

        return rootView;

    }

    private void initListener() {

        menu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu.showLocation(R.id.menu_iv);
                popupMenu.setOnItemClickListener(new PopupMenu.OnItemClickListener() {
                    @Override
                    public void onClick(PopupMenu.MENUITEM item, String str) {
                        Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();

                        //维保关注Activity
                        Intent intent = new Intent(getActivity(), WbgzActivity.class);
                        startActivity(intent);


                    }
                });
            }
        });
    }

    private void initView(View rootView) {
        //表格
        mLinearLayout = (LinearLayout)rootView.findViewById(R.id.wbgz_LinearLayout);
        popupMenu = new PopupMenu(getActivity());

        menu_iv = (ImageView) getActivity().findViewById(R.id.menu_iv);
        menu_iv.setVisibility(View.VISIBLE);
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
        String sydw = (String) map.get("sydw");
        String sydd = (String) map.get("sydd");
        String wbrq = (String) map.get("wbrq");
        String wb = (String) map.get("wb");

        TableRow tableRow = new TableRow(getActivity());
        View view = getView(zcdm,sydw,sydd,wbrq,wb,j, mListAll.size());
        tableRow.addView(view);
        mTableLayout.addView(tableRow);

        return mTableLayout;
    }


    public View getView(final String msg,final String msg2, final String msg3,
                        final String msg4,final String msg5,final int current_Id, int totle_Num) {
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.wbgl_table_item, null);


        view.setId(Integer.parseInt(msg2));

        TextView zcdm_tv = (TextView) view.findViewById(R.id.zcdm_tv);
//        TextView sydw_tv = (TextView) view.findViewById(R.id.sydw_tv);
        TextView sydd_tv = (TextView) view.findViewById(R.id.sydd_tv);
        TextView wbrq_tv = (TextView) view.findViewById(R.id.wbrq_tv);
//        TextView wb_tv = (TextView) view.findViewById(R.id.wb_tv);


        zcdm_tv.setText(msg);
//        sydw_tv.setText(msg2);
        sydd_tv.setText(msg3);
        wbrq_tv.setText(msg4);
//        wb_tv.setText(msg5);


        view.setBackgroundDrawable(new BitmapDrawable());
        view.setFocusable(true);
        view.setClickable(true);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("v.getID():"+v.getId());
                Toast.makeText(getActivity(),"id:"+v.getId(),Toast.LENGTH_SHORT).show();



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

