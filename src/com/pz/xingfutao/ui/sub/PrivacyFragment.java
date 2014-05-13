package com.pz.xingfutao.ui.sub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.pz.xingfutao.R;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.ui.GestureActivity;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;

public class PrivacyFragment extends BaseBackButtonFragment{

	private CheckBox check;
	private View gestureProtect;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_privacy);
		getTitleView().setText(getString(R.string.title_privacy));
		
		check = (CheckBox) content.findViewById(R.id.check);
		gestureProtect = content.findViewById(R.id.gesture_layout);
		gestureProtect.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				check.setChecked(!check.isChecked());
				if(check.isChecked()){
					Intent intent = new Intent(getActivity(), GestureActivity.class);
					intent.putExtra("edit_mode", true);
					startActivity(intent);
				}
			}
		});
		
		check.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(check.isChecked()){
					Intent intent = new Intent(getActivity(), GestureActivity.class);
					intent.putExtra("edit_mode", true);
					startActivity(intent);
				}else{
					XFSharedPreference.getInstance(getActivity()).setIsGestureProtect(false);
				}
			}
		});
		
		check.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(!isChecked){
					XFSharedPreference.getInstance(getActivity()).setIsGestureProtect(false);
				}
			}
			
		});
		
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(XFSharedPreference.getInstance(getActivity()).isGestureProtected()){
			check.setChecked(true);
		}else{
			check.setChecked(false);
		}
	}
}
