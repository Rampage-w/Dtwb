package com.sdhmw.dtwb.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sdhmw.dtwb.main.R;

/**
 * Created by wanglingsheng on 2017/9/18.
 */

public class SegmentView extends LinearLayout{

//    private TextView leftTextView;
//    private TextView rightTextView;

    private TextView OneTextView;
    private TextView TwoTextView;
    private TextView ThreeTextView;
    private TextView FourTextView;


    private onSegmentViewClickListener segmentListener;

    // 这是代码加载ui必须重写的方法
    public SegmentView(Context context) {
        super(context);
        initView();
    }

    // 这是在xml布局使用必须重写的方法
    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
//        leftTextView = new TextView(getContext());
//        rightTextView = new TextView(getContext());

        OneTextView = new TextView(getContext());
        TwoTextView = new TextView(getContext());
        ThreeTextView = new TextView(getContext());
        FourTextView = new TextView(getContext());






        // 设置textview的布局宽高并设置为weight属性都为1
//        leftTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
//        rightTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));

        OneTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        TwoTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        ThreeTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        FourTextView.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));





//        leftTextView.setLayoutParams(new LayoutParams(300, 100, 1));
//        rightTextView.setLayoutParams(new LayoutParams(300, 100, 1));

        // 初始化的默认文字
//        leftTextView.setText("维护保养");
//        rightTextView.setText("维修改造");

        OneTextView.setText("半月");
        TwoTextView.setText("季度");
        ThreeTextView.setText("半年");
        FourTextView.setText("年度");




        // 实现不同的按钮状态，不同的颜色
        ColorStateList csl = getResources().getColorStateList(R.color.segment_text_color_selector);
//        leftTextView.setTextColor(csl);
//        rightTextView.setTextColor(csl);

        OneTextView.setTextColor(csl);
        TwoTextView.setTextColor(csl);
        ThreeTextView.setTextColor(csl);
        FourTextView.setTextColor(csl);





        // 设置textview的内容位置居中
//        leftTextView.setGravity(Gravity.CENTER);
//        rightTextView.setGravity(Gravity.CENTER);


        OneTextView.setGravity(Gravity.CENTER);
        TwoTextView.setGravity(Gravity.CENTER);
        ThreeTextView.setGravity(Gravity.CENTER);
        FourTextView.setGravity(Gravity.CENTER);






        // 设置textview的内边距
//        leftTextView.setPadding(29, 10, 29, 10);
//        rightTextView.setPadding(29, 10, 29, 10);

        OneTextView.setPadding(29, 10, 29, 10);
        TwoTextView.setPadding(29, 10, 29, 10);
        ThreeTextView.setPadding(29, 10, 29, 10);
        FourTextView.setPadding(29, 10, 29, 10);



        // 设置文字大小
        setSegmentTextSize(16);

        // 设置背景资源
//        leftTextView.setBackgroundResource(R.drawable.segment_left_background);
//        rightTextView.setBackgroundResource(R.drawable.segment_right_background);

        OneTextView.setBackgroundResource(R.drawable.segment_left_background);
        TwoTextView.setBackgroundResource(R.drawable.segment_middle_1_background);
        ThreeTextView.setBackgroundResource(R.drawable.segment_middle_2_background);
        FourTextView.setBackgroundResource(R.drawable.segment_right_background);







        // 默认左侧textview为选中状态
//        leftTextView.setSelected(true);


        OneTextView.setSelected(true);






        // 加入textview
        this.removeAllViews();
//        this.addView(leftTextView);
//        this.addView(rightTextView);

        this.addView(OneTextView);
        this.addView(TwoTextView);
        this.addView(ThreeTextView);
        this.addView(FourTextView);





        this.invalidate();//重新draw()

//        leftTextView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (leftTextView.isSelected()) {
//                    return;
//                }
//                leftTextView.setSelected(true);
//                rightTextView.setSelected(false);
//                if (segmentListener != null) {
//                    segmentListener.onSegmentViewClick(leftTextView, 0);
//                }
//            }
//        });
//
//        rightTextView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (rightTextView.isSelected()) {
//                    return;
//                }
//                rightTextView.setSelected(true);
//                leftTextView.setSelected(false);
//                if (segmentListener != null) {
//                    segmentListener.onSegmentViewClick(rightTextView, 1);
//                }
//            }
//        });




        OneTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (OneTextView.isSelected()) {
                    return;
                }
                OneTextView.setSelected(true);
                TwoTextView.setSelected(false);
                ThreeTextView.setSelected(false);
                FourTextView.setSelected(false);


                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(OneTextView, 0);
                }
            }
        });

        TwoTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TwoTextView.isSelected()) {
                    return;
                }
                OneTextView.setSelected(false);
                TwoTextView.setSelected(true);
                ThreeTextView.setSelected(false);
                FourTextView.setSelected(false);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(TwoTextView, 1);
                }
            }
        });

        ThreeTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ThreeTextView.isSelected()) {
                    return;
                }
                OneTextView.setSelected(false);
                TwoTextView.setSelected(false);
                ThreeTextView.setSelected(true);
                FourTextView.setSelected(false);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(ThreeTextView, 2);
                }
            }
        });

        FourTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FourTextView.isSelected()) {
                    return;
                }
                OneTextView.setSelected(false);
                TwoTextView.setSelected(false);
                ThreeTextView.setSelected(false);
                FourTextView.setSelected(true);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(FourTextView, 3);
                }
            }
        });

    }

    /**
     * 设置字体大小
     *
     * @param dp
     */
    private void setSegmentTextSize(int dp) {
//        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
//        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);

        OneTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        TwoTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        ThreeTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        FourTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);



    }

    /**
     * 手动设置选中的状态
     *
     * @param i
     */
    public void setSelect(int i) {
//        if (i == 0) {
//            leftTextView.setSelected(true);
//            rightTextView.setSelected(false);
//        } else {
//            leftTextView.setSelected(false);
//            rightTextView.setSelected(true);
//        }


        if (i == 0) {
            OneTextView.setSelected(true);
            TwoTextView.setSelected(false);
            ThreeTextView.setSelected(false);
            FourTextView.setSelected(false);
        } else if (i == 1) {
            OneTextView.setSelected(false);
            TwoTextView.setSelected(true);
            ThreeTextView.setSelected(false);
            FourTextView.setSelected(false);
        } else if (i == 2) {
            OneTextView.setSelected(false);
            TwoTextView.setSelected(false);
            ThreeTextView.setSelected(true);
            FourTextView.setSelected(false);
        } else if (i == 3) {
            OneTextView.setSelected(false);
            TwoTextView.setSelected(false);
            ThreeTextView.setSelected(false);
            FourTextView.setSelected(true);
        }


    }

//    /**
//     * 设置控件显示的文字
//     *
//     * @param text
//     * @param position
//     */
//    public void setSegmentText(CharSequence text, int position) {
//        if (position == 0) {
//            leftTextView.setText(text);
//        }
//        if (position == 1) {
//            rightTextView.setText(text);
//        }
//    }

    // 定义一个接口接收点击事件
    public interface onSegmentViewClickListener {
        public void onSegmentViewClick(View view, int postion);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener segmentListener) {
        this.segmentListener = segmentListener;
    }
}
