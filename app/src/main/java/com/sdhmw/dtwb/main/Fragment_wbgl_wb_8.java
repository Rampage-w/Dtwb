package com.sdhmw.dtwb.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdhmw.dtwb.utils.MyData;
import com.sdhmw.dtwb.utils.signFragment;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglingsheng on 2017/10/9.
 *
 * 用户签名
 */

public class Fragment_wbgl_wb_8 extends Fragment{



    private View view;

    private Bitmap bitmap;

    private String urlString = "http://192.168.10.5";
    public String filePath;  //发送的文件路径
    public String fileName;  //发送的文件名称


    //签字
    private ImageView ivSign;
    private TextView tvSign;
    private ImageView qianzidel;//签字删除按钮
    private ImageView qianziinfo_iv;//签字上传图片
    private TextView  qianziinfo_tv;//签字上传信息

    private Animation operationAnim;

    //小程序集
    private SharedPreferences sp;

    public Fragment_wbgl_wb_8() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_wbgl_wb_8, container, false);

        initView();

        return view;
    }






    private void initView() {


        //签字
        ivSign = (ImageView) view.findViewById(R.id.aqjc_add_signImg);		//
        tvSign = (TextView) view.findViewById(R.id.aqjc_add_text_sign);		//签字区域
        qianzidel = (ImageView) view.findViewById(R.id.aqjc_qianzi_del);	//签名的删除按钮
        qianziinfo_iv = (ImageView) view.findViewById(R.id.aqjc_add_qianziphotoInfoImg);	//长传照片动图
        qianziinfo_tv = (TextView) view.findViewById(R.id.aqjc_add_qianziphotoInfo);		//正在上传照片 文字提醒
        qianziinfo_iv.setVisibility(View.GONE);
        qianziinfo_tv.setVisibility(View.GONE);

        // 正在上传的动画效果
        operationAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.imagerotate);
        LinearInterpolator lin = new LinearInterpolator();
        operationAnim.setInterpolator(lin);


        //弹出签字界面
        ivSign.setOnClickListener(signListener);
        tvSign.setOnClickListener(signListener);

    }


    //弹出签字界面
    private View.OnClickListener signListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {


            System.out.println("准备 弹出签字界面");



            //v4包的 需要向下兼容
            FragmentTransaction mFragTransaction = getFragmentManager().beginTransaction();
            Fragment fragment = getFragmentManager().findFragmentByTag("signFragment");

            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            if(fragment != null){
                mFragTransaction.remove(fragment);
            }

            signFragment sf = new signFragment();
            sf.setTargetFragment(Fragment_wbgl_wb_8.this, signFragment.requestCode);
            sf.show(getFragmentManager(), "signFragment");

        }
    };



    //该方法用来接收 signFragment发送过来的消息
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != Activity.RESULT_OK)
            return ;
        if(requestCode == signFragment.requestCode){
            MyData mydata = (MyData) data.getSerializableExtra(signFragment.key);


            bitmap = mydata.getBitmap();
            fileName = mydata.getFileName();
            filePath = mydata.getFilePath();



            Log.e("bitmap", bitmap+"");
            Log.e("fileName", fileName+"");
            Log.e("filePath", filePath+"");


            if(bitmap != null){

                ivSign.setImageBitmap(bitmap);
                ivSign.setVisibility(View.VISIBLE);//显示图片
                tvSign.setVisibility(View.GONE);//隐藏文本

                try {
                    qianziinfo_iv.setVisibility(View.VISIBLE);
                    qianziinfo_tv.setVisibility(View.VISIBLE);
                    qianziinfo_iv.startAnimation(operationAnim);

                    ImageUploadThread iuThread = new ImageUploadThread();
                    iuThread.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    class ImageUploadThread extends Thread{

        @Override
        public void run() {
            super.run();

            String end ="\r\n";
            String twoHyphens ="--";
            String boundary ="*****";

            try{
                URL url =  new URL(urlString+"/Data/Xcx_handSign.ashx");  //服务器端处理流的webservice

                HttpURLConnection con = (HttpURLConnection)url.openConnection();/* 允许Input、Output，不使用Cache */
                con.setDoInput(true);
                con.setDoOutput(true);
                con.setUseCaches(false);
				/* 设置传送的method=POST */
                con.setRequestMethod("POST");
				/* setRequestProperty */
                con.setRequestProperty("Connection", "Keep-Alive");
                con.setRequestProperty("Charset", "UTF-8");
                con.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);/* 设置DataOutputStream */
                DataOutputStream ds = new DataOutputStream(con.getOutputStream());
                ds.writeBytes(twoHyphens + boundary + end);


                /**
                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                ds.writeBytes("Content-Disposition: form-data; "+"name=\"picturePath\";filename=\""+fileName +"\""+ end);
                ds.writeBytes(end);
				/* 取得文件的FileInputStream */
                FileInputStream fStream =new FileInputStream(filePath);
				/* 设置每次写入1024bytes */
                int bufferSize =1024;
                byte[] buffer = new byte[bufferSize];
                int length =-1;
				/* 从文件读取数据至缓冲区 */
                int count = 0;
                while((length = fStream.read(buffer)) !=-1){
					/* 将资料写入DataOutputStream中 */
                    ds.write(buffer, 0, length);
                }
                ds.writeBytes(end);
                ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
				/* close streams */
                fStream.close();
                ds.flush();
				/* 取得Response内容 */
                InputStream is = con.getInputStream();
                int ch;
                StringBuffer b =new StringBuffer();
                while( ( ch = is.read() ) !=-1 ){
                    b.append( (char)ch );
                }
				/* 将Response显示于Dialog */
                String result_upload = b.toString().trim();

                Log.e("上传成功", result_upload+"");

                ds.close();
                Message msg = new Message();
                msg.what = 0;
                Map<String,Object> rMap = new HashMap<String,Object>();
                rMap.put("result", result_upload);
                msg.obj = rMap;
                handler.sendMessage(msg);
            } catch(Exception e){
                Log.e("上传失败", e+"");

                e.printStackTrace();
                Message msg = new Message();
                msg.what = 1;
                Map<String,Object> rMap = new HashMap<String,Object>();
                rMap.put("result", e);
                msg.obj = rMap;
                handler.sendMessage(msg);
            }

        }

    }



    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                Toast.makeText(getActivity(), "签字上传成功",
                        Toast.LENGTH_SHORT).show();

                qianziinfo_iv.clearAnimation();
                qianziinfo_tv.setVisibility(View.GONE);
                qianziinfo_iv.setVisibility(View.GONE);
                qianzidel.setVisibility(View.VISIBLE);
                qianzidel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        ivSign.setVisibility(View.GONE);
                        view.setVisibility(View.GONE);
                        tvSign.setVisibility(View.VISIBLE);

						/*
						 * 清空bmp内存
						 * **/
                        Bitmap bmp = ((BitmapDrawable)ivSign.getDrawable()).getBitmap();
                        if(bmp != null && !bmp.isRecycled()){
                            bmp.recycle();
                            bmp = null;
                        }
                    }
                });
            }else if (msg.what == 1) {
                Toast.makeText(getActivity(), "签字上传失败",
                        Toast.LENGTH_SHORT).show();
            }
        }


    };


}
