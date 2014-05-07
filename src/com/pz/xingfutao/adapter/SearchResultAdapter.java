package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.utils.Renderer;

public class SearchResultAdapter extends BaseAdapter {
	
	private Context context;
	private List<ItemDetailEntity> datas;
	
	public SearchResultAdapter(Context context, List<ItemDetailEntity> datas){
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public ItemDetailEntity getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview_fragment_search_result, null, false);
		}
		
		NetworkHandler.getInstance(context).imageRequest(Renderer.getBaseUrl() + getItem(position).getThumb(), (ImageView) convertView.findViewById(R.id.thumb));
		((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getName());
		((TextView) convertView.findViewById(R.id.price)).setText("¥" + getItem(position).getShopPrice());
		//((TextView) convertView.findViewById(R.id.sold)).setText("月销量 " + getItem(position).)
		
		
		return convertView;
	}

}
