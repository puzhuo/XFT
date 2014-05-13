package com.pz.xingfutao.ui.sub;

import android.os.Bundle;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.api.RequireLogin;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;

public class MyOrderFragment extends BaseBackButtonFragment implements RequireLogin {

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_my_order);
		getTitleView().setText(getString(R.string.title_my_order));
	}
}
