package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.utils.PLog;

public class SearchHotWordAdapter extends BaseAdapter{
	
	private Context context;
	private List<String> hotWords;
	
	public SearchHotWordAdapter(Context context, List<String> hotWords){
		this.context = context;
		this.hotWords = hotWords;
	}

	@Override
	public int getCount() {
		return hotWords.size();
	}

	@Override
	public String getItem(int position) {
		PLog.d("item", hotWords.get(position));
		return hotWords.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_hot_fragment_search, null, false);
		}
	    
		((TextView) convertView.findViewById(R.id.index)).setText(position + 1 + "");
		int alpha = 0;
		if(position < 3){
			alpha = 255 - 100 * position;
		}else{
			alpha = 30;
		}
		((TextView) convertView.findViewById(R.id.index)).getBackground().setAlpha(alpha);
		((TextView) convertView.findViewById(R.id.hot_word)).setText(getItem(position));
		
		return convertView;
	}

}
