package com.pz.xingfutao.utils;

import com.pz.xingfutao.XFApplication;
import com.pz.xingfutao.dao.XFSharedPreference;


public class LoginUtil {
	
	public static boolean checkLogin(){
		String v = XFSharedPreference.getInstance(XFApplication.getInstance()).getSession();
		return v != null && v != "";
	}
	
}
