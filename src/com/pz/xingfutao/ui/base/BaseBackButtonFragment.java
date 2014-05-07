package com.pz.xingfutao.ui.base;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.pz.xingfutao.R;

public class BaseBackButtonFragment extends BaseTitleFragment{

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_TITLE | MODE_LEFT_BUTTON);
		getLeftButton().setImageResource(R.drawable.selector_title_button_back);
		getLeftButton().setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				dismiss();
			}
		});
	}
}
