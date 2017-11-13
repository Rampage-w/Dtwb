package com.sdhmw.dtwb.utils;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


public class ImageUploadThread extends Thread{

	public int SUCCESS; //图片由客户端分给服务器的返回码
	public int RESULT;  //服务器上传照片的返回码
	public int FAILURE; //操作失败的返回码
	
	public Handler handler;  //回调函数的句柄
	public String urlString; //发送的url
	public String filePath;  //发送的文件路径
	public String fileName;  //发送的文件名称
	public CustomView6 customView;  //页面的对应遮罩层
	public long fileLength;  //发送的文件大小
	public int progress = 0; //客户端发给服务端的进度
	public String type;
	public Object infoObject;
	
	public String checkType;
	
	public ImageUploadThread(String type,Handler handler,String urlString,String filePath,String fileName,CustomView6 customView,
								long fileLength,int SUCCESS,int RESULT,int FAILURE,Object infoObject,String checkType){
		this.type = type;
		this.handler = handler;
		this.urlString = urlString;
		this.filePath = filePath;
		this.fileName = fileName;
//		this.customView = customView;
		this.fileLength = fileLength;
//		this.SUCCESS = SUCCESS;
		this.FAILURE = FAILURE;
		this.RESULT = RESULT;
		this.infoObject = infoObject;	
		Log.e("=======filePath=========", this.filePath);
		
		this.checkType = checkType;
	}
	
	@Override
	public void run() {
		int SUCCESS=this.SUCCESS; //图片由客户端分给服务器的返回码
		int RESULT=this.RESULT;  //服务器上传照片的返回码
		int FAILURE=this.FAILURE; //操作失败的返回码
		
		Handler handler=this.handler;  //回调函数的句柄
		String urlString=this.urlString; //发送的url
		String filePath=this.filePath;  //发送的文件路径
		String fileName=this.fileName;  //发送的文件名称
		CustomView6 customView=this.customView;  //页面的对应遮罩层
		long fileLength=this.fileLength;  //发送的文件大小
		int progress = this.progress; //客户端发给服务端的进度
		String type=this.type;
		Object infoObject=this.infoObject;
		
		
		
		String end ="\r\n";        
		String twoHyphens ="--";        
		String boundary ="*****";
		try{
			//	月度检查存文件的
			//	/Data/CRM_web_ydjc_att
			
			URL url = null;
			if(checkType.equals("年度检查")){
				url = new URL(urlString+"/Data/CRM_web_ndjc_att.ashx");  //服务器端处理流的webservice        
			}else if (checkType.equals("在线检验")) {
				url = new URL(urlString+"/Data/CRM_web_zxjy_att.ashx");  
			}else if(checkType.equals("月度检查")){
				url = new URL(urlString+"/Data/CRM_web_qzjx_ydjc_att.ashx");  // CRM_web_qzjx_ydjc_att  /file/ ydjc_qzjx_att/
			}else if(checkType.equals("全面检查")){
				url = new URL(urlString+"/Data/CRM_web_qzjx_qmjc_att.ashx");  //CRM_web_qzjx_qmjc_att  /file/qmjc_qzjx_att/
			}
			
			
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
//			ds.writeBytes("&type=zdry");
//			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + end);      
			
			
			 /**
			 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
			 * filename是文件的名字，包含后缀名的 比如:abc.png
		     */
//			ds.writeBytes("Content-Disposition: form-data; "+"name=\"file1\";filename=\""+fileName +"\""+ end);          
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
//				count += bufferSize;
//				
//				
//				progress = (int) (((float) count / fileLength) * 100);
//				if(length <= 0 || progress > 100){
//					customView.setProgress(100);
//					progress = 100;
//				}else{
//					customView.setProgress(progress);
//				}
//				
//				if(progress > 100 || progress == 100){//图片上传完成
//					Message msg = new Message();
//					msg.what = SUCCESS;
//					handler.sendMessage(msg);
//				}
				
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
			msg.what = RESULT;
			Map<String,Object> rMap = new HashMap<String,Object>();
			rMap.put("result", result_upload);
			rMap.put("infoObject", infoObject);
			msg.obj = rMap;
			handler.sendMessage(msg);
		} catch(Exception e){       
			System.out.println("上传失败"+e);
			e.printStackTrace();
			Message msg = new Message();
			msg.what = FAILURE;
			Map<String,Object> rMap = new HashMap<String,Object>();
			rMap.put("result", e);
			rMap.put("infoObject", infoObject);
			msg.obj = rMap;
			handler.sendMessage(msg);
		}      

	}
	
}
