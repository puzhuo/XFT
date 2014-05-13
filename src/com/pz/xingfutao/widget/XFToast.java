package com.pz.xingfutao.widget;

import android.widget.Toast;

import com.pz.xingfutao.XFApplication;

public class XFToast {

	public static void showTextShort(int stringRes){
		Toast.makeText(XFApplication.getInstance(), XFApplication.getInstance().getString(stringRes), Toast.LENGTH_SHORT).show();
	}
	
	public static void showTextLong(int stringRes){
		Toast.makeText(XFApplication.getInstance(), XFApplication.getInstance().getString(stringRes), Toast.LENGTH_LONG).show();
	}
	
	public static void showTextShort(String string){
		Toast.makeText(XFApplication.getInstance(), string, Toast.LENGTH_SHORT).show();
	}
	
	public static void showTextLong(String string){
		Toast.makeText(XFApplication.getInstance(), string, Toast.LENGTH_LONG).show();
	}
}
