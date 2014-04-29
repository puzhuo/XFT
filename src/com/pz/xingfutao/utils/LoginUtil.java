package com.pz.xingfutao.utils;

import android.content.Context;
import android.content.Intent;

import com.pz.xingfutao.ui.sub.LoginActivity;

public class LoginUtil {
	
	public static boolean verify(Context context){
		Intent intent = new Intent(context, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		context.startActivity(intent);
		
		return false;
	}
}
