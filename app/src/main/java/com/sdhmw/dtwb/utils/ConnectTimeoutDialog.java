package com.sdhmw.dtwb.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;

/**
 * 连接超时的提示信息，直接退出退出，重新登录
 * Created by wanglingsheng on 2017/11/9.
 */

public class ConnectTimeoutDialog{

    public static void TimeoutDialog(Activity activity){
        AlertDialog.Builder builder = new Builder(activity);
        builder.setMessage("网络连接超时或异常，请重新登录！");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialoginterface, int i) {
                CrashHandler.exit();
            }
        });
        builder.create();
        builder.show();
    }
}
