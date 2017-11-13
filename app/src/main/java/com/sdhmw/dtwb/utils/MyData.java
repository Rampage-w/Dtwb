package com.sdhmw.dtwb.utils;

import java.io.Serializable;

import android.graphics.Bitmap;


//fragment之间传递参数的 中间 存储数据的类
public class MyData implements Serializable{

	public Bitmap bitmap;  
	public String fileName;
	public String filePath;
	  
    
    
    public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	
	
	
	
	
    
    
}
