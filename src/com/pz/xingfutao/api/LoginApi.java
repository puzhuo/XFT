package com.pz.xingfutao.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class LoginApi extends BaseApi{
	
	private static final String registerUrl = baseUrl + "wap_user_api.php";
	private static final String loginUrl = baseUrl + "wap_user_api.php?act=search_user_login&username=admin&password=wang131426";
	
	public static String getRegisterUrl(String userName, String password, String email, String phoneNumber){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("act", "search_user_register"));
		params.add(new BasicNameValuePair("username", userName));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("confirm_password", password));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("mobile_phone", phoneNumber));
		
		return registerUrl + "?" + URLEncodedUtils.format(params, "UTF-8");
	}
	
	
	public static String getLoginUrl(String userName, String password){
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("act", "search_user_login"));
		params.add(new BasicNameValuePair("username", userName));
		params.add(new BasicNameValuePair("password", password));
		
		return loginUrl + "?" + URLEncodedUtils.format(params, "UTF-8");
	}
	
}
