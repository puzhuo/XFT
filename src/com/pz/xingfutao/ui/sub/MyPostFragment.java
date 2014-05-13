package com.pz.xingfutao.ui.sub;

import android.os.Bundle;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.api.ForumApi;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.api.RequireLogin;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.PLog;

public class MyPostFragment extends BaseBackButtonFragment implements RequireLogin{

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_my_post);
		getTitleView().setText(getString(R.string.title_my_post));
		
		NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ForumApi.getForumMyPostUrl(XFSharedPreference.getInstance(getActivity()).getUserId()), new Listener<String>(){
			@Override
			public void onResponse(String response){
				PLog.d("response", response);
			}
		}, this);
	}
}
