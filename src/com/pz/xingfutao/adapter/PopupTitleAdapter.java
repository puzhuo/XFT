package com.pz.xingfutao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pz.xingfutao.R;

public class PopupTitleAdapter extends BaseAdapter {
	
	private Context context;
	private int[] imageRes;
	private String[] titles;
	
	public PopupTitleAdapter(Context context, int[] imageRes, String[] titles){
		if(imageRes.length != titles.length) throw new IllegalArgumentException(); 
		this.context = context;
		this.imageRes = imageRes;
		this.titles = titles;
	}

	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_popup_title, null, false);
		}
		
		((ImageView) convertView.findViewById(R.id.icon)).setImageResource(imageRes[position]);
		((TextView) convertView.findViewById(R.id.title)).setText(titles[position]);
		
		return convertView;
	}

}
