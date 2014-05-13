package com.pz.xingfutao.ui.sub;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.adapter.ItemDetailAdapter;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.api.UserApi;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.entities.CommentEntity;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.base.BaseBackButtonFragment;
import com.pz.xingfutao.ui.tab.fragments.TabCartFragment;
import com.pz.xingfutao.utils.LoginUtil;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.widget.AddToCart;

public class ItemDetailFragment extends BaseBackButtonFragment {
	
	private ItemDetailEntity itemDetailEntity;
	private List<CommentEntity> commentLists;
	private ItemDetailAdapter adapter;
	
	private int userId;
	private String goodId;
	
	private ListView list;
	
	private ImageView cart;
	private TextView cartCount;
	
	private AddToCart addToCart;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		getTitleView().setText(getString(R.string.title_good_detail));
		
		userId = XFSharedPreference.getInstance(getActivity()).getUserId();
		
		setContentView(R.layout.fragment_item_detail);
		setMode(getMode() | MODE_RIGHT_BUTTON);
		getRightButton().setImageResource(R.drawable.selector_detail_fav);
		
		cart = (ImageView) content.findViewById(R.id.cart);
		cartCount = (TextView) content.findViewById(R.id.count);
		addToCart = new AddToCart(getLeftButton(), cart, R.drawable.shopping_cart);
		
		cart.setClickable(true);
		cart.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				TabCartFragment fragment = new TabCartFragment();
				Bundle bundle = new Bundle();
				bundle.putBoolean("extra_back_button", true);
				fragment.setArguments(bundle);
				PLog.d("bundle", fragment.getArguments().toString());
				startFragmentWithBackEnabled(fragment, getString(R.string.tab_title_cart));
			}
		});
		
		itemDetailEntity = new ItemDetailEntity();
		commentLists = new ArrayList<CommentEntity>();
		adapter = new ItemDetailAdapter(getActivity(), itemDetailEntity, commentLists, addToCart);
		
		list = (ListView) content.findViewById(R.id.list);
		list.setAdapter(adapter);
		
		new ArgExec(this, "good_id"){
			@Override
			public void set(int i, Object value){
				goodId = (String) value;
				NetworkHandler.getInstance(getActivity()).jsonRequest(Method.POST, ContentApi.getGoodDetailUrl(goodId), new Listener<JSONObject>(){

					@Override
					public void onResponse(JSONObject jsonObject) {
						
						adapter.setItemDetail(ContentApi.parseItemDetail(jsonObject));
						adapter.notifyDataSetChanged();
					}
					
				}, ItemDetailFragment.this);
				if(LoginUtil.checkLogin()){
					NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, UserApi.getIsGoodFavUrl(userId, goodId), new Listener<String>(){
						@Override
						public void onResponse(String response){
							PLog.d("fav", response);
							if(UserApi.isGoodFav(response)){
								getRightButton().setSelected(true);
							}else{
								getRightButton().setSelected(false);
							}
						}
					}, ItemDetailFragment.this);
				}
			}
		};
		
		
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(addToCart != null){
			addToCart.onDestroy();
		}
	}
	
	@Override
	protected void onClick(int id){
		super.onClick(id);
		switch(id){
		case MODE_RIGHT_BUTTON:
			if(LoginUtil.checkLogin()){
				if(goodId != null){
					if(getRightButton().isSelected()){
						NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, UserApi.getDelFavUrl(userId, goodId), new Listener<String>(){
							@Override
							public void onResponse(String response){
								if(UserApi.checkDelFav(response)) getRightButton().setSelected(false);
							}
						}, this);
					}else{
						NetworkHandler.getInstance(getActivity()).stringRequest(Method.POST, UserApi.getAddFavUrl(userId, goodId), new Listener<String>(){
							@Override
							public void onResponse(String response){
								if(UserApi.checkAddFav(response)) getRightButton().setSelected(true);
							}
						}, this);
					}
				}
			}else{
				startFragmentWithBackEnabled(new LoginFragment(), null);
			}
			break;
		}
	}

	@Override
	protected boolean isContentEmpty(){
		return list == null || list.getAdapter() == null || list.getAdapter().getCount() == 0;
	}
}
