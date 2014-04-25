package com.pz.xingfutao.ui.tab.fragments;

import android.os.Bundle;
import android.widget.ListView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.widget.PullRefreshWidget;
import com.pz.xingfutao.widget.PullRefreshWidget.OnRefreshListener;

public abstract class RefreshableListViewFragment extends BaseTitleFragment{
	
	protected ListView list;
	protected PullRefreshWidget pullRefreshWidget;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_base_listview);
		
		list = (ListView) content.findViewById(R.id.list);
		pullRefreshWidget = (PullRefreshWidget) content.findViewById(R.id.pull_refresh);
		pullRefreshWidget.setOnRefreshListener(new OnRefreshListener(){
			@Override
			public void onRefresh(){
				RefreshableListViewFragment.this.onRefresh();
			}
		});
		
	}
	
	protected abstract void onRefresh();
	
	protected void onRefreshComplete(){
		pullRefreshWidget.onRefreshComplete();
	}
}
