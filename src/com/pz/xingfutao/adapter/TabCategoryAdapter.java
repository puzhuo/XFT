package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pz.xingfutao.entities.TabCategoryEntity;

public class TabCategoryAdapter extends BaseAdapter{
	
	private Context context;
	private List<TabCategoryEntity> datas;
	
	public TabCategoryAdapter(Context context, List<TabCategoryEntity> datas){
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
