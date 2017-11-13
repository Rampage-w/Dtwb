package com.sdhmw.dtwb.main;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.sdhmw.dtwb.model.MyApp;
import com.sdhmw.dtwb.model.UserInfo;
import com.sdhmw.dtwb.net.WebServiceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by wanglingsheng on 2017/10/30.
 * 登录界面 2
 */

public class LoginActivity2 extends FragmentActivity {


    public static LoginActivity2 finish = null;

    private Button login_btn;   //登录
    private EditText username_et;   //用户名
    private EditText password_et;   //密码
    private LinearLayout login_ll;

    private String username;
    private String password;

    private SharedPreferences sp;   //小记录集

    private Drawable mDrawableRight;    //EditText右侧的图标

    //监听用户名和密码输入框 设置按钮是否可点击以及颜色变化
    private boolean username_flag;
    private boolean password_flag;


    private ProgressDialog progressDialog;

    private UserInfo userInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2);

        finish = this;


        initView();
        initListener();


    }


    /*
     * 提示加载
     */
    public void showProgressDialog(String title, String message) {
        if (progressDialog == null) {

            progressDialog = ProgressDialog.show(LoginActivity2.this, title,
                    message, true, false);

        } else if (progressDialog.isShowing()) {
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
        }
        progressDialog.show();
        progressDialog.setCancelable(true);
    }

    /*
     * 隐藏提示加载
     */
    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    //改变button的状态
    private void initBtn() {

        if (username_flag && password_flag) {

            //点击按钮 可以点击
            login_btn.setEnabled(true);
            login_btn.setBackgroundResource(R.drawable.button_blue);
        } else {

            //点击按钮 无法点击
            login_btn.setEnabled(false);
            login_btn.setBackgroundResource(R.drawable.button_blue_unclick);
        }


    }


    private void initListener() {

        //1.监听 用户名 EditText 是否点击的右侧叉号
        username_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = username_et.getCompoundDrawables()[2];

                //如果右边没有图片，不再处理
                if (drawable == null) {
                    return false;
                }

                //如果不是按下事件，不再处理
                if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }


                if (motionEvent.getX() > username_et.getWidth()
                        - username_et.getPaddingRight()
                        - drawable.getIntrinsicWidth()){
                    username_et.setText("");

                    //隐藏Drawables
                    username_et.setCompoundDrawables(null,null,null,null);

                }
                return false;
            }
        });


        //2.监听 用户名 EditText 内容变化
        username_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {

                    username_flag = false;

                    //点击按钮 无法点击
                    login_btn.setEnabled(false);
                    login_btn.setBackgroundResource(R.drawable.button_blue_unclick);


                    //hide
                    //隐藏Drawables
                    username_et.setCompoundDrawables(null, null, null, null);
                } else {

                    username_flag = true;


                    initBtn();

//                    //点击按钮 可以点击
//                    login_btn.setEnabled(true);
//                    login_btn.setBackgroundResource(R.drawable.button_blue);



                    // 这一步必须要做,否则不会显示.
                    mDrawableRight.setBounds(0, 0, mDrawableRight.getMinimumWidth(), mDrawableRight.getMinimumHeight());
                    username_et.setCompoundDrawables(null, null, mDrawableRight, null);

//                    username_et.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawableRight, null);

                }

            }
        });


        //3.监听 密码 EditText 是否点击的右侧叉号
        password_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {


                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = password_et.getCompoundDrawables()[2];

                //如果右边没有图片，不再处理
                if (drawable == null) {
                    return false;
                }

                //如果不是按下事件，不再处理
                if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    return false;
                }


                if (motionEvent.getX() > password_et.getWidth()
                        - password_et.getPaddingRight()
                        - drawable.getIntrinsicWidth()){
                    password_et.setText("");

                    //隐藏Drawables
                    password_et.setCompoundDrawables(null,null,null,null);

                }
                return false;
            }
        });


        //4.监听 密码 EditText 内容变化
        password_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() == 0) {

                    password_flag = false;

                    //点击按钮 无法点击
                    login_btn.setEnabled(false);
                    login_btn.setBackgroundResource(R.drawable.button_blue_unclick);


                    //hide
                    //隐藏Drawables
                    password_et.setCompoundDrawables(null, null, null, null);
                } else {


                    password_flag = true;

                    initBtn();


                    // 这一步必须要做,否则不会显示.
                    mDrawableRight.setBounds(0, 0, mDrawableRight.getMinimumWidth(), mDrawableRight.getMinimumHeight());
                    password_et.setCompoundDrawables(null, null, mDrawableRight, null);

//                    password_et.setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawableRight, null);

                }

            }
        });







        //登录
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = username_et.getText().toString().trim();
                password = password_et.getText().toString().trim();


                sp.edit().putString("save_username", username).commit();
                sp.edit().putString("save_password", password).commit();



                showProgressDialog("","正在加载...");


                getLoginThread gt = new getLoginThread();
                gt.start();

            }
        });



        //点击edittext外侧隐藏键盘
        login_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });


    }

    private void initView() {

        login_ll = (LinearLayout) findViewById(R.id.login_ll);

        login_btn = (Button) findViewById(R.id.login_btn);




        username_et = (EditText) findViewById(R.id.username_et);
        password_et = (EditText) findViewById(R.id.password_et);


        //小记录集
        sp = this.getSharedPreferences("info", MODE_PRIVATE);

        username = sp.getString("save_username", "");
        password = sp.getString("save_password", "");

        username_et.setText(username);
        password_et.setText(password);




        //控制用户名 右侧叉号的大小
        mDrawableRight = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_16, null);

        if (username.equals("") || username == null) {

            username_flag = false;

            //隐藏Drawables
            username_et.setCompoundDrawables(null,null,null,null);

        } else {

            username_flag = true;


//            // 这一步必须要做,否则不会显示.
            mDrawableRight.setBounds(0, 0, mDrawableRight.getMinimumWidth(), mDrawableRight.getMinimumHeight());
            username_et.setCompoundDrawables(null, null, mDrawableRight, null);

        }

        //密码
        if (password.equals("") || password == null) {


            password_flag = false;

            //隐藏Drawables
            password_et.setCompoundDrawables(null,null,null,null);

        } else {

            password_flag = true;


//            // 这一步必须要做,否则不会显示.
            mDrawableRight.setBounds(0, 0, mDrawableRight.getMinimumWidth(), mDrawableRight.getMinimumHeight());
            password_et.setCompoundDrawables(null, null, mDrawableRight, null);
        }





    }


    //弹出框
    public void mAlertDialog(String msg) {
        new AlertDialog.Builder(LoginActivity2.this)
                .setMessage(msg)
                .setPositiveButton("确定", null)
                .show();
    }


    class getLoginThread extends Thread {

        @Override
        public void run() {
            super.run();

            WebServiceManager service = new WebServiceManager();
            userInfo = service.getLogin(username, password);
            Message msg = new Message();


            Log.e("result   userInfo:", userInfo+"");


            if (userInfo == null) {
                msg.what = 0;
                handler.sendMessage(msg);


            } else if (userInfo.getUnittype().equals("null")) {

                msg.what = 1;
                handler.sendMessage(msg);


            } else {


                //MyApp 登录信息封装--------------
                MyApp myApp = (MyApp) getApplicationContext();


                String unitid = userInfo.getUnitid();
                String unitname = userInfo.getUnitname();
                String unitType = userInfo.getUnittype();

                myApp.setUnitid(unitid);
                myApp.setUnitname(unitname);
                myApp.setUnittype(unitType);

                System.out.println("unitid:" + unitid);
                System.out.println("unitname:" + unitname);
                System.out.println("unitType:" + unitType);


                msg.what = 2;
                msg.obj = unitType; //角色
                handler.sendMessage(msg);

            }

        }
    }


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:

                    hideProgressDialog();
                    mAlertDialog("网络连接错误，请检查网络连接");

                    break;

                case 1:

                    hideProgressDialog();
                    mAlertDialog("用户名或密码错误");

                    break;

                case 2:

                    hideProgressDialog();

                    String mUnitType = (String) msg.obj;

                    System.out.println("mUnitType:" + mUnitType);

                    if (mUnitType == null || (!mUnitType.equals("1") && !mUnitType.equals("2") &&
                            !mUnitType.equals("3") && !mUnitType.equals("4"))) {

                        mAlertDialog("mUnitType："+mUnitType+"   单位类别不满足");
                    } else {

                        Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }



                    //修改
                    //1:检验单位2:维保单位3:监察单位4:个人


                    //以前自己设定的 1：维保 2：监察 3：检验 4：业主
//                    Intent intent = new Intent(LoginActivity2.this, MainActivity.class);
//                    intent.putExtra("role", "1");
//                    startActivity(intent);





                    break;
            }
        }
    };




}

