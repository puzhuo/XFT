package com.pz.xingfutao.ui.sub;

import android.os.Bundle;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;

public class AboutFragment extends BaseBackButtonFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_about);
		getTitleView().setText(getString(R.string.title_about));
	}
}
