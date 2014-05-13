package com.pz.xingfutao;

import android.app.Application;

public class XFApplication extends Application {
	
	private static XFApplication instance;
	
	@Override
	public void onCreate(){
		super.onCreate();
		
		instance = this;
	}
	
	public static XFApplication getInstance(){
		return instance;
	}
}
