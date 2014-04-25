package com.pz.xingfutao.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.tab.TabActivity;

public class SplashActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				SplashActivity.this.startActivity(new Intent(SplashActivity.this, TabActivity.class));
				SplashActivity.this.finish();
			}
		}, 2000);
		
	}

}
