package com.pz.xingfutao.ui.tab.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.adapter.TabCategoryAdapter;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.RefreshableListViewFragment;
import com.pz.xingfutao.ui.sub.SearchFragment;
import com.pz.xingfutao.utils.FragmentUtil;


public class TabCategoryFragment extends RefreshableListViewFragment {
	
	private TabCategoryAdapter adapter;
	private List<ImageMap> datas;

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setMode(MODE_SEARCH_BAR | MODE_TITLE);
		getTitleView().setText("");
		
		datas = new ArrayList<ImageMap>();
		adapter = new TabCategoryAdapter(getActivity(), datas);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View target, int position, long id){
				FragmentUtil.startImageMappingFragment(getActivity(), adapter.getItem(position));
			}
		});
		
		NetworkHandler.getInstance(getActivity()).jsonRequest(Method.POST, ContentApi.getMainCategoryUrl(), new Listener<JSONObject>(){
			@Override
			public void onResponse(JSONObject jsonObject){
				datas.clear();
				datas.addAll(ContentApi.parseMainCategoryUrl(jsonObject));
				adapter.notifyDataSetChanged();
			}
		}, this);
	}
	
	@Override
	protected String getTitleUpperText(){
		return null;
	}
	
	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_TITLE:
			startFragmentWithBackEnabled(new SearchFragment(), "");
			break;
		}
	}
	
	@Override
	protected void onRefresh() {
		
	}
}
