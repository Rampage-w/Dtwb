package com.sdhmw.dtwb.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sdhmw.dtwb.zxing.main.CaptureActivity;

import static com.sdhmw.dtwb.main.Fragment_wbgl_wb.REQUEST_CODE_ASK_CALL_PHONE;

/**
 * Created by wanglingsheng on 2017/10/27.
 * 维保关注-查询
 */

public class Fragment_wbgz_cx extends Fragment {


    private View rootView;
    private Button wbgz_cx_btn; //查询
    private Button wbgz_sys_btn;    //扫一扫

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbgz_cx, container, false);

        initView(rootView);
        initListener();

        return rootView;

    }

    private void initListener() {
        //查询
        wbgz_cx_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //切换fragment
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.wbgz_Fragment, new Fragment_wbgz_cx_list());
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        //扫一扫
        wbgz_sys_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //complie的  暂时不使用
//                IntentIntegrator integrator = new IntentIntegrator(getActivity());
//                integrator.initiateScan();



                //权限
                permission_CAMERA();


//                Intent intent = new Intent(getActivity(), CaptureActivity.class);
//                startActivity(intent);




            }
        });
    }


    final public static int REQUEST_CODE = 123;

    public void permission_CAMERA(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},REQUEST_CODE);
                return;
            }else{
                //方法
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
            }
        } else {
            //方法
            Intent intent = new Intent(getActivity(), CaptureActivity.class);
            startActivity(intent);
        }
    }


    private void initView(View rootView) {
        wbgz_cx_btn = (Button) rootView.findViewById(R.id.wbgz_cx_btn);
        wbgz_sys_btn = (Button) rootView.findViewById(R.id.wbgz_sys_btn);

    }


    //扫描二维码的 回调结果(complie的  但是找到的不能竖屏显示 采用的是导入源文件)
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (scanResult !=null){

            String result=scanResult.getContents();

            Toast.makeText(getActivity(),result,Toast.LENGTH_LONG).show();
        }
    }*/


}
