package com.pz.xingfutao.ui.tab.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.TabCartAdapter;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.api.UserApi;
import com.pz.xingfutao.dao.XFDatabase;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.BaseTitleFragment;
import com.pz.xingfutao.ui.sub.SettleFragment;
import com.pz.xingfutao.ui.tab.TabActivity;
import com.pz.xingfutao.utils.LoginUtil;
import com.pz.xingfutao.utils.PLog;

public class TabCartFragment extends BaseTitleFragment{
	
	private TabCartAdapter adapter;
	private List<ItemDetailEntity> datas;
	private Map<String, Integer>[] extraData;
	
	private CheckBox totalSettleCheck;
	private TextView settleButton;
	private TextView total;
	
	private ListView list;
	
	private boolean editMode;
	
	private boolean altered;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		editMode = false;
		altered = false;
		
		setContentViewWithMode(R.layout.fragment_tab_cart, MODE_TITLE | MODE_RIGHT_BUTTON);
		
		list = (ListView) content.findViewById(R.id.list);
		totalSettleCheck = (CheckBox) content.findViewById(R.id.confirm);
		settleButton = (TextView) content.findViewById(R.id.settle_button);
		total = (TextView) content.findViewById(R.id.total);
		
		settleButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				if(editMode){
					if(LoginUtil.checkLogin()){
						
					}else{
						if(extraData != null && extraData.length == datas.size()){
							for(int i = 0; i < extraData.length; i++){
								if(extraData[i] != null && extraData[i].containsKey("is_check") && extraData[i].get("is_check") == 1){
									XFDatabase.getInstance(getActivity()).deleteCartByGoodId(datas.get(i).getId());
								}
							}
							datas.clear();
							datas.addAll(Arrays.asList(XFDatabase.getInstance(getActivity()).getCartList()));
							extraData = new HashMap[datas.size()];
							if(extraData.length > 0){
								extraData[0] = new HashMap<String, Integer>();
								extraData[0].put("edit_mode", 1);
							}
							adapter.setExtraData(extraData);
							adapter.notifyDataSetChanged();
						}
					}
				}else{
					if(totalSettleCheck.isChecked()){
						SettleFragment fragment = new SettleFragment();
						Bundle bundle = new Bundle();
						bundle.putSerializable("items", datas.toArray(new ItemDetailEntity[datas.size()]));
						bundle.putSerializable("extra", extraData);
						fragment.setArguments(bundle);
						startFragmentWithBackEnabled(fragment, "");
					}
				}
			}
		});
		
		totalSettleCheck.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				int check;
				if(totalSettleCheck.isChecked()){
					check = 1;
					int totalPrice = 0;
					for(ItemDetailEntity entity : datas){
						totalPrice += entity.getShopPrice() * entity.getPurchaseCount();
					}
					
					total.setText("¥" + totalPrice);
					
				}else{
					check = 0;
				}
				for(Map<String, Integer> map : extraData){
					map.put("is_check", check);
				}
				
				adapter.notifyDataSetChanged();
			}
		});
		
		totalSettleCheck.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					int totalPrice = 0;
					if(datas.size() == extraData.length){
						for(int i = 0; i < extraData.length; i++){
							if(extraData[i] != null && extraData[i].containsKey("is_check") && extraData[i].get("is_check") == 1){
								totalPrice += datas.get(i).getShopPrice() * datas.get(i).getPurchaseCount();
								PLog.d("totalPrice", totalPrice + "");
							}
						}
					}
					
					total.setText("¥" + totalPrice);
				}else{
					total.setText("¥" + 0);
				}
			}
		});
		
		datas = new ArrayList<ItemDetailEntity>();
		if(extraData == null) extraData = new HashMap[0];
		adapter = new TabCartAdapter(getActivity(), datas, extraData, totalSettleCheck);
		list.setAdapter(adapter);
		
	}
	
	@Override
	public void onResume(){
		super.onResume();
		PLog.d("resume", "cart");
		Bundle bundle = ((TabActivity) getActivity()).getBundle(this);
		if(bundle != null){
			extraData = (Map<String, Integer>[]) bundle.getSerializable("extra");
			if(extraData != null && adapter != null){
				adapter.setExtraData(extraData);
				adapter.notifyDataSetChanged();
			}
		}
		
		if(LoginUtil.checkLogin()){
			NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, UserApi.getRetrieveCartUrl(XFSharedPreference.getInstance(getActivity()).getUserId()), new Listener<String>(){
				@Override
				public void onResponse(String response){
					PLog.d("response", response);
					List<ItemDetailEntity> result = ContentApi.parseMainCategoryDetail(response);
					if(result != null){
						datas.clear();
						datas.addAll(result);
						if(extraData == null || extraData.length != datas.size())extraData = new HashMap[datas.size()];
						adapter.setExtraData(extraData);
						adapter.notifyDataSetChanged();
					}
				}
			}, this);
		}else{
			List<ItemDetailEntity> result = Arrays.asList(XFDatabase.getInstance(getActivity()).getCartList());
			if(result != null){
				datas.clear();
				datas.addAll(result);
				if(extraData == null || extraData.length != datas.size())extraData = new HashMap[datas.size()];
				adapter.setExtraData(extraData);
				adapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_RIGHT_BUTTON:
			if(editMode){
				editMode = false;
				if(extraData != null && extraData.length > 0){
					if(extraData[0] == null) extraData[0] = new HashMap<String, Integer>();
					extraData[0].put("edit_mode", 0);
					adapter.notifyDataSetChanged();
				}
				settleButton.setText(R.string.fragment_tab_cart_settle);
				getRightButton().setImageResource(R.drawable.selector_title_button_settings);
			}else{
				editMode = true;
				altered = true;
				if(extraData != null && extraData.length > 0){
					if(extraData[0] == null) extraData[0] = new HashMap<String, Integer>();
					extraData[0].put("edit_mode", 1);
					adapter.notifyDataSetChanged();
				}
				settleButton.setText(R.string.fragment_tab_cart_delete);
				getRightButton().setImageResource(R.drawable.selector_title_button_confirm);
			}
			break;
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		PLog.d("pause", "cart");
		if(extraData != null && extraData.length > 0){
			if(extraData[0] != null && extraData[0].containsKey("edit_mode")){
				extraData[0].remove("edit_mode");
			}
			Bundle bundle = new Bundle();
			bundle.putSerializable("extra", extraData);
			((TabActivity) getActivity()).addToBundle(this, bundle);
		}
		
		NetworkHandler.getInstance(getActivity()).stringRequest(UserApi.getUpdateCartParams(datas), UserApi.getUpdateCartUrl(), new Listener<String>(){
			@Override
			public void onResponse(String response){
				PLog.d("response", response);
			}
		}, null);
	}
	
	@Override
	protected boolean isContentEmpty(){
		return list == null || list.getAdapter() == null || list.getAdapter().getCount() == 0;
	}
	
	@Override
	protected View getEmptyStateView(){
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout_tab_cart, null, false);
		
		((Button) v.findViewById(R.id.go_shopping)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Activity activity = getActivity();
				if(activity instanceof TabActivity){
					((TabActivity) activity).removeFragmentsAndSwitchTo(TabCartFragment.this, 0);
				}
			}
		});
		
		return v;
	}
}
