package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.SettingsMap;

public class SettingsAdapter extends BaseAdapter{

	private Context context;
	private List<SettingsMap> datas;
	
	public SettingsAdapter(Context context, List<SettingsMap> datas){
		this.context = context;
		this.datas = datas;
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public SettingsMap getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public int getViewTypeCount(){
		return 5;
	}
	
	@Override
	public int getItemViewType(int position){
	    return getItem(position).getType();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		
		switch(getItemViewType(position)){
		case SettingsMap.MODE_RADIO_BUTTON:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_settings_radio, null, false);
			}
			
			if(getItem(position).getTitle() != null) ((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getTitle());
			break;
		case SettingsMap.MODE_ARROW_WITH_EXTRA:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_settings_arrow_with_extra, null, false);
			}
			
			if(getItem(position).getTitle() != null) ((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getTitle());
			
			TextView extra = (TextView) convertView.findViewById(R.id.extra);
			if(getItem(position).getExtra() != null){
				extra.setVisibility(View.VISIBLE);
				extra.setText(getItem(position).getExtra());
			}else{
				extra.setVisibility(View.GONE);
			}
			
			break;
		case SettingsMap.MODE_DESC:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_settings_desc, null, false);
			}
			
			if(getItem(position).getTitle() != null) ((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getTitle());
			
			break;
		case SettingsMap.MODE_DIV:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_settings_div, null, false);
			}
			break;
		case SettingsMap.MODE_EXIT:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_settings_exit, null, false);
			}
			
			if(getItem(position).getTitle() != null) ((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getTitle());
			
			break;
		}
		return convertView;
	}

}
