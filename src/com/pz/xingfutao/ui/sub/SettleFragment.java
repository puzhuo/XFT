package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.SettleAdapter;
import com.pz.xingfutao.api.UserApi;
import com.pz.xingfutao.dao.XFDatabase;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.api.KeyboardInvoke;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.utils.LoginUtil;
import com.pz.xingfutao.widget.XFToast;

public class SettleFragment extends BaseBackButtonFragment implements KeyboardInvoke{
	
	private ItemDetailEntity[] datas;
	private Map<String, Integer>[] extra;
	
	private List<ItemDetailEntity> rDatas;
	
	private ListView list;
	private SettleAdapter adapter;
	
	private TextView settle;

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		setContentView(R.layout.fragment_settle);
		getTitleView().setText(getString(R.string.title_settle));
		
		list = (ListView) content.findViewById(R.id.list);
		settle = (TextView) content.findViewById(R.id.settle_button);
		settle.setClickable(true);
		settle.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				String msg = adapter.getMessage();
				String address = XFSharedPreference.getInstance(getActivity()).getAddress();
				String consignee = XFSharedPreference.getInstance(getActivity()).getConsignee();
				String phone = XFSharedPreference.getInstance(getActivity()).getPhoneNumber();
				if(address != null && consignee != null && phone != null){
					
					NetworkHandler.getInstance(getActivity()).addToStringWithPost(UserApi.getSettleUrl(XFSharedPreference.getInstance(getActivity()).getUserId()), UserApi.getSettleUrlParams(address, phone, consignee, msg, rDatas), SettleFragment.this, new Listener<String>(){
						@Override
						public void onResponse(String response){
							if(UserApi.checkSettle(response)){
								XFToast.showTextShort("订单提交成功，工作人员很快会打电话跟您确认订单");
								if(!LoginUtil.checkLogin()){
									for(int i = 0; i < rDatas.size(); i++){
										XFDatabase.getInstance(getActivity()).deleteCartByGoodId(rDatas.get(i).getId());
									}
								}
								
								dismiss();
							}else{
								XFToast.showTextShort("订单提交失败，稍后再试试吧，亲");
							}
						}
					});
				}
			}
		});
		
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		new ArgExec(this, "items", "extra"){
			@Override
			public void set(int i, Object value){
				switch(i){
				case 0:
					datas = (ItemDetailEntity[]) value;
					break;
				case 1:
					extra = (Map<String, Integer>[]) value;
					break;
				}
			}
		};
		
		if(datas != null && extra != null && datas.length == extra.length){
			
			rDatas = new ArrayList<ItemDetailEntity>();
			
			float totalPrice = 0;
			
			for(int i = 0; i < extra.length; i++){
				if(extra[i].containsKey("is_check") && extra[i].get("is_check") == 1){
					rDatas.add(datas[i]);
					totalPrice += datas[i].getShopPrice() * datas[i].getPurchaseCount();
				}
			}
			
			adapter = new SettleAdapter(getActivity(), rDatas);
			list.setAdapter(adapter);
			
			((TextView) content.findViewById(R.id.total)).setText("¥" + totalPrice);
			((TextView) content.findViewById(R.id.save)).setText(totalPrice > 198 ? "¥15.00":"¥0.0");
		}
	}
}
