package com.pz.xingfutao.net;

import java.util.Map;

import android.content.Context;

import com.pz.xingfutao.XFApplication;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.utils.PLog;

public class SessionHandler {
	
	private static final String SET_COOKIE_KEY = "Set-Cookie";
	private static final String COOKIE_KEY = "Cookie";
	private static final String SESSION_COOKIE = "VERIFICATION";
	
	public static final void checkSessionCookie(Context context, Map<String, String> headers){
		PLog.d("sdaf", "asdf");
		PLog.d("header", headers.toString());
		if(headers.containsKey(SET_COOKIE_KEY) && headers.get(SET_COOKIE_KEY).contains(SESSION_COOKIE)){
			String cookie = headers.get(SET_COOKIE_KEY);
			if(cookie.length() > 0){
				String[] splitCookie = cookie.split(";");
				String[] splitSessionId = splitCookie[0].split("=");
				cookie = splitSessionId[1];
				PLog.d("session", cookie);
				XFSharedPreference.getInstance(XFApplication.getInstance()).putSession(cookie);
			}
		}
	}
	
	public static final void addSessionCookie(Context context, Map<String, String> headers){
		
		String sessionId = XFSharedPreference.getInstance(XFApplication.getInstance()).getSession();
		if(sessionId != null && sessionId.length() > 0){
			PLog.d("session", sessionId);
			StringBuilder builder = new StringBuilder();
			builder.append(SESSION_COOKIE);
			builder.append("=");
			builder.append(sessionId);
			if(headers.containsKey(COOKIE_KEY)){
				builder.append(";");
				builder.append(headers.get(COOKIE_KEY));
			}
			
			headers.put(COOKIE_KEY,  builder.toString());
			
		}
		
		//headers.put("Cache-Control", "max-age=0");
		/*
		
		headers.put("Connection", "keep-alive");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*\/*;q=0.8");
		headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
		headers.put("Accept-Encoding", "gzip,deflate,sdch");
		headers.put("Accept-Language", "zh-CN,zh;q=0.8");
		 */

		
		PLog.d("request_header", headers.toString());
	}
}
