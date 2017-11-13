package com.sdhmw.dtwb.net;

import android.util.Log;

import com.sdhmw.dtwb.model.UserInfo;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class WebServiceManager {

	public static final String TIME_OUT = "timeout"; // 连接超时
	public static final String NET_ERROR = "netError"; // 网络异常
	
	private String nameSpace = "http://tempuri.org/";

	private String endPoint = "http://192.168.10.3:8120/WebService/Rm_ForAndroid.asmx";//服务器的防火墙一定要开端口(于小清)
//	private String endPoint = "http://192.168.10.10:845/WebService/Rm_ForAndroid.asmx";//服务器的防火墙一定要开端口（10）
//	private String endPoint = "http://218.59.191.198:845/WebService/Rm_ForAndroid.asmx";//服务器的防火墙一定要开端口（10外网）

	private String username;
	private String password;


	public String HelloWorld(){
		String[][] paras = {};
		String result = WebServiceConnect("HelloWorld", paras);
		return result;
	}

	//一、首页
	//1.维保单位的电梯数量
	public String getWbdwIndex(String unitid) {

		String[][] paras = {{"unitid",unitid}};
		String result = WebServiceConnect("getWbdwIndex", paras);
		return result;
	}

	//2、维保及时率 	getWbdwWbjsl(string start_rq,string end_rq,string unitid,string wblb)

	public String getWbdwWbjsl(String start_rq,String end_rq,String unitid) {

		String[][] paras = {{"start_rq",start_rq},{"end_rq",end_rq},{"unitid",unitid}};
		String result = WebServiceConnect("getWbdwWbjsl", paras);
		return result;
	}

	//3.电梯故障率
	public String getDtGzl(String start_rq,String end_rq,String unitid) {
		String[][] paras = {{"start_rq",start_rq},{"end_rq",end_rq},{"unitid",unitid}};
		String result = WebServiceConnect("getDtGzl", paras);
		return result;
	}


	//二、维保管理
	//1.所在区域
	public String getQuxian() {
		String[][] paras = {};
		String result = WebServiceConnect("getQuxian", paras);
		return result;
	}
	//2.维保管理查询
	public String getWbdwEle(String unitid,String quxian,String sydw,String zcdm) {
		String[][] paras = {{"unitid",unitid},{"quxian",quxian},{"sydw",sydw},{"zcdm",zcdm}};
		String result = WebServiceConnect("getWbdwEle", paras);
		return result;
	}

	//3.选择电梯 选择维保种类  进行维保
	//3.1电梯维保_基本信息：getElexq(string zcdm)
	public String getElexq(String zcdm) {
		String[][] paras = {{"zcdm",zcdm}};
		String result = WebServiceConnect("getElexq", paras);
		return result;
	}
	//3.2 电梯维保_机房, 电梯维保_井道，电梯维保_轿厢，电梯维保_地坑:
    // getWbxm(string wb_type,string leibie) Wb_type:半月，季度，半年，年度，是CRM_ckZh_wbxm表中的flag

    public String getWbxm(String wb_type,String leibie) {
        String[][] paras = {{"wb_type",wb_type},{"leibie",leibie}};
        String result = WebServiceConnect("getWbxm", paras);
        return result;
    }


	
	//参数名称必须和后台webservice中的参数名称一样
	public UserInfo getLogin(String strUserName,String strPassWord){

		this.username = strUserName;
		this.password = strPassWord;

		UserInfo userInfo = null;
		String[][] paras = {{"strUserName",strUserName},{"strPassWord",strPassWord}};
		String result = WebServiceConnect("getLogin", paras);

		Log.e("登录的result", result+"");

		if (result.equals("timeout") || result.equals("netError")) {

			userInfo = null;
        } else if (result.equals("3")) {
            userInfo = new UserInfo();
            userInfo.setUnittype("null");

        } else {
            try {
                JSONObject userJson = new JSONObject(result);
                userInfo = convertUserInfoFromJson(userJson);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return userInfo;
	}


	/**
	 * 将从服务器获取的json对象转换成用户信息对象
	 */
	private UserInfo convertUserInfoFromJson(JSONObject factoryJson) throws JSONException{


		UserInfo userInfoConvert = null;
		JSONArray ja = factoryJson.getJSONArray("Rows");
		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo_item = ja.getJSONObject(i);

			userInfoConvert = new UserInfo();
			userInfoConvert.setUnitid(jo_item.getString("unitid"));
			userInfoConvert.setUnittype(jo_item.getString("unittype"));
			userInfoConvert.setUnitname(jo_item.getString("unitname"));
		}
		return userInfoConvert;

	}



	/**
	 * 连接web服务
	 */
	private String WebServiceConnect(String methodName, String[][] paras) {

		// 指定WebService的命名空间和调用的方法名
		SoapObject rpc = new SoapObject(nameSpace, methodName);
		SoapSerializationEnvelope envelope = null;
		//设置需要调用WebService接口的两个参数
		for (int i = 0; i < paras.length; i++) {
			rpc.addProperty(paras[i][0], paras[i][1]);
		}
		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(endPoint,2000);		//android连接服务器的时间	 连接服务器超时的提示

		try {
			// 调用WebService
			transport.call(nameSpace + methodName, envelope);
		} catch (SocketTimeoutException e) {
			// 连接超时
			return TIME_OUT;
		} catch (ConnectException e) {
			return NET_ERROR;
		} catch (Exception e) {
			return e.getMessage();
		}

		// 获取返回的数据
		String result = null;
		SoapObject object = null;

		try {
			object = (SoapObject) envelope.bodyIn;
			// 获取返回的结果
			if (object != null) {
				result = object.getProperty(0).toString();
				return result;
			}
			return "";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
