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

public class ItemListAdapter extends BaseAdapter {
	
	private Context context;
	private List<ItemDetailEntity> datas;
	
	public ItemListAdapter(Context context, List<ItemDetailEntity> datas){
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
		
		ImageView thumb = (ImageView) convertView.findViewById(R.id.thumb);
		TextView title = (TextView) convertView.findViewById(R.id.title);
		TextView price = (TextView) convertView.findViewById(R.id.price);
		TextView sold = (TextView) convertView.findViewById(R.id.sold);
		
		if(getItem(position).getName() != null){
			convertView.setClickable(false);
			
			thumb.setVisibility(View.VISIBLE);
			title.setVisibility(View.VISIBLE);
			price.setVisibility(View.VISIBLE);
			
			
			NetworkHandler.getInstance(context).imageRequest(getItem(position).getThumb(), thumb);
			title.setText(getItem(position).getName());
			price.setText("¥" + getItem(position).getShopPrice());
			if(getItem(position).getSoldCount() != -1){ 
				sold.setVisibility(View.VISIBLE);
				sold.setText("月销量 " + getItem(position).getSoldCount());
			}else{
				sold.setVisibility(View.GONE);
			}
		}else{
			convertView.setClickable(true);
			thumb.setVisibility(View.INVISIBLE);
			title.setVisibility(View.INVISIBLE);
			price.setVisibility(View.INVISIBLE);
			sold.setVisibility(View.INVISIBLE);
		}
		
		
		return convertView;
	}

}
