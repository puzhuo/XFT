package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.widget.FitWidthImageView;

public class MyFavAdapter extends BaseAdapter {
	
	private Context context;
	private List<ItemDetailEntity> datas;
	
	public MyFavAdapter(Context context, List<ItemDetailEntity> datas){
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public ItemDetailEntity getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = new FitWidthImageView(context);
		}
		
		NetworkHandler.getInstance(context).imageRequest(getItem(position).getItemMap().getImageLink(), (FitWidthImageView) convertView);
		
		return convertView;
	}

}
