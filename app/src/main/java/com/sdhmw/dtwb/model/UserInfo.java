package com.sdhmw.dtwb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglingsheng on 2017/11/2.
 */

public class UserInfo implements Serializable {

    /**
     * id	int
     uid		登录用户名
     pwd		密码
     name		姓名
     tel		电话
     unitid		单位id
     unitname		单位名称
     remarks		备注
     canlogin		能否登录系统 0：能 1：不能
     regi_flag		0:注册用户1:后期分配用户
     canlogin_app		能否登录app  0：能 1：不能
     Create_id
     Create_date
     Update_id
     Update_date
     unittype		1:检验单位2:维保单位3:监察单位4:用户
     isDelete		逻辑删除标志 0:正常 1:删除
     delete_time		逻辑删除时间
     shr_flag		审核人标志位0:不是1:是

     */


    private String uid;
    private String pwd;
    private String name;
    private String tel;
    private String unitid;
    private String unitname;

    private String unittype;

    public static List<UserInfo> infos = new ArrayList<UserInfo>();

    static {
    }

    public UserInfo() {
    }






    public static List<UserInfo> getInfos() {
        return infos;
    }

    public static void setInfos(List<UserInfo> infos) {
        UserInfo.infos = infos;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getUnittype() {
        return unittype;
    }

    public void setUnittype(String unittype) {
        this.unittype = unittype;
    }
}
