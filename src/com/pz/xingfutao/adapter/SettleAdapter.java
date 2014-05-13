package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.sub.AddressEditFragment;
import com.pz.xingfutao.ui.tab.TabActivity;
import com.pz.xingfutao.utils.PLog;

public class SettleAdapter extends BaseAdapter {
	
	private Context context;
	private List<ItemDetailEntity> datas;
	
	private EditText message;
	
	public SettleAdapter(Context context, List<ItemDetailEntity> datas){
		this.context = context;
		this.datas = datas;
	}
	
	public String getMessage(){
		if(message != null && message.getText() != null){
			return message.getText().toString();
		}
		
		return null;
	}

	@Override
	public int getCount() {
		return datas.size() + 2;
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
	public int getViewTypeCount(){
		return 3;
	}
	
	@Override
	public int getItemViewType(int position){
		if(position == 0){
			return 0;
		}else if(position == datas.size() + 1){
			return 2;
		}else{
			return 1;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PLog.d("position", position + "");
		LayoutInflater inflater = LayoutInflater.from(context);
		switch(getItemViewType(position)){
		case 0:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_settle_info, null, false);
				
				String sharedAddress = XFSharedPreference.getInstance(context).getAddress();
				String sharedConsignee = XFSharedPreference.getInstance(context).getConsignee();
				String sharedNumber = XFSharedPreference.getInstance(context).getPhoneNumber();
				
				if(sharedAddress != null){
					((TextView) convertView.findViewById(R.id.address)).setText(sharedAddress);
				}else{
					((TextView) convertView.findViewById(R.id.address)).setText("");
				}
				
				if(sharedConsignee != null){
					((TextView) convertView.findViewById(R.id.consignee)).setText(sharedConsignee);
				}else{
					((TextView) convertView.findViewById(R.id.consignee)).setText("");
				}
				
				if(sharedNumber != null){
					((TextView) convertView.findViewById(R.id.number)).setText(sharedNumber);
				}else{
					((TextView) convertView.findViewById(R.id.number)).setText("");
				}
				
				
				convertView.findViewById(R.id.info).setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						((TabActivity) context).getLastFragment().startFragmentWithBackEnabled(new AddressEditFragment(), "");
					}
				});
			}
			
			break;
		case 1:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_settle_good, null, false);
			}
			
			NetworkHandler.getInstance(context).imageRequest(datas.get(position - 1).getThumb(), (ImageView) convertView.findViewById(R.id.thumb));
			((TextView) convertView.findViewById(R.id.title)).setText(datas.get(position - 1).getName());
			((TextView) convertView.findViewById(R.id.count)).setText(datas.get(position - 1).getPurchaseCount() + "");
			((TextView) convertView.findViewById(R.id.price)).setText(datas.get(position - 1).getPurchaseCount() * datas.get(position - 1).getShopPrice() + "");
			
			break;
		case 2:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_settle_total, null, false);
				message = (EditText) convertView.findViewById(R.id.message);
				
				int count = 0;
				float totalPrice = 0;
				
				for(int i = 0; i < datas.size(); i++){
					count += datas.get(i).getPurchaseCount();
					totalPrice += (datas.get(i).getShopPrice() * datas.get(i).getPurchaseCount());
				}
				
				((TextView) convertView.findViewById(R.id.count)).setText(count + "");
				((TextView) convertView.findViewById(R.id.delivery_cost)).setText(totalPrice > 198 ? "¥0.0" : "¥15.00");
				((TextView) convertView.findViewById(R.id.total_price)).setText("¥" + totalPrice);
			}
			break;
		}
		return convertView;
	}

}
