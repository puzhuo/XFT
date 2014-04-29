package com.pz.xingfutao.ui.sub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseBackButtonActivity;

public class LoginActivity extends BaseBackButtonActivity{
	
	private EditText accountInput;
	private EditText passwordInput;
	
	private Button loginButton;
	private Button signupButton;
	
	private OnClickListener onClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
			case R.id.log_in_button:
				break;
			case R.id.sign_up_button:
				startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
				LoginActivity.this.finish();
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		
		accountInput = (EditText) findViewById(R.id.account_input);
		passwordInput = (EditText) findViewById(R.id.password_input);
		loginButton = (Button) findViewById(R.id.log_in_button);
		signupButton = (Button) findViewById(R.id.sign_up_button);
		
		loginButton.setOnClickListener(onClickListener);
		signupButton.setOnClickListener(onClickListener);
	}

}
