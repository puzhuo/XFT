package com.pz.xingfutao.ui.tab.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.TabStoreAdapter;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.entities.base.BaseTabStoreEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.RefreshableListViewFragment;
import com.pz.xingfutao.ui.sub.SearchFragment;
import com.pz.xingfutao.utils.PLog;


public class TabStoreFragment extends RefreshableListViewFragment{
	
	private TabStoreAdapter adapter;
	
	private List<BaseTabStoreEntity> datas;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		datas = new ArrayList<BaseTabStoreEntity>();
		
		adapter = new TabStoreAdapter(getActivity(), datas);
		
		list.setAdapter(adapter);
		NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ContentApi.getStoreContentUrl(), new Listener<String>(){

			@Override
			public void onResponse(String response) {
				
				PLog.d("json", response);
				
                List<BaseTabStoreEntity> jsonResultList = ContentApi.parseStoreContent(response);
				if(jsonResultList != null){
					
					datas.clear();
					datas.addAll(jsonResultList);
					
					adapter.notifyDataSetChanged();
				}
			}
			
		}, this);
		
		setMode(MODE_RIGHT_BUTTON | MODE_TITLE);
		getTitleView().setText(getString(R.string.app_name));
		getRightButton().setImageResource(R.drawable.selector_title_button_search);
	}

	@Override
	protected void onRefresh() {
		NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, ContentApi.getStoreContentUrl(), new Listener<String>(){

			@Override
			public void onResponse(String response) {
				
				List<BaseTabStoreEntity> jsonResultList = ContentApi.parseStoreContent(response);
				if(jsonResultList != null){
					datas.clear();
				    datas.addAll(jsonResultList);
					
					adapter.notifyDataSetChanged();
				}
				
				
				onRefreshComplete();
				list.smoothScrollToPosition(0);
			}
			
		}, this);
		
		
	}
	
	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_RIGHT_BUTTON:
			startFragmentWithBackEnabled(new SearchFragment(), null);
			break;
		}
	}
}
