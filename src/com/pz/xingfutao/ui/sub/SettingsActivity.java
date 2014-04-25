package com.pz.xingfutao.ui.sub;

import android.os.Bundle;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseTitleActivity;

public class SettingsActivity extends BaseTitleActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setMode(MODE_TITLE | MODE_LEFT_BUTTON);
		getLeftButton().setImageResource(R.drawable.selector_title_button_back);
	}

}
