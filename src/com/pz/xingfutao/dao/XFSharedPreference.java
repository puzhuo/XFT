package com.pz.xingfutao.dao;

import android.content.Context;
import android.content.SharedPreferences;

public class XFSharedPreference {
	
	private static XFSharedPreference instance;
	
	private SharedPreferences sharedPreferences;
	
	private static final String PREFERENCE_NAME = "pz.xingfutao.sf";
	
	private static final String SESSION_COOKIE = "PHPSESSION";
	private static final String USER_ID = "USERID";
	private static final String USER_NAME = "USERNAME";
	private static final String RENDERED_PWD = "PWD";
	
	private static final String CONSIGNEE = "CONSIGNEE";
	private static final String PHONE_NUMBER = "PHONE_NUMBER";
	private static final String ADDRESS = "ADDRESS";
	
	private static final String IS_GESTURE_PROTECTED = "IS_GESTURE_PROTECTED";
	private static final String GESTURE = "GESTURE";
	private static final String GESTURE_LENGTH = "GESTURE_LENGTH";
	
	private XFSharedPreference(Context context){
		if(sharedPreferences == null){
			sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		}
	}
	
	public static XFSharedPreference getInstance(Context context){
		if(instance == null){
			instance = new XFSharedPreference(context);
		}
		
		return instance;
	}
	
	public void putGestureList(Integer[] gestures){
		if(gestures == null || gestures.length == 0) return;
		
		sharedPreferences.edit().putInt(GESTURE_LENGTH, gestures.length).commit();
		for(int i = 0; i < gestures.length; i++){
			sharedPreferences.edit().putInt(GESTURE + i, gestures[i]).commit();
		}
	}
	
	public int[] getGestureList(){
		int[] result = new int[sharedPreferences.getInt(GESTURE_LENGTH, 0)];
		
		if(result.length > 0){
			for(int i = 0; i < result.length; i++){
				result[i] = sharedPreferences.getInt(GESTURE + i, -1);
			}
			
			return result;
		}
		
		return null;
	}
	
	public void setIsGestureProtect(boolean isProtect){
		sharedPreferences.edit().putBoolean(IS_GESTURE_PROTECTED, isProtect).commit();
	}
	
	public boolean isGestureProtected(){
		return sharedPreferences.getBoolean(IS_GESTURE_PROTECTED, false);
	}
	
	public void putConsignee(String consignee){
		sharedPreferences.edit().putString(CONSIGNEE, consignee).commit();
	}
	
	public String getConsignee(){
		return sharedPreferences.getString(CONSIGNEE, null);
	}
	
	public void putPhoneNumber(String phoneNumber){
		sharedPreferences.edit().putString(PHONE_NUMBER, phoneNumber).commit();
	}
	
	public String getPhoneNumber(){
		return sharedPreferences.getString(PHONE_NUMBER, null);
	}
	
	public void setAddress(String address){
		sharedPreferences.edit().putString(ADDRESS, address).commit();
	}
	
	public String getAddress(){
		return sharedPreferences.getString(ADDRESS, null);
	}
	
	public void putUserName(String userName){
		sharedPreferences.edit().putString(USER_NAME, userName).commit();
	}
	
	public String getUserName(){
		return sharedPreferences.getString(USER_NAME, null);
	}
	
	public void putRenderedPwd(String pwd){
		sharedPreferences.edit().putString(RENDERED_PWD, pwd).commit();
	}
	
	public String getRenderedPwd(){
		return sharedPreferences.getString(RENDERED_PWD, null);
	}
	
	public void putSession(String value){
		sharedPreferences.edit().putString(SESSION_COOKIE, value).commit();
	}
	
	public String getSession(){
		return sharedPreferences.getString(SESSION_COOKIE, null);
	}
	
	public void putUserId(int id){
		sharedPreferences.edit().putInt(USER_ID, id).commit();
	}
	
	public int getUserId(){
		return sharedPreferences.getInt(USER_ID, -1);
	}
	
	public void putString(String key, String value){
		sharedPreferences.edit().putString(key, value).commit();
	}
	
	public String getString(String key, String defaultValue){
		return sharedPreferences.getString(key, defaultValue);
	}
}
