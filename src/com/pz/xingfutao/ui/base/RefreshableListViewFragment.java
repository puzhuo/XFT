package com.pz.xingfutao.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.widget.PullRefreshWidget;
import com.pz.xingfutao.widget.PullRefreshWidget.OnRefreshListener;

public abstract class RefreshableListViewFragment extends BaseTitleFragment{
	
	protected ListView list;
	protected PullRefreshWidget pullRefreshWidget;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		final View v = super.onCreateView(inflater, container, savedInstanceState);
		
        setContentView(R.layout.fragment_base_listview);
		
		list = (ListView) content.findViewById(R.id.list);
		pullRefreshWidget = (PullRefreshWidget) content.findViewById(R.id.pull_refresh);
		pullRefreshWidget.setPullable(pullable());
		pullRefreshWidget.setOnRefreshListener(new OnRefreshListener(){
			@Override
			public void onRefresh(){
				RefreshableListViewFragment.this.onRefresh();
			}
		});
		
		return v;
	}
	
	/*
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
	 */
	
	@Override
	protected String getTitleUpperText(){
		return getString(R.string.refreshing);
	}
	
	protected abstract void onRefresh();
	
	protected boolean pullable(){
		return true;
	}
	
	protected void onRefreshComplete(){
		pullRefreshWidget.onRefreshComplete();
	}
	
	@Override
	protected boolean isContentEmpty(){
		return list == null || list.getAdapter() == null || list.getAdapter().getCount() == 0;
	}
	
}
