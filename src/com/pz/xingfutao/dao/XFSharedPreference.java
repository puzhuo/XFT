package com.pz.xingfutao.dao;

import android.content.Context;
import android.content.SharedPreferences;

public class XFSharedPreference {
	
	private static XFSharedPreference instance;
	
	private SharedPreferences sharedPreferences;
	
	private static final String PREFERENCE_NAME = "pz.xingfutao.sf";
	
	private XFSharedPreference(Context context){
		if(sharedPreferences == null){
			sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		}
	}
	
	public static XFSharedPreference getInstance(Context context){
		if(instance != null){
			instance = new XFSharedPreference(context);
		}
		
		return instance;
	}
}
