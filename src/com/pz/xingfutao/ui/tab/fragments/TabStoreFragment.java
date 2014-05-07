package com.pz.xingfutao.ui.tab.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

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


public class TabStoreFragment extends RefreshableListViewFragment{
	
	private TabStoreAdapter adapter;
	
	private List<BaseTabStoreEntity> datas;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		datas = new ArrayList<BaseTabStoreEntity>();
		
		adapter = new TabStoreAdapter(getActivity(), datas);
		
		list.setAdapter(adapter);
		
		NetworkHandler.getInstance(getActivity()).jsonRequest(Method.POST, ContentApi.getStoreContentUrl(), new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject jsonObject) {
				
                List<BaseTabStoreEntity> jsonResultList = ContentApi.parseStoreContent(jsonObject);
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
		getTitleView().setUpperText(getString(R.string.refreshing));
		NetworkHandler.getInstance(getActivity()).jsonRequest(Method.POST, ContentApi.getStoreContentUrl(), new Listener<JSONObject>(){

			@Override
			public void onResponse(JSONObject jsonObject) {
				
				List<BaseTabStoreEntity> jsonResultList = ContentApi.parseStoreContent(jsonObject);
				if(jsonResultList != null){
					datas.clear();
				    datas.addAll(jsonResultList);
					
					adapter.notifyDataSetChanged();
				}
				
				
			}
			
		}, this);
		
		onRefreshComplete();
		getTitleView().backward();
		list.smoothScrollToPosition(0);
	}
	
	@Override
	protected boolean isContentEmpty(){
		//return datas.size() <= 0;
		return super.isContentEmpty();
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
