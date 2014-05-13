package com.pz.xingfutao.ui.sub;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.api.LoginApi;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.api.KeyboardInvoke;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.utils.Renderer;
import com.pz.xingfutao.widget.XFToast;

public class SignUpFragment extends BaseBackButtonFragment implements KeyboardInvoke{
	
	private boolean isLoged;
	
	private EditText accountInput;
	private EditText pwdInput;
	private EditText pwdConfirmInput;
	private EditText emailInput;
	private EditText phoneInput;
	
	private Button signup;

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_sign_up);
		
		getTitleView().setText(getString(R.string.title_signup));
		
		accountInput = (EditText) content.findViewById(R.id.account_input);
		pwdInput = (EditText) content.findViewById(R.id.password_input);
		pwdConfirmInput = (EditText) content.findViewById(R.id.password_confirm_input);
		emailInput = (EditText) content.findViewById(R.id.email_input);
		phoneInput = (EditText) content.findViewById(R.id.phone_input);
		
		signup = (Button) content.findViewById(R.id.submit);
		signup.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(accountInput.length() == 0){
					XFToast.showTextShort(R.string.fragment_signup_quote_user_name_null);
					return;
				}
				if(pwdInput.length() == 0){
					XFToast.showTextShort(R.string.fragment_signup_quote_password_null);
					return;
				}
				if(pwdConfirmInput.length() == 0){
					XFToast.showTextShort(R.string.fragment_signup_quote_confirm_password_null);
					return;
				}
				
				if(emailInput.length() == 0){
					XFToast.showTextShort(R.string.fragment_signup_quote_email_null);
					return;
				}
				
				if(phoneInput.length() == 0){
					XFToast.showTextShort(R.string.fragment_signup_quote_phone_null);
					return;
				}
				
				if(!pwdInput.getText().toString().equals(pwdConfirmInput.getText().toString())){
					XFToast.showTextShort(R.string.fragment_signup_quote_password_not_match);
					return;
				}
				
				String userName = accountInput.getText().toString();
				String pwd = Renderer.md5(pwdInput.getText().toString());
				String email = emailInput.getText().toString();
				String phone = phoneInput.getText().toString();
				
				NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, LoginApi.getRegisterUrl(userName, pwd, email, phone), new Listener<String>(){
					@Override
					public void onResponse(String response){
						PLog.d("response", response);
						if(LoginApi.verifySignup(response)){
							XFToast.showTextShort(R.string.fragment_signup_quote_signup_success);
							dismiss();
						}
					}
				}, SignUpFragment.this);
			}
		});
		
		isLoged = false;
	}
	
	@Override
	public void dismiss(){
		if(!isLoged && getArguments() != null){
			if(getArguments().containsKey("fragment_class")) getArguments().remove("fragment_class");
		}
		super.dismiss();
	}
	
	@Override
	protected String getTitleUpperText(){
		return getString(R.string.signing);
	}
}
