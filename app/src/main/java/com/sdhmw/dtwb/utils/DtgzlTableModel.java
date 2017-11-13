package com.sdhmw.dtwb.utils;

/**
 * Created by wanglingsheng on 2017/9/20.
 *
 * 电梯故障率 model
 */

public class DtgzlTableModel {

    private String dtlb;    //电梯类别
    private int cs;     //故障次数

    private int Num;    //第几列

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public DtgzlTableModel(int Num, String dtlb, int cs) {
        super();
        this.Num = Num;
        this.dtlb = dtlb;
        this.cs = cs;
    }


    public String getDtlb() {
        return dtlb;
    }

    public void setDtlb(String dtlb) {
        this.dtlb = dtlb;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

}
