package com.pz.xingfutao.ui.sub;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.pz.xingfutao.R;
import com.pz.xingfutao.XFApplication;
import com.pz.xingfutao.api.LoginApi;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.api.KeyboardInvoke;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.utils.Renderer;
import com.pz.xingfutao.widget.XFToast;

public class LoginFragment extends BaseBackButtonFragment implements KeyboardInvoke{
	
	private EditText userName;
	private EditText password;
	
	private Button loginButton;
	private Button signupButton;
	
	private boolean isLoged;
	
	private OnClickListener onClickListener = new OnClickListener(){
		@Override
		public void onClick(View v){
			switch(v.getId()){
			case R.id.log_in_button:
				login();
				break;
			case R.id.sign_up_button:
				SignUpFragment fragment = new SignUpFragment();
				Bundle bundle = getArguments();
				if(bundle != null) fragment.setArguments(bundle);
				
				dismiss();
				
				startFragmentWithBackEnabled(fragment, null);
				break;
			}
		}
	};
	
	private void login(){
		final String userNameInput = userName.getText().toString();
		final String passwordInput = Renderer.md5(password.getText().toString());
		if(userNameInput.length() == 0){
			XFToast.showTextShort(R.string.fragment_login_quote_user_name_null);
			return;
		}
		if(password.getText().length() == 0){
			XFToast.showTextShort(R.string.fragment_login_quote_password_null);
			return;
		}
		NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, LoginApi.getLoginUrl(userNameInput, passwordInput), new Listener<String>(){
			@Override
			public void onResponse(String response){
				PLog.d("response", response);
				if(LoginApi.verify(response)){
					isLoged = true;
					XFSharedPreference.getInstance(XFApplication.getInstance()).putUserName(userNameInput);
					XFSharedPreference.getInstance(XFApplication.getInstance()).putRenderedPwd(passwordInput);
					XFToast.showTextShort(R.string.fragment_login_quote_login_success);
					dismiss();
				}else{
					XFToast.showTextShort(R.string.fragment_login_quote_login_failure);
				}
			}
		}, LoginFragment.this);
	}
	
	@Override
	public void onExtraErrorHandle(VolleyError error){
		Toast.makeText(getActivity(), getString(R.string.fragment_common_error), Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void dismiss(){
		if(!isLoged && getArguments() != null){
			if(getArguments().containsKey("fragment_class")) getArguments().remove("fragment_class");
		}
		super.dismiss();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_login);
		
		getTitleView().setText(getString(R.string.title_login));
		
		loginButton = (Button) content.findViewById(R.id.log_in_button);
		signupButton = (Button) content.findViewById(R.id.sign_up_button);
		
		userName = (EditText) content.findViewById(R.id.account_input);
		password = (EditText) content.findViewById(R.id.password_input);
		password.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				
				if(actionId == EditorInfo.IME_ACTION_SEND){
					login();
					return true;
				}
				
				return false;
			}
			
		});
		
		loginButton.setOnClickListener(onClickListener);
		signupButton.setOnClickListener(onClickListener);
		
		isLoged = false;
	}
	
	@Override
	protected String getTitleUpperText(){
		return getString(R.string.loging);
	}
}
