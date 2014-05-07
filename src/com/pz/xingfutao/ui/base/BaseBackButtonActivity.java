package com.pz.xingfutao.ui.base;

import android.os.Bundle;

import com.pz.xingfutao.R;

@Deprecated
public class BaseBackButtonActivity extends BaseTitleActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setMode(MODE_TITLE | MODE_LEFT_BUTTON);
		getLeftButton().setImageResource(R.drawable.selector_title_button_back);
	}
	
	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_LEFT_BUTTON:
			finish();
			break;
		}
	}

}
