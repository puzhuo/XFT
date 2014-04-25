package com.pz.xingfutao.ui.tab.fragments;

import android.content.Intent;
import android.os.Bundle;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.sub.SettingsActivity;



public class TabProfileFragment extends BaseTitleFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_TITLE | MODE_RIGHT_BUTTON);
		getRightButton().setImageResource(R.drawable.selector_title_button_settings);
	}
	
	@Override
	public void onClick(int id){
		switch(id){
		case MODE_RIGHT_BUTTON:
			Intent intent = new Intent(getActivity(), SettingsActivity.class);
			getActivity().startActivity(intent);
			break;
		}
	}
}
