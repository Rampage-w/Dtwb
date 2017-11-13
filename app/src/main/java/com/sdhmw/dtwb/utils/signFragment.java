package com.sdhmw.dtwb.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdhmw.dtwb.main.R;

public class signFragment extends DialogFragment{

	private View view;
	private Button cancel;
	private Button clear;
	private Button sure;
	
	
	private LinePathView mPathView;
	private Bitmap bitmap;
	public String filePath;  //发送的文件路径
	public String fileName;  //发送的文件名称
	
	
	//fragment 之间传递参数
	public static int requestCode = 0;
	public static String key = "signfragment.key";  
	
	
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.signfragment, container,false);
		
		initView();
		
		initListener();
		
		return view;
		
	}

	
	
	
	
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		
		Log.e("onDismiss", "onDismiss");
		
	}


	final public static int REQUEST_CODE = 123;

	public void SDCard(){
		if (Build.VERSION.SDK_INT >= 23) {
			int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
			if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
				ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
				return;
			}else{
				//方法
				//手写签名 传递到fragment5
				signature();
			}
		} else {
			//方法
			//手写签名 传递到fragment5
			signature();
		}
	}




	private void initView() {
		//签名画图区域
		mPathView = (LinePathView) view.findViewById(R.id.imageupload_linePathView);
		
		cancel = (Button) view.findViewById(R.id.cancel);
		clear = (Button) view.findViewById(R.id.clear);
		sure = (Button) view.findViewById(R.id.sure);
		
	}
	
	private void initListener() {
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Log.e("cancel", "cancel");
				
				getDialog().dismiss();
				
			}
		});
		clear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				mPathView.clear();	
				
			}
		});
		sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {


				System.out.println("确定 读取SD卡长传签字");

				//读取SD卡的权限
				SDCard();

				//手写签名 传递到fragment5
//				signature();
				
				
			}
		});
	}


	private void signature() {
		
		if(mPathView.getTouched()){
			try {
				
				String filePathTmp = Environment
						.getExternalStorageDirectory()
						+ File.separator
						+ "Dtwb"
						+ File.separator
						+ "uploadTmp";
				
				File file = new File(filePathTmp);
				if(!file.exists()){
					file.mkdirs();
				}
				fileName = UUID.randomUUID().toString()
						+ ".png";
				
				filePath = filePathTmp + File.separator
						+ fileName;
				
				mPathView.save(filePath, true, 10);	//只留下签名范围 其余为黑色

				
				bitmap = mPathView.getBitMap();
				
				Log.e("bitmap", bitmap+"");

				
				MyData data = new MyData();
				data.setBitmap(bitmap);		
				data.setFileName(fileName);
				data.setFilePath(filePath);
				
				Intent intent = new Intent();
				intent.putExtra(key, data);
				//用于发送消息给fragment5
				getTargetFragment().onActivityResult(requestCode, Activity.RESULT_OK, intent); 
				
				
				Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
				
				getDialog().dismiss();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		  }else {
			Toast.makeText(getActivity(), "您没有签名~", Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
