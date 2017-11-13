package com.sdhmw.dtwb.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdhmw.dtwb.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/9/20.
 *
 * 电梯故障率 tableView
 */

public class DtgzlTableView extends LinearLayout{


    //表格
    private LinearLayout mHorizontalWeekLayout; //第一行标题
    private LinearLayout mVerticalWeekLaout;   //表格

    private String[] titleName = {"机房", "次数"};
    private String[] line_1 = new String[2];
    private String[] line_2 = new String[2];
    private String[] line_3 = new String[2];
    private String[] line_4 = new String[2];

    private final static int TimeTableNumWidth = 20;    //宽度
    private final static int TimeTableWeekNameHeight = 30;
    public final static int MAXNUM = 4; //列数

    //显示几列
    public final static int column = 2;
    //单个View高度
    private final static int TimeTableHeight = 50;
    //线的高度
    private final static int TimeTableLineHeight = 2;




    //数据源
    private List<DtgzlTableModel> mListTimeTable = new ArrayList<DtgzlTableModel>();

    public DtgzlTableView(Context context) {
        super(context);
    }

    public DtgzlTableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 横的分界线
     *
     * @return
     */
    private View getWeekTransverseLine() {
        TextView mWeekline = new TextView(getContext());
        mWeekline.setBackgroundColor(getResources().getColor(R.color.view_line));
        mWeekline.setHeight(TimeTableLineHeight);
        mWeekline.setWidth(LayoutParams.MATCH_PARENT);
        return mWeekline;
    }

    /**
     * 竖向分界线
     *
     * @return
     */
    private View getWeekVerticalLine() {
        TextView mWeekline = new TextView(getContext());
        mWeekline.setBackgroundColor(getResources().getColor(R.color.view_line));
        mWeekline.setHeight(dip2px(TimeTableWeekNameHeight));
        mWeekline.setWidth((TimeTableLineHeight));
        return mWeekline;
    }


    private void initView() {

        mHorizontalWeekLayout = new LinearLayout(getContext());
        mHorizontalWeekLayout.setOrientation(LinearLayout.HORIZONTAL);

        mVerticalWeekLaout = new LinearLayout(getContext());
        mVerticalWeekLaout.setOrientation(LinearLayout.VERTICAL);

        //表格 line column
        for (int i = 1;i <= column; i++) {

            //设置显示的标题
            LinearLayout mHoriView = new LinearLayout(getContext());
            mHoriView.setOrientation(VERTICAL);
            TextView mTitleName = new TextView(getContext());
            mTitleName.setTextColor(getResources().getColor(R.color.text_color));
            mTitleName.setWidth((getViewWidth()) / column);
            mTitleName.setHeight(dip2px(TimeTableWeekNameHeight));
            mTitleName.setGravity(Gravity.CENTER);
            mTitleName.setTextSize(16);
            mTitleName.setText(titleName[i - 1]);
            mHoriView.addView(mTitleName);
            mHorizontalWeekLayout.addView(mHoriView);


            for (int k = 0; k < mListTimeTable.size(); k++) {

                String[] line = new String[2];
                line[0] = mListTimeTable.get(k).getDtlb();
                line[1] = String.valueOf(mListTimeTable.get(k).getCs());

                LinearLayout mLayout = new LinearLayout(getContext());
                mLayout.setOrientation(HORIZONTAL);
                TextView mContentName = new TextView(getContext());
                mContentName.setTextColor(getResources().getColor(R.color.text_color));
                mContentName.setWidth((getViewWidth()) / column);
                mContentName.setHeight(dip2px(TimeTableWeekNameHeight));
                mContentName.setGravity(Gravity.CENTER);
                mContentName.setTextSize(16);
                mContentName.setText(line[i - 1]);
                mLayout.addView(mContentName);
                mVerticalWeekLaout.addView(mLayout);
                mVerticalWeekLaout.addView(getWeekTransverseLine());
            }
            mHorizontalWeekLayout.addView(getWeekVerticalLine());
        }
        addView(mHorizontalWeekLayout);
        addView(getWeekTransverseLine());
        addView(mVerticalWeekLaout);
        addView(getWeekTransverseLine());


    }




    private int getViewWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }


    /**
     * 转换dp
     *
     * @param dpValue
     * @return
     */
    public int dip2px(float dpValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void setTimeTable(List<DtgzlTableModel> mlist) {
        this.mListTimeTable = mlist;

        System.out.println("mListTimeTable:"+mListTimeTable);
        System.out.println("mListTimeTable1:"+mListTimeTable.get(0).getDtlb());

        for (DtgzlTableModel TableModel : mlist) {
//            addTimeName(TableModel.getDtlb());
        }

        initView();
        invalidate();
    }

}
