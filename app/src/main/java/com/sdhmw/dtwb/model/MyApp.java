package com.sdhmw.dtwb.model;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import java.util.Stack;

/**
 * Created by wanglingsheng on 2017/11/3.
 */

public class MyApp extends Application {


    //给全部Activity添加集合管理

    //activity管理堆栈，用于返回首页。当返回首页时除栈低的元素外所有元素都finish。
    public static Stack<Activity> ActivityStack = new Stack<Activity>();



    private String unitid;
    private String unitname;
    private String unittype;
    private String URL_String;


    public static Stack<Activity> getActivityStack() {
        return ActivityStack;
    }

    public static void setActivityStack(Stack<Activity> activityStack) {
        ActivityStack = activityStack;
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

    public String getURL_String() {
        return URL_String;
    }

    public void setURL_String(String URL_String) {
        this.URL_String = URL_String;
    }


    @Override
    public void onCreate() {
        super.onCreate();

//        URL_String = "http://192.168.10.3";//测试(于)
        URL_String = "http://192.168.10.10";//测试(公司)
//        URL_String = "http://114.55.147.47";//阿里云

        initImageLoader(getApplicationContext());


    }



    /**
     *
     * imageload 通用框架
     *
     * ***/
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

}
