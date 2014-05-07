package com.pz.xingfutao.ui.sub;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;

public class LoginFragment extends BaseBackButtonFragment{
	
	private Button loginButton;
	private Button signupButton;
	
	private OnClickListener onClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
			case R.id.log_in_button:
				break;
			case R.id.sign_up_button:
				startFragmentWithBackEnabled(new SignUpFragment(), null);
				break;
			}
		}
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_login);
		
		loginButton = (Button) content.findViewById(R.id.log_in_button);
		signupButton = (Button) content.findViewById(R.id.sign_up_button);
		
		loginButton.setOnClickListener(onClickListener);
		signupButton.setOnClickListener(onClickListener);
	}
}
