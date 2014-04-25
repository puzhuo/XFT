package com.pz.xingfutao.utils;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import com.pz.xingfutao.widget.TabView;

@Deprecated
public class TabManager {
	
	private Context context;
	private WindowManager windowManager;
	
	private TabView tabView;
	
	private static TabManager instance;
	
	public static TabManager getInstance(Context context){
		if(instance == null){
			instance = new TabManager(context);
		}
		
		return instance;
	}
	
	private TabManager(Context context){
		this.context = context;
		windowManager = (WindowManager) this.context.getSystemService(Context.WINDOW_SERVICE);
	}
	
	public void createTab(String[] titles, int[] drawableRes, Class<?>[] classes){
		
		if(!(titles.length == drawableRes.length && drawableRes.length == classes.length)) throw new IllegalArgumentException("all array must have the same length");
			
		if(tabView == null){
			tabView = new TabView(context);
		}
		
		tabView.setTab(titles, drawableRes, classes);
		
		WindowManager.LayoutParams tabParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, 50);
		tabParams.type = WindowManager.LayoutParams.TYPE_PHONE;
		tabParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		tabParams.gravity = Gravity.BOTTOM;
		tabParams.format = PixelFormat.RGBA_8888;
		
		windowManager.addView(tabView, tabParams);
	}
}
