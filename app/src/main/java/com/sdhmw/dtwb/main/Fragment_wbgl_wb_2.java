package com.sdhmw.dtwb.main;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglingsheng on 2017/9/26.
 * 维保管理 机房
 */

@SuppressWarnings({"WrongConstant", "ResourceType"})
public class Fragment_wbgl_wb_2 extends Fragment {


    private View rootView;

    private View view;
    private String str = "检查(J)";
    private String MyStrValue;


    private static final  String MSG_1[]={"1.机房滑轮间环境\n清洁，门窗完好、照明正常","小黑","小黄","小白","小黑","小黄","小白","小黑","小黄","小白","小黑","小黄","小白","小黑","小黄"};
    private LayoutParams layoutParams = null;
    private LinearLayout Layout_ll;
    private TableLayout tableLayout;

    private Map<Integer, String> map = new HashMap<Integer, String>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbgl_wb_2, container, false);

        initView(rootView);


        setTable();

        return rootView;

    }

    private void initView(View rootView) {
        Layout_ll = (LinearLayout) rootView.findViewById(R.id.Layout_ll);
    }

    private void setTable() {
        layoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.bottomMargin = 30;
        layoutParams.topMargin = 40;
        layoutParams.leftMargin=10;
        layoutParams.rightMargin=10;

        Layout_ll.addView(getTable(MSG_1), layoutParams);
    }

    //获取table
    public TableLayout getTable(final String[] array){
        tableLayout = new TableLayout(getActivity());
        tableLayout.setLayoutParams(layoutParams);
        tableLayout.setStretchAllColumns(true);

        for (int i = 0; i < array.length; i++) {
            TableRow tableRow = new TableRow(getActivity());
            View view = getView(array[i],i,array.length);
            tableRow.addView(view);
            tableLayout.addView(tableRow);
        }
        return tableLayout;
    }


    public View getView(final String msg,final int current_Id,int totle_Num){
        LinearLayout linearLayout=new LinearLayout(getActivity());
        linearLayout.setOrientation(1);  		//HORIZONTAL = 0		VERTICAL = 1

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.set_content_1, null);

        view.setBackgroundDrawable(new BitmapDrawable());
        view.setFocusable(true);
        view.setClickable(true);

        TextView tv_name=(TextView)view.findViewById(R.id.tv_list_item);
        tv_name.setText(msg);
//        tv_name.setTextSize(20);

        final TextView tv_list_result = (TextView) view.findViewById(R.id.tv_list_result);




        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.e("v.getId", v.getId()+"");

                //dialog 选择
                initDialog(current_Id,tv_list_result);


                if(msg.equals(MSG_1[0])){
                    Toast.makeText(getActivity(), "小白", Toast.LENGTH_SHORT).show();
                }else if(msg.equals(MSG_1[1])){
                    Toast.makeText(getActivity(), "小黑", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "小黄", Toast.LENGTH_SHORT).show();
                }

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
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = -2;
        view.setLayoutParams(layoutParams);
    }



    private void initDialog(final int current_Id, final TextView tv) {



        str = "检查(J)";	//重新归位


        final String[] items = {"检查(J)","修理(X)","润滑(R)","更换(G)","清扫(Q)","调校(T)","无此项(/)",};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int index) {
                        Toast.makeText(getActivity(), items[index], Toast.LENGTH_SHORT).show();

                        str = items[index];

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {




                        if(str.equals("检查(J)")){
                            MyStrValue = "J";
                        }else if (str.equals("修理(X)")) {
                            MyStrValue = "X";
                        }else if (str.equals("润滑(R)")) {
                            MyStrValue = "R";
                        }else if (str.equals("更换(G)")) {
                            MyStrValue = "G";
                        }else if (str.equals("清扫(Q)")) {
                            MyStrValue = "Q";
                        }else if (str.equals("调校(T)")) {
                            MyStrValue = "T";
                        }else if (str.equals("无此项(/)")) {
                            MyStrValue = "/";
                        }

                        tv.setText(MyStrValue);


                        //写入map集合
                        map.put(current_Id, MyStrValue);


                        Log.e("map", map+"");


                    }
                }).show();

    }



}

