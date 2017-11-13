package com.sdhmw.dtwb.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sdhmw.dtwb.net.WebServiceManager;
import com.sdhmw.dtwb.utils.ImageTools;
import com.sdhmw.dtwb.utils.ImageUploadThread;
import com.sdhmw.dtwb.utils.ReSetBitMapSize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by wanglingsheng on 2017/9/26.
 *
 * 拍照
 *
 *
 */

public class Fragment_wbgl_wb_6 extends Fragment {


    private View rootView;
    private ProgressDialog progressdialog;//等待进度条   (点击保存的时候显示)

    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String LOG_TAG = "Camera";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 300;
    private static final int SELECT_PICTURE = 400;
    private static final int SCALE = 5;// 照片缩小比例

    private String uploadPath = "";// 传递到ImageUploa

    private LinearLayout picThumbLayout;
    private LinearLayout.LayoutParams lp;
    private ImageView imgLayout;


    // 照片
    private LinearLayout photoLayout;// 照片以及照片上传信息和删除按钮所在容器
    private LinearLayout photoInfoLayout;
    private TextView photoInfo;// 照片上传显示的信息
    private ImageView photoInfoImg;// 照片上传信息的图片
    private Button photoDel;// 照片的删除框
    private String photoName;// 传照片名
    private String photoResult;// 图片上传的结果
    private ImageUploadThread imageUpload;// 图片上传线程
    private Animation operationAnim;

    private List<Object> zhpTextView = new ArrayList<Object>();// 照片内容向数据库传

    /**
     * 图片下载
     * **/
    DisplayImageOptions options;


    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private String url_string = "";//查询地址


    //net服务器传过来的上传成功图片的名字
    private String nowfileName = "";
    //文件大小
    private String file_size = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_wbgl_wb_6, container, false);

        initView(rootView);


        //显示图片的所有配置
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.neiye_sp)	//设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.neiye_sp)	//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.neiye_sp)		//设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)						//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)							//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)					//是否考虑JPEG图像EXIF参数（旋转，翻转）
                .bitmapConfig(Bitmap.Config.RGB_565)		//设置图片的解码类型
                .build();									//构建完成

        /*
		 * 获得显示设备高宽
		 * */
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //显示器宽
        int screenwidth = metrics.widthPixels;
        //显示器高
        int screenheight = metrics.heightPixels;
        //dpi
        int dpi = metrics.densityDpi;
        Log.v("显示器的dip", dpi+"");
        int kong = (int) ((dpi/160.0)*5);
        int offwidth = (screenwidth-4*kong)/3;
        int m = (int) (5*(dpi/160.0));
        lp = new LinearLayout.LayoutParams(offwidth, offwidth);
        lp.setMargins(5, 5, 5, 0);



        return rootView;
    }

    private void initView(View rootView) {

        operationAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.imagerotate);
        LinearInterpolator lin = new LinearInterpolator();//修饰动画效果 变化率
        operationAnim.setInterpolator(lin);

        LayoutInflater mInflater = LayoutInflater.from(getContext());

        //上传照片 控件初始化
        //照片
        LinearLayout piclayout = (LinearLayout) rootView.findViewById(R.id.upload_addpiclayout);
        //添加一张照片
        View picViewLay = mInflater.inflate(R.layout.item_wbglpic,null);

        photoLayout = (LinearLayout) picViewLay.findViewById(R.id.wbgl_photoLayout);//整个item（包含照片添加和删除的按钮）
        picThumbLayout = (LinearLayout) picViewLay.findViewById(R.id.wbgl_picThumbLayout);//拇指 区域
        photoInfoLayout = (LinearLayout) picViewLay.findViewById(R.id.wbgl_photoInfoLayout);//照片显示 区域
        photoInfo = (TextView) picViewLay.findViewById(R.id.wbgl_photoInfo);//照片正在上传  文字提示
        photoInfoImg = (ImageView) picViewLay.findViewById(R.id.wbgl_photoInfoImg);//照片上传的动态图
        photoDel = (Button) picViewLay.findViewById(R.id.wbgl_photoDel);//照片右上角的删除按钮


        // 提示信息隐藏
        photoInfo.setVisibility(View.GONE);
        photoInfoImg.setVisibility(View.GONE);
        photoDel.setVisibility(View.GONE);


        /*
		 * 获得显示设备高宽
		 */
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // 显示器宽
        int screenwidth = metrics.widthPixels;
        // 显示器高
        int screenheight = metrics.heightPixels;
        // dpi
        int dpi =metrics.densityDpi;

        Log.v("显示器的dip", dpi + "");

        int kong = (int) ((dpi / 160.0) * 5);
        int offwidth = (screenwidth - 8 * kong) / 3;
        LinearLayout.LayoutParams alp = new LinearLayout.LayoutParams(offwidth, offwidth);
        // left top right bottom
        alp.setMargins(kong, kong, 0, kong);
        photoLayout.setLayoutParams(alp);
        // picThumbLayout.setLayoutParams(alp);
        photoInfoLayout.setVisibility(View.GONE);//照片显示区域 隐藏


        //加号(上传照片)
        picThumbLayout
                .setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        view.requestFocus();
                        alertAddPhoToDialog();
                    }
                });

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.setMargins(30, 30, 30, 30);


        //拍照的区域的位置
        piclayout.addView(picViewLay,lp2);




        // 添加
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);

    }

    //上传照片
