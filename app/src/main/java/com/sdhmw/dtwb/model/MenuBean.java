package com.sdhmw.dtwb.model;

/**
 * Created by wanglingsheng on 2017/10/16.
 * 我的菜单的 model类
 */

public class MenuBean {
    private String tag; //所属的分类
    private String menu;
    private Integer img;


    public MenuBean(String tag, String menu, Integer img) {
        this.tag = tag;
        this.menu = menu;
        this.img = img;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
