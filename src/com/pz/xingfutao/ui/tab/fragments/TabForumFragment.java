package com.pz.xingfutao.ui.tab.fragments;

import com.pz.xingfutao.R;

import android.os.Bundle;


public class TabForumFragment extends RefreshableListViewFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_TITLE | MODE_LEFT_BUTTON);
		getLeftButton().setImageResource(R.drawable.selector_title_button_menu);
	}

	@Override
	protected void onRefresh() {
		// TODO Auto-generated method stub
		
	}

}
