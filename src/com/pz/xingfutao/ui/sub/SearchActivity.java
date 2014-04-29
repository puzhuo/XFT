package com.pz.xingfutao.ui.sub;

import android.os.Bundle;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseTitleActivity;

public class SearchActivity extends BaseTitleActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentViewWithMode(R.layout.activity_search, MODE_SEARCH_BAR);
	}
}
