package com.pz.xingfutao.ui.tab.fragments;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseTitleFragment;
import com.pz.xingfutao.ui.sub.SettingsFragment;



public class TabProfileFragment extends BaseTitleFragment {
	
	private LinearLayout myOrder;
	private LinearLayout myFav;
	
	private OnClickListener onClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
			case R.id.my_order:
				break;
			case R.id.my_favorite:
				break;
			}
		}
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentViewWithMode(R.layout.fragment_tab_profile, MODE_TITLE | MODE_RIGHT_BUTTON);
		getRightButton().setImageResource(R.drawable.selector_title_button_settings);
		
		myOrder = (LinearLayout) content.findViewById(R.id.my_order);
		myFav = (LinearLayout) content.findViewById(R.id.my_favorite);
		
		myOrder.setClickable(true);
		myFav.setClickable(true);
		
		myOrder.setOnClickListener(onClickListener);
		myFav.setOnClickListener(onClickListener);
	}
	
	@Override
	public void onClick(int id){
		switch(id){
		case MODE_RIGHT_BUTTON:
			startFragmentWithBackEnabled(new SettingsFragment(), null);
			break;
		}
	}
}
