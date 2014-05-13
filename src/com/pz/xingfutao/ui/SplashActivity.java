package com.pz.xingfutao.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.pz.xingfutao.R;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.ui.tab.TabActivity;

public class SplashActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		if(XFSharedPreference.getInstance(this).isGestureProtected()){
			setContentView(R.layout.activity_empty);
			
			startActivity(new Intent(this, GestureActivity.class));
			finish();
		}else{
			setContentView(R.layout.activity_splash);
			
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run(){
					SplashActivity.this.startActivity(new Intent(SplashActivity.this, TabActivity.class));
					SplashActivity.this.finish();
				}
			}, 2000);
		}
		
	}

}
