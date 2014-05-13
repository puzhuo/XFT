package com.pz.xingfutao.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.pz.xingfutao.XFApplication;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.widget.XFToast;

public class LoginApi extends BaseApi{
	
	private static final String registerUrl = baseUrl + "wap_user_api.php";
	private static final String loginUrl = baseUrl + "wap_user_api.php";
	
	private static final Map<String, String> failureReplacement = new HashMap<String, String>();
	
	static{
		failureReplacement.put("邮件地址不合法", "邮箱好像输错了哦");
		failureReplacement.put("手机号码不是一个有效号码", "手机号码不对吧，亲");
		failureReplacement.put("邮箱已经存在！", "这个邮箱已经注册了哦");
	}
	
	public static String getRegisterUrl(String userName, String password, String email, String phoneNumber){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("act", "search_user_register"));
		params.add(new BasicNameValuePair("username", userName));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("confirm_password", password));
		if(email != null) params.add(new BasicNameValuePair("email", email));
		if(phoneNumber != null) params.add(new BasicNameValuePair("mobile_phone", phoneNumber));
		
		return registerUrl + "?" + URLEncodedUtils.format(params, "UTF-8");
	}
	
	
	public static String getLoginUrl(String userName, String password){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("act", "search_user_login"));
		params.add(new BasicNameValuePair("username", userName));
		params.add(new BasicNameValuePair("password", password));
		
		return loginUrl + "?" + URLEncodedUtils.format(params, "UTF-8");
	}
	
	public static boolean verifySignup(String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.has("result")){
				String result = jsonObject.getString("result");
				if(result.equals("success")){
					String verification = jsonObject.getJSONObject("info").getString("verification");
					if(verification != null && verification.length() > 0 && !verification.equals("null")) XFSharedPreference.getInstance(XFApplication.getInstance()).putSession(verification);
					int userId = jsonObject.getJSONObject("info").getInt("user_id");
					if(userId != 0) XFSharedPreference.getInstance(XFApplication.getInstance()).putUserId(userId);
				}else if(result.equals("fail")){
					String info = jsonObject.getString("info");
					if(info != null && failureReplacement.containsKey(info)){
						XFToast.showTextShort(failureReplacement.get(info));
					}
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean verify(String response){
		try{
			JSONObject jsonObject = new JSONObject(response);
			if(jsonObject.has("result") && jsonObject.getString("result").equals("success")){
				JSONObject infoJson = jsonObject.optJSONObject("info");
				if(infoJson != null && infoJson.has("success")){
					String verification = infoJson.getString("verification");
					if(verification != null && verification.length() > 0 && !verification.equals("null")) XFSharedPreference.getInstance(XFApplication.getInstance()).putSession(verification);
					int userId = infoJson.getInt("user_id");
					if(userId != 0) XFSharedPreference.getInstance(XFApplication.getInstance()).putUserId(userId);
					return true;
				}
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
}
