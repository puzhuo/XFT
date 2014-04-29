package com.pz.xingfutao.utils;

import java.lang.reflect.Field;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class SystemMeasurementUtil {
	
	private static int statusBarHeight;
	
	private static int screenWidth;
	private static int screenHeight;
	
	private static float density;
	
	private static String telNumber;
	private static String deviceId;
	
	private static String appId;

	public static int getStatusBarHeight(Context context){
		if(statusBarHeight == 0){
			try{
				Class<?> clazz = Class.forName("com.android.internal.R$dimen");
				Object o = clazz.newInstance();
				Field field = clazz.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = context.getResources().getDimensionPixelSize(x);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return statusBarHeight;
	}
	
	public static int[] getScreenMeasurement(Context context){
		if(screenWidth == 0 || screenHeight == 0){
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(dm);
			
			screenWidth = dm.widthPixels;
			screenHeight = dm.heightPixels;
			
			density = dm.density;
		}
		
		return new int[]{screenWidth, screenHeight};
	}
	
	public static float getScreenDensity(Context context){
		if(density == 0){
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(dm);
			
			screenWidth = dm.widthPixels;
			screenHeight = dm.heightPixels;
			
			density = dm.density;
		}
		
		return density;
	}
	
	public static String getTelNumber(Context context){
		if(telNumber == null){
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			telNumber = telephonyManager.getLine1Number();
			
			deviceId = telephonyManager.getDeviceId();
		}
		
		return telNumber;
	}
	
	public static String getDeviceId(Context context){
		if(deviceId == null){
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			telNumber = telephonyManager.getLine1Number();
			
			deviceId = telephonyManager.getDeviceId();
		}
		
		return deviceId;
	}
	
	public static String getAppId(Context context){
		if(appId == null){
			try{
				appId = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData.getString("pzad_app_id");
			}catch(NameNotFoundException e){
				e.printStackTrace();
			}catch(NullPointerException e){
				e.printStackTrace();
			}
		}
		
		return appId;
	}
}
