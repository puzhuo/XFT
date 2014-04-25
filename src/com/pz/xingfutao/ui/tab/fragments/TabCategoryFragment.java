package com.pz.xingfutao.ui.tab.fragments;

import android.os.Bundle;


public class TabCategoryFragment extends RefreshableListViewFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_SEARCH_BAR);
	}
	@Override
	protected void onRefresh() {
		// TODO Auto-generated method stub
		
	}
}
