package com.pz.xingfutao.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.dao.XFDatabase;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.utils.FragmentUtil;
import com.pz.xingfutao.utils.LoginUtil;

public class TabCartAdapter extends BaseAdapter{

	private Context context;
	private List<ItemDetailEntity> datas;
	private Map<String, Integer>[] extraData;
	private CheckBox totalSettleCheck;
	
	public TabCartAdapter(Context context, List<ItemDetailEntity> datas, Map<String, Integer>[] extraData, CheckBox totalSettleCheck){
		this.context = context;
		this.datas = datas;
		this.extraData = extraData;
		this.totalSettleCheck = totalSettleCheck;
	}
	
	public void setExtraData(Map<String, Integer>[] extraData){
		this.extraData = extraData;
		
		if(totalSettleCheck != null && totalSettleCheck.isChecked()){
			boolean eachCheck = false;
			for(Map<String, Integer> map : extraData){
				if(map != null && map.containsKey("is_check") && map.get("is_check") == 1){
					eachCheck = true;
				}
			}
			
			if(!eachCheck) {
				totalSettleCheck.setChecked(false);
			}else{
				totalSettleCheck.setChecked(false);
				totalSettleCheck.setChecked(true);
			}
		}
	}
	
	public Map<String, Integer>[] getExtraData(){
		return extraData;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_cart_list, null, false);
		}
		
		if(extraData.length > position && extraData[position] == null) extraData[position] = new HashMap<String, Integer>();
		
		if(extraData[0].containsKey("edit_mode") && extraData[0].get("edit_mode") == 1){
			convertView.findViewById(R.id.count_alter).setVisibility(View.VISIBLE);
			
			final TextView alterCount = (TextView) convertView.findViewById(R.id.c);
			final TextView count = ((TextView) convertView.findViewById(R.id.count));
			
			alterCount.setText(getItem(position).getPurchaseCount() + "");
			((Button) convertView.findViewById(R.id.decrease)).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					if(getItem(position).getPurchaseCount() > 0){
						getItem(position).setPurchaseCount(getItem(position).getPurchaseCount() - 1);
						alterCount.setText(getItem(position).getPurchaseCount() + "");
						count.setText(context.getString(R.string.fragment_tab_cart_count) + getItem(position).getPurchaseCount());
					}
					
					if(LoginUtil.checkLogin()){
						
					}else{
						XFDatabase.getInstance(context).updateCartCountByGoodId(getItem(position).getId(), getItem(position).getPurchaseCount());
					}
					
					if(totalSettleCheck != null && totalSettleCheck.isChecked()){
						totalSettleCheck.setChecked(false);
						totalSettleCheck.setChecked(true);
					}
				}
			});
			((Button) convertView.findViewById(R.id.increase)).setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					getItem(position).setPurchaseCount(getItem(position).getPurchaseCount() + 1);
					alterCount.setText(getItem(position).getPurchaseCount() + "");
					count.setText(context.getString(R.string.fragment_tab_cart_count) + getItem(position).getPurchaseCount());
					
                    if(LoginUtil.checkLogin()){
                    	
					}else{
						XFDatabase.getInstance(context).updateCartCountByGoodId(getItem(position).getId(), getItem(position).getPurchaseCount());
					}
					
					if(totalSettleCheck != null && totalSettleCheck.isChecked()){
						totalSettleCheck.setChecked(false);
						totalSettleCheck.setChecked(true);
					}
				}
			});
		}else{
			convertView.findViewById(R.id.count_alter).setVisibility(View.GONE);
		}
		
		ImageView thumb = (ImageView) convertView.findViewById(R.id.thumb);
		thumb.setClickable(true);
		thumb.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				FragmentUtil.startImageMappingFragment(context, getItem(position).getItemMap());
			}
		});
		
		CheckBox confirm = (CheckBox) convertView.findViewById(R.id.confirm);
		if(extraData.length > position && extraData[position].containsKey("is_check")){
			confirm.setChecked(extraData[position].get("is_check") == 1 ? true : false);
			if(extraData[position].get("is_check") == 1 && totalSettleCheck != null) totalSettleCheck.setChecked(true);
		}
		confirm.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					extraData[position].put("is_check", 1);
					totalSettleCheck.setChecked(false);
					totalSettleCheck.setChecked(true);
				}else{
					extraData[position].put("is_check", 0);
					if(totalSettleCheck.isChecked()){
						boolean eachCheck = false;
						for(Map<String, Integer> map : extraData){
							if(map.containsKey("is_check") && map.get("is_check") == 1){
								eachCheck = true;
							}
						}
						
						if(!eachCheck) {
							totalSettleCheck.setChecked(false);
						}else{
							totalSettleCheck.setChecked(false);
							totalSettleCheck.setChecked(true);
						}
					}
				}
			}
		});
		
		NetworkHandler.getInstance(context).imageRequest(getItem(position).getThumb(), thumb);
		((TextView) convertView.findViewById(R.id.title)).setText(getItem(position).getName());
		((TextView) convertView.findViewById(R.id.desc)).setText(context.getString(R.string.fragment_tab_cart_unit_price) + "¥" + getItem(position).getShopPrice());
		((TextView) convertView.findViewById(R.id.count)).setText(context.getString(R.string.fragment_tab_cart_count) + getItem(position).getPurchaseCount());
		
		return convertView;
	}

}
