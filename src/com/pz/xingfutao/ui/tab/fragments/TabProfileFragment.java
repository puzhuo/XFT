package com.pz.xingfutao.ui.tab.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.ui.base.BaseTitleFragment;
import com.pz.xingfutao.ui.sub.FeedbackFragment;
import com.pz.xingfutao.ui.sub.LoginFragment;
import com.pz.xingfutao.ui.sub.MyFavFragment;
import com.pz.xingfutao.ui.sub.MyOrderFragment;
import com.pz.xingfutao.ui.sub.PrivacyFragment;
import com.pz.xingfutao.ui.sub.SettingsFragment;
import com.pz.xingfutao.utils.LoginUtil;
import com.pz.xingfutao.view.AlertListener;
import com.pz.xingfutao.view.PopupWindowDispatcher;



public class TabProfileFragment extends BaseTitleFragment {
	
	private LinearLayout myOrder;
	private LinearLayout myFav;
	
	private TextView welcome;
	private TextView clickToLogin;
	
	private View serviceCallButton;
	private View feedbackButton;
	private View privacyButton;
	
	private OnClickListener onClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
			case R.id.my_order:
				startFragmentWithBackEnabled(new MyOrderFragment(), "");
				break;
			case R.id.my_favorite:
				startFragmentWithBackEnabled(new MyFavFragment(), "");
				break;
			case R.id.click_to_login:
				startFragmentWithBackEnabled(new LoginFragment(), "");
				break;
			case R.id.service_call:
				PopupWindowDispatcher.getInstance(getActivity()).popupInterceptAlert(content, new AlertListener(getString(R.string.fragment_tab_profile_service_call_confirm)){
					@Override
					public void onClick(View v){
						Intent intent = new Intent();
						intent.setAction("android.intent.action.CALL");
						intent.setData(Uri.parse("tel:" + getString(R.string.fragment_tab_profile_service_number)));
						startActivity(intent);
					}
				}, new AlertListener(getString(R.string.fragment_tab_profile_common_cancel)){
					@Override
					public void onClick(View v){
						PopupWindowDispatcher.getInstance(getActivity()).dismiss();
					}
				}, getString(R.string.fragment_tab_profile_service_call_quote), getString(R.string.fragment_tab_profile_service_desc));
				break;
			case R.id.feedback:
				startFragmentWithBackEnabled(new FeedbackFragment(), "");
				break;
			case R.id.privacy_protection:
				startFragmentWithBackEnabled(new PrivacyFragment(), "");
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
		welcome = (TextView) content.findViewById(R.id.welcome);
		clickToLogin = (TextView) content.findViewById(R.id.click_to_login);
		
		serviceCallButton = content.findViewById(R.id.service_call);
		feedbackButton = content.findViewById(R.id.feedback);
		privacyButton = content.findViewById(R.id.privacy_protection);
		
		myOrder.setClickable(true);
		myFav.setClickable(true);
		welcome.setClickable(true);
		clickToLogin.setClickable(true);
		serviceCallButton.setClickable(true);
		feedbackButton.setClickable(true);
		privacyButton.setClickable(true);
		
		myOrder.setOnClickListener(onClickListener);
		myFav.setOnClickListener(onClickListener);
		welcome.setOnClickListener(onClickListener);
		clickToLogin.setOnClickListener(onClickListener);
		serviceCallButton.setOnClickListener(onClickListener);
		feedbackButton.setOnClickListener(onClickListener);
		privacyButton.setOnClickListener(onClickListener);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		if(LoginUtil.checkLogin()){
			String userName = XFSharedPreference.getInstance(getActivity()).getUserName();
			if(userName != null){
				welcome.setText(userName + getString(R.string.fragment_tab_profile_quote_welcome));
			}
			clickToLogin.setVisibility(View.GONE);
		}else{
			welcome.setText(getString(R.string.fragment_tab_profile_hi) + getString(R.string.fragment_tab_profile_quote_welcome));
			clickToLogin.setVisibility(View.VISIBLE);
		}
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
