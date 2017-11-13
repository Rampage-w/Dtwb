package com.sdhmw.dtwb.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Created by wanglingsheng on 2017/10/19.
 * 登录界面
 *
 */

public class LoginActivity extends FragmentActivity {


    public static LoginActivity finish = null;

    private MaterialEditText login_edit_username;
    private MaterialEditText login_edit_pwd;
    private Button login_btn_login;

    private String username;
    private String pwd;
    private RelativeLayout login_view;

    private SharedPreferences sp;   //小记录集

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        finish = this;



        initView();
        initListener();

    }

    private void initListener() {

        login_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username = login_edit_username.getText().toString().trim();
                pwd = login_edit_pwd.getText().toString().trim();

                System.out.println("username:" + username);

                sp.edit().putString("save_username", username).commit();



                //维保用户
                if (username.equals("1")) {

                    myAlertDialog("维保","1");

//                    new AlertDialog.Builder(LoginActivity.this)
//                            .setMessage("将进入维保单位的界面")
//                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    //1：维保 2：监察 3：检验 4：业主
//                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                    intent.putExtra("role", "1");
//                                    startActivity(intent);
//                                    finish();
//
//                                }
//                            })
//                            .setNegativeButton("取消", null)
//                            .show();

                } else if(username.equals("2")){
                    myAlertDialog("监察","2");
                }else if(username.equals("3")){
                    myAlertDialog("检验","3");

                }else if(username.equals("4")){
                    myAlertDialog("业主","4");

                }
            }
        });



        //点击edittext外侧隐藏键盘
        login_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("login_view    onclick");




                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });


    }

    private void initView() {

        login_view = (RelativeLayout) findViewById(R.id.login_view);
        login_btn_login = (Button) findViewById(R.id.login_btn_login);
        login_edit_username = (MaterialEditText) findViewById(R.id.login_edit_username);
        login_edit_pwd = (MaterialEditText) findViewById(R.id.login_edit_pwd);


        //小记录集
        sp = this.getSharedPreferences("info", MODE_PRIVATE);
        username = sp.getString("save_username", "");
        login_edit_username.setText(username);


    }


    //弹出框
    public void myAlertDialog(String msg, final String role) {

        new AlertDialog.Builder(LoginActivity.this)
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //1：维保 2：监察 3：检验 4：业主
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("role", role);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }
}
