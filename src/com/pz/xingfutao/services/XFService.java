package com.pz.xingfutao.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class XFService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		
		
		return super.onStartCommand(intent, flags, startId);
	}

}