//    uploadImageThread ut = new uploadImageThread();
//	    ut.start();


    //保存线程--> 年度检查报告图片 照片上传
    class uploadImageThread extends Thread {
        @Override
        public void run() {
            super.run();

//            WebServiceManager service = new WebServiceManager();
//
//            String result = service.getLogin("1","1");
//
//            Message msg = new Message();
//            msg.what = 0;
//            msg.obj = result;


        }
    }


    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == 0){

            }
        }
    };


    //开启权限
    final public static int REQUEST_CODE = 123;

    public void permission_CAMERA(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},REQUEST_CODE);
                return;
            }else{

                //方法
                takePicture();
            }
        } else {
            //方法
            takePicture();
        }
    }


    // ------------------------------------------照片-------------------------------------------------
    // 照片添加功能
    private void alertAddPhoToDialog() {
        AlertDialog alert = new AlertDialog.Builder(getActivity())
                .setItems(new String[] { "相机", "相册" },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface interf,
                                                int position) {
                                if (position == 0) {

                                    //打开拍照权限
                                    permission_CAMERA();

//                                    takePicture();

                                } else {
                                    openAmbu();
                                }
                            }
                        }).create();
        alert.setCanceledOnTouchOutside(true);
        alert.show();
    }

    private void openAmbu() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "选择图片"),
                SELECT_PICTURE);
    }

    // 拍照
    private void takePicture() {
        Log.d("PIC", "Take Picture Button Click");
        // 利用系统自带的相机应用:拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // create a file to save the image
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
        // set the image file name
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = null;
        try {
            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MyCameraApp");

            Log.d(LOG_TAG, "Successfully created mediaStorageDir: "
                    + mediaStorageDir);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in Creating mediaStorageDir: "
                    + mediaStorageDir);
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // 在SD卡上创建文件夹需要权限：
                // <uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                Log.d(LOG_TAG,
                        "failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        // else if (type == MEDIA_TYPE_VIDEO){
        // mediaFile = new File(mediaStorageDir.getPath() + File.separator
        // + "VID_" + timeStamp + ".mp4");
        // }
        else {
            return null;
        }

        return mediaFile;
    }



    //Activity中得到新打开Activity关闭后返回的数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "onActivityResult: requestCode: " + requestCode
                + ", resultCode: " + requestCode + ", data: " + data);


        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){	//拍照

            imgLayout = new ImageView(getActivity().getApplicationContext());

            if(resultCode == RESULT_OK){
                photoName = "";
                uploadPath = "";

                photoLayout.setVisibility(View.VISIBLE);
                photoInfo.setVisibility(View.VISIBLE);
                photoInfo.setText("正在上传照片...");
                photoInfoImg.setVisibility(View.VISIBLE);
                photoInfoImg.setImageDrawable(getResources().getDrawable(
                        R.drawable.load1));
                photoInfoImg.startAnimation(operationAnim);//开始动画

                photoName = "";
                uploadPath = "";
                Log.d(LOG_TAG, "RESULT_OK");
                // Check if the result includes a thumbnail Bitmap
                String fileDir = Environment.getExternalStorageDirectory()	//获取SD卡的根目录
                        + File.separator + Environment.DIRECTORY_DCIM;	//在SD卡上创建目录

                Bitmap bmp = null;//Bitmap Android系统中的图像处理的最重要类之一

                try {
                    if(data!= null){
                        // 没有指定特定存储路径的时候
                        Log.d(LOG_TAG,
                                "data is NOT null, file on default position.");
                        // 指定了存储路径的时候（intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);）
                        // Image captured and saved to fileUri specified in the
                        Toast.makeText(getActivity(),
                                "Image saved to:\n" + data.getData(),
                                Toast.LENGTH_LONG).show();
                        if (data.hasExtra("data")) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inTempStorage = new byte[1024 * 1024 * 2];	//内存限制
                            options.inSampleSize = 2;						//图片缩放比
                            bmp = BitmapFactory.decodeFile(fileDir, options);
                            imgLayout.setImageBitmap(bmp);
                        }

                    }else {
                        Log.d(LOG_TAG,
                                "data IS null, file saved on target position.");
					/*BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
					factoryOptions.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(fileUri.getPath(),
							factoryOptions);
					int imageWidth = factoryOptions.outWidth;
					int imageHeight = factoryOptions.outHeight;
					int scaleFactor = Math.min(imageWidth / 100,
							imageHeight / 100);
					factoryOptions.inJustDecodeBounds = false;
					factoryOptions.inSampleSize = scaleFactor;
					factoryOptions.inPurgeable = true;*/

					/*bmp = BitmapFactory.decodeFile(fileUri.getPath(),
							factoryOptions);*/

                        bmp = getThumbnail(fileUri , ReSetBitMapSize.SIZE);

                        //照片旋转
                        int degree = getBitmapDegree(fileUri.getPath());
                        bmp = rotateBitmapByDegree(bmp, degree);

                        //imgLayout.setImageBitmap(bmp);
                        //显示图片

                        //loadImageByVolley(fileUri.toString(),imgLayout);
                    }

                    FileOutputStream photo = null;
                    File file = new File(fileDir);
                    file.mkdir();

                    String fileId = UUID.randomUUID().toString() + ".jpg";
                    photoName = fileId;
                    String fileName = fileDir + File.separator + fileId;
                    uploadPath = fileName;
                    imgLayout.setTag(fileName);
                    //long fileLength = 0;


				/*
				 * 复制图片显示原来图片
				 * */

                    photo = new FileOutputStream(fileName);
                    //复制图片
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, photo);
                    photo.flush();
                    bmp.recycle();

                    loadImageByVolley("file:///"+fileName,imgLayout);

                    /**
                     * 插入图片
                     * **/
				/*MediaStore.Images.Media.insertImage(
						getApplicationContext().getContentResolver(),
						fileName, new File(fileName).getName(),
						new File(fileName).getName());
				Intent intent = new Intent(
						Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.fromFile(new File(fileName));
				intent.setData(uri);
				getApplicationContext().sendBroadcast(intent);*/

                    File photoFile = new File(fileName);
                    FileInputStream photoIn = new FileInputStream(
                            photoFile);

                    //判断流的长度
                    long fileLength = photoIn.available();//返回的实际可读字节数，也就是总大小
                    photoIn.close();

                    file_size = Long.toString(fileLength);


                    Map<String, Object> rMap = new HashMap<String, Object>();
                    rMap.put("filename", fileId);// 文件名
                    rMap.put("photoInfo", photoInfo);// 提示
                    rMap.put("piclayout", picThumbLayout);
                    rMap.put("photoInfoImg", photoInfoImg);// 图片
                    rMap.put("delbutton", photoDel);// 删除键

                    // 上传照片
                    imageUpload = new ImageUploadThread(
                            "safeCheck", handler, url_string, fileName,
                            fileId, null, fileLength, 0, 5, 6, rMap,"年度检查");
                    imageUpload.start();


                } catch (Exception e) {
                    e.printStackTrace();
                }

                picThumbLayout.removeAllViews();
                picThumbLayout.addView(imgLayout, lp);


            }else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            }else {
                // Image capture failed, advise user
            }
        }else if(requestCode == SELECT_PICTURE) {	//选择照片
            imgLayout = new ImageView(getActivity().getApplicationContext());

            if (resultCode == RESULT_OK) {

                photoName = "";
                uploadPath = "";

                photoLayout.setVisibility(View.VISIBLE);
                photoInfoLayout.setVisibility(View.VISIBLE);
                photoInfo.setVisibility(View.VISIBLE);
                photoInfo.setText("正在上传照片...");
                photoInfoImg.setVisibility(View.VISIBLE);
                photoInfoImg.setImageDrawable(getResources().getDrawable(
                        R.drawable.load1));
                photoInfoImg.startAnimation(operationAnim);
                // 上传照片名字
                photoName = "";
                // 上传照片路径
                uploadPath = "";

                ContentResolver resolver = getActivity().getContentResolver();
                // 照片的原始资源地址
                Uri originalUri = data.getData();

                try {
                    // 使用ContentProvider通过URI获取原始图片
					/*Bitmap photo = MediaStore.Images.Media.getBitmap(
							resolver, originalUri);*/
                    Bitmap photo = getThumbnail(originalUri, ReSetBitMapSize.SIZE);
                    if (photo != null) {
                        // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                        Bitmap smallBitmap = ImageTools.zoomBitmap(
                                photo,
                                (photo.getWidth() / SCALE < 1 ? 1 : photo
                                        .getWidth() / SCALE),
                                (photo.getHeight() / SCALE < 1 ? 1 : photo
                                        .getHeight() / SCALE));
                        // 释放原始图片占用的内存，防止out of memory异常发生
                        String filePathTmp = Environment
                                .getExternalStorageDirectory()
                                + File.separator
                                + "sdaf"
                                + File.separator
                                + "uploadTmp";
                        File fpTmp = new File(filePathTmp);
                        if (!fpTmp.exists()) {
                            fpTmp.mkdirs();
                        }
                        String fileId = UUID.randomUUID().toString()
                                + ".jpg";
                        photoName = fileId;
                        String filePath = filePathTmp + File.separator
                                + fileId;
                        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                        int quality = 100;
                        OutputStream stream = null;
                        stream = new FileOutputStream(filePath);

						/*
						 * 复制图片
						 *
						 * 删除原bitmap
						 *
						 * */
                        photo.compress(format, quality, stream);
                        uploadPath = filePath;
                        imgLayout.setTag(filePath);
                        photo.recycle();

                        //显示图片
                        //imgLayout.setImageBitmap(smallBitmap);
                        loadImageByVolley("file:///"+filePath,imgLayout);


                        File photoFile = new File(filePath);
                        FileInputStream photoIn = new FileInputStream(
                                photoFile);
                        long fileLength = photoIn.available();
                        photoIn.close();

                        file_size = Long.toString(fileLength);

                        Map<String, Object> rMap = new HashMap<String, Object>();
                        rMap.put("filename", fileId);// 文件名
                        rMap.put("photoInfo", photoInfo);// 提示
                        rMap.put("piclayout", picThumbLayout);
                        rMap.put("photoInfoImg", photoInfoImg);// 图片
                        rMap.put("delbutton", photoDel);// 删除键

                        // 上传照片
                        imageUpload = new ImageUploadThread(
                                "safeCheck", handler, url_string, filePath,
                                fileId, null, fileLength, 0, 5, 6, rMap,"年度检查");
                        imageUpload.start();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                picThumbLayout.removeAllViews();
                // 添加照片到 layout
                picThumbLayout.addView(imgLayout, lp);

            }else {
                Toast.makeText(getActivity(), "请重新选择图片",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }




    /**
     *
     * 将原始图片缩小
     *
     * **/

    public  Bitmap getThumbnail(Uri uri,int size) throws FileNotFoundException, IOException{
        InputStream input = getActivity().getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        Log.e("zzzz", originalSize+"");
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = getActivity().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }


    //图片
    protected void loadImageByVolley(final String photoPath,ImageView photo) {

        //image load
        imageLoader.displayImage(photoPath, photo, options,new SimpleImageLoadingListener() {
            @Override
            //图片下载完成
            public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                //点击放大事件
                view.setOnClickListener(new View.OnClickListener() {
                    private Bitmap loadedImage1 = loadedImage;
                    @Override
                    public void onClick(View view) {
                        LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());
                        View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
                        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                        ImageView img = (ImageView)imgEntryView.findViewById(R.id.large_image);
                        img.setImageBitmap(loadedImage1);
                        //imageLoader.get(photoPath, ImageLoader.getImageListener(img, R.drawable.neiye_sp, R.drawable.neiye_sp));
                        dialog.setView(imgEntryView); // 自定义dialog
                        dialog.show();
                        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
                        imgEntryView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View paramView) {
                                dialog.cancel();
                            }
                        });//
                    }
                });//点击事件

            }
        });
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path
     *            图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm
     *            需要旋转的图片
     * @param degree
     *            旋转角度
     * @return 旋转后的图片
     */
    private static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        //根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }



}
