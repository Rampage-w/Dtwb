package com.sdhmw.dtwb.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;

import org.feezu.liuli.timeselector.TimeSelector;

/**
 * Created by wanglingsheng on 2017/10/16.
 * 查询报告
 */

public class Fragment_bggl_cxbg extends Fragment {

    private View rootView;
    private BootstrapEditText wbrq_start_txt;   //维保日期开始
    private BootstrapEditText wbrq_end_txt;     //维保日期结束

    private Button bggl_cxbg_cx_btn;    //查询

    private BootstrapEditText sydw_bet; //使用单位
    private Drawable mDrawableRight;    //EditText右侧的图标

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_bggl_cxbg, container, false);

        initView(rootView);
        initListener();



        return rootView;

    }

    private void initListener() {

        sydw_bet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                System.out.println("EditText点击 判断是否是右侧的叉号");

                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = sydw_bet.getCompoundDrawables()[2];

                //如果右边没有图片，不再处理
                if (drawable == null) {
                    return false;
                }

                //如果不是按下事件，不再处理
                if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }


                if (motionEvent.getX() > sydw_bet.getWidth()
                        - sydw_bet.getPaddingRight()
                        - drawable.getIntrinsicWidth()){
                    sydw_bet.setText("");

                    //隐藏Drawables
                    sydw_bet.setCompoundDrawables(null,null,null,null);

                }
                return false;
            }
        });

        sydw_bet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 0) {
                    //hide
                    //隐藏Drawables
                    sydw_bet.setCompoundDrawables(null, null, null, null);
                } else {
                    // 这一步必须要做,否则不会显示.
                    mDrawableRight.setBounds(0, 0, mDrawableRight.getMinimumWidth(), mDrawableRight.getMinimumHeight());
                    sydw_bet.setCompoundDrawables(null, null, mDrawableRight, null);

                }
            }
        });


        bggl_cxbg_cx_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //跳转到 报告管理Activity
                Intent intent = new Intent(getActivity(), BgglActivity.class);
                intent.putExtra("item","1");    //查询报告 跳转
                startActivity(intent);


                //动画(老款)
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.animo_no);

            }
        });




    }



    private void initView(View rootView) {

        mDrawableRight = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_16, null);
        sydw_bet = (BootstrapEditText) rootView.findViewById(R.id.sydw_bet);

        //隐藏Drawables
        sydw_bet.setCompoundDrawables(null, null, null, null);

        bggl_cxbg_cx_btn = (Button) rootView.findViewById(R.id.bggl_cxbg_cx_btn);

        //日期
        wbrq_start_txt = (BootstrapEditText) rootView.findViewById(R.id.wbrq_start_txt);
        wbrq_end_txt = (BootstrapEditText) rootView.findViewById(R.id.wbrq_end_txt);


        wbrq_start_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //时间选择控件
                TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                    @Override
                    public void handle(String time) {
                        Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();


                        String endTime = time.substring(0, 11);

                        wbrq_start_txt.setText(endTime);

                    }
                }, "2015-01-01 00:00", "2018-12-31 23:59:59");

                timeSelector.setIsLoop(false);  //设置不循环

//                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
                timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
                timeSelector.show();
            }
        });



        wbrq_start_txt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {

                if (hasFocus) {

                    //时间选择控件
                    TimeSelector timeSelector = new TimeSelector(getActivity(), new TimeSelector.ResultHandler() {
                        @Override
                        public void handle(String time) {
                            Toast.makeText(getActivity(), time, Toast.LENGTH_SHORT).show();


                            String endTime = time.substring(0, 11);

                            wbrq_start_txt.setText(endTime);

                        }
                    }, "2015-01-01 00:00", "2018-12-31 23:59:59");

                    timeSelector.setIsLoop(false);  //设置不循环

//                timeSelector.setMode(TimeSelector.MODE.YMDHM);  //显示 年月日时分（默认）
                    timeSelector.setMode(TimeSelector.MODE.YMD);  //显示 年月日时分（默认）
                    timeSelector.show();


                } else {

                }

            }
        });


    }
}
