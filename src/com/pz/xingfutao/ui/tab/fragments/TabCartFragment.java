package com.pz.xingfutao.ui.tab.fragments;

import android.os.Bundle;

public class TabCartFragment extends RefreshableListViewFragment{
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_TITLE);
	}

	@Override
	protected void onRefresh() {
		// TODO Auto-generated method stub
		
	}

}
