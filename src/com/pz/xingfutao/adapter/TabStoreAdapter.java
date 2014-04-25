package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.BaseEntity;
import com.pz.xingfutao.entities.ImageBrickEntity;
import com.pz.xingfutao.entities.ImageFlowEntity;

public class TabStoreAdapter extends BaseAdapter{
	
	private static final int TYPE_IMAGE_FLOW = 0;
	private static final int TYPE_IMAGE_BRICK = 1;
	
	private Context context;
	private List<BaseEntity> datas;
	
	public TabStoreAdapter(Context context, List<BaseEntity> datas){
		this.context = context;
		this.datas = datas;
	}
	
	@Override
	public int getCount(){
		return datas.size();
	}
	
	@Override
	public BaseEntity getItem(int position){
		return datas.get(position);
	}
	
	@Override
	public int getViewTypeCount(){
		return 2;
	}
	
	@Override
	public int getItemViewType(int position){
		if(datas.get(position) instanceof ImageFlowEntity){
			return TYPE_IMAGE_FLOW;
		}
		if(datas.get(position) instanceof ImageBrickEntity){
			return TYPE_IMAGE_BRICK;
		}
		
		return TYPE_IMAGE_FLOW;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		if(convertView == null){
			switch(getItemViewType(position)){
			case TYPE_IMAGE_FLOW:
				convertView = new ViewPager(context);
				
				break;
			case TYPE_IMAGE_BRICK:
				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_store_brick, null, false);
				break;
			}
		}
		
		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
