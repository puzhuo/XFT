package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.ImageMap;

public class TabCategoryAdapter extends BaseAdapter{
	
	private Context context;
	private List<ImageMap> datas;
	
	public TabCategoryAdapter(Context context, List<ImageMap> datas){
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public ImageMap getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_category_list, null, false);
		}
		
		//NetworkHandler.getInstance(context).imageRequest(getItem(position).getImageLink()
		((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getTitle());
		
		return convertView;
	}

}
