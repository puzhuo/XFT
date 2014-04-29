package com.pz.xingfutao.ui.sub;

import org.json.JSONObject;

import android.os.Bundle;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.BaseBackButtonActivity;
import com.pz.xingfutao.utils.PLog;

public class ItemDetailActivity extends BaseBackButtonActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setMode(MODE_TITLE | MODE_LEFT_BUTTON | MODE_RIGHT_BUTTON);
		getTitleView().setText(getString(R.string.activity_title_good_detail));
		
		if(getIntent().hasExtra("good_id")){
			NetworkHandler.getInstance(this).jsonRequest(Method.POST, ContentApi.getGoodDetailUrl(getIntent().getStringExtra("good_id")), new Listener<JSONObject>(){

				@Override
				public void onResponse(JSONObject jsonObject) {
					PLog.d("json_detail", jsonObject.toString());
				}
				
			}, null);
		}
	}
	
	@Override
	protected void onClick(int id){
		super.onClick(id);
	}
}
