package com.pz.xingfutao.view;

import android.view.View;
import android.view.View.OnClickListener;

public class AlertListener implements OnClickListener{
		private String title;
		
		public AlertListener(String title){
			this.title = title;
		}

	    public String getTitle(){
	    	return title;
	    }

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	}