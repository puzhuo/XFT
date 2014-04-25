package com.pz.xingfutao.ui.tab.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.TabStoreAdapter;
import com.pz.xingfutao.entities.BaseEntity;
import com.pz.xingfutao.entities.ImageBrickEntity;
import com.pz.xingfutao.entities.ImageFlowEntity;
import com.pz.xingfutao.ui.sub.SearchActivity;
import com.pz.xingfutao.utils.PLog;


public class TabStoreFragment extends RefreshableListViewFragment{
	
	private TabStoreAdapter adapter;
	
	private List<BaseEntity> datas;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		datas = new ArrayList<BaseEntity>();
		datas.add(new ImageFlowEntity());
		datas.add(new ImageBrickEntity());
		datas.add(new ImageBrickEntity());
		datas.add(new ImageBrickEntity());
		datas.add(new ImageBrickEntity());
		datas.add(new ImageBrickEntity());
		datas.add(new ImageBrickEntity());
		
		adapter = new TabStoreAdapter(getActivity(), datas);
		
		list.setAdapter(adapter);
		
		setMode(MODE_RIGHT_BUTTON | MODE_TITLE);
		getRightButton().setImageResource(R.drawable.selector_title_button_search);
	}

	@Override
	protected void onRefresh() {
		PLog.d("pull", "refresh");
		getTitleView().setUpperText("正在刷新");
		
		new Handler().postDelayed(new Runnable(){
			@Override
			public void run(){
				onRefreshComplete();
				getTitleView().backward();
			}
		}, 3000L);
	}
	
	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_RIGHT_BUTTON:
			getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
			break;
		}
	}
}
