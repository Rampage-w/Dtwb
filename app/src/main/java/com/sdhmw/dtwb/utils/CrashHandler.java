package com.sdhmw.dtwb.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * android中如果出现未捕获异常就会让应用程序crash掉，给用户的提示仅仅是该应用已崩溃，
 * 很不友好，自己定义一个UncaughtExceptionHanlder来捕获UI线程上的Exception，
 * 使用AlertDialog的方式展示出来，可以方便测试，也可以提升用户体验
 *
 * Created by wanglingsheng on 2017/11/9.
 */

public class CrashHandler implements UncaughtExceptionHandler{

    public static List<Activity> list = new ArrayList<Activity>();

    public static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private static CrashHandler INSTANCE = new CrashHandler();

    private Context mContext;

    private Map<String ,String> infos = new HashMap<String,String>();

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CrashHandler(){}
    public static CrashHandler getInstance(){
        return INSTANCE;
    }

    public static void addActivity(Activity activity){
        if(!list.contains(activity)){
            list.add(activity);
        }
    }

    public void init(Context context){
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mDefaultHandler != null){
            mDefaultHandler.uncaughtException(thread, ex);
        }else{
//			try {
//				Thread.sleep(3000);
//			} catch (Exception e) {
//				Log.e(TAG, "error", e);
//			}
            System.out.println("tuichu -----------------");
            exit();
//			android.os.Process.killProcess(android.os.Process.myPid());
//			System.exit(0);
        }

    }

    public static void exit(){
        try {
            for(Activity activity:list){
                if(activity != null){
                    activity.finish();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "an error occured close activity");
        }finally{
            System.exit(0);
        }
    }

    private boolean handleException(Throwable ex){
        if(ex == null){
            return false;
        }
        new Thread(){
            @Override
            public void run(){
                Looper.prepare();
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();
        //collectDeviceInfo(mContext);
        //saveCrasgInfo2File(ex);
        return true;
    }
    public void collectDeviceInfo(Context ctx){
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if(pi != null){
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (Exception e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for(Field field:fields){
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + ":" + field.getByte(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }
    public String saveCrasgInfo2File(Throwable ex){
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String, String> entry : infos.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while(cause != null){
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-"+time+"-"+timestamp+".log";
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                String path="sdcard/crash/";
                File dir = new File(path);
                if(dir.exists()){
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(path+fileName);
                fos.write(sb.toString().getBytes());
                fos.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file ...", e);
        }
        return null;
    }
}

