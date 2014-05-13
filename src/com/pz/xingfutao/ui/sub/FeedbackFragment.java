package com.pz.xingfutao.ui.sub;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.widget.XFToast;

public class FeedbackFragment extends BaseBackButtonFragment{

	EditText input;
	Button send;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_feedback);
		getTitleView().setText(getString(R.string.title_feedback));
		
		input = (EditText) content.findViewById(R.id.input);
		send = (Button) content.findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ContentApi.getFeedbackUrl(input.getText().toString()), new Listener<String>(){
					@Override
					public void onResponse(String response){
						if(ContentApi.checkFeedback(response)){
							XFToast.showTextShort("反馈成功");
						}
					}
				}, FeedbackFragment.this);
			}
		});
	}
}
