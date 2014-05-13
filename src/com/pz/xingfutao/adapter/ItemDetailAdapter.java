package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.pz.xingfutao.R;
import com.pz.xingfutao.api.ContentApi;
import com.pz.xingfutao.api.UserApi;
import com.pz.xingfutao.dao.XFDatabase;
import com.pz.xingfutao.dao.XFSharedPreference;
import com.pz.xingfutao.entities.CommentEntity;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.ui.tab.TabActivity;
import com.pz.xingfutao.ui.tab.fragments.TabCartFragment;
import com.pz.xingfutao.utils.FragmentUtil;
import com.pz.xingfutao.utils.LoginUtil;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.widget.AddToCart;
import com.pz.xingfutao.widget.XFToast;

public class ItemDetailAdapter extends BaseAdapter{
	
	
	private static final int ITEM_IMAGE = 0;
	private static final int SOLD = 1;
	private static final int DETAIL = 2;
	private static final int COMMENT = 3;
	private static final int EXTRA = 4;
	private static final int COMMENT_CONTENT = 5;
	
	private boolean commentadd;
	
	private Context context;
	private ItemDetailEntity detailEntity;
	private List<CommentEntity> comments;
	private AddToCart addToCart;
	
	private String[] floorNames;
	private String[] floorBackgrounds;

	public ItemDetailAdapter(Context context, ItemDetailEntity detailEntity, List<CommentEntity> comments, AddToCart addToCart){
		this.context = context;
		this.detailEntity = detailEntity;
		this.comments = comments;
		this.addToCart = addToCart;
		
		commentadd = false;
		
		floorNames = context.getResources().getStringArray(R.array.floor_name);
		floorBackgrounds = context.getResources().getStringArray(R.array.floor_background);
	}
	
	public void setItemDetail(ItemDetailEntity detailEntity){
		this.detailEntity = detailEntity;
	}
	
	@Override
	public int getCount() {
		return detailEntity == null || detailEntity.getId() == null ? 0 : 5 + comments.size();
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
		return 6;
	}
	
	@Override
	public int getItemViewType(int position){
		if(position < 4) return position;
		if(position == getCount() - 1) return EXTRA;
		
		return COMMENT_CONTENT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		
		switch(getItemViewType(position)){
		case ITEM_IMAGE:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_item_detail_head, null, false);
				
				NetworkHandler.getInstance(context).imageRequest(detailEntity.getImage(), (ImageView) convertView.findViewById(R.id.thumb));
				((TextView) convertView.findViewById(R.id.title)).setText(detailEntity.getName());
				((TextView) convertView.findViewById(R.id.price)).setText("¥" + detailEntity.getShopPrice());
				TextView op = (TextView) convertView.findViewById(R.id.origin_price);
				op.setPaintFlags(op.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				op.setText("¥" + detailEntity.getMarketPrice());
				
				View purchase = convertView.findViewById(R.id.purchase);
				purchase.setClickable(true);
				purchase.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						if(LoginUtil.checkLogin()){
							NetworkHandler.getInstance(context).stringRequest(Method.POST, UserApi.getAddCartUrl(detailEntity.getId(), XFSharedPreference.getInstance(context).getUserId()), new Listener<String>(){
								@Override
								public void onResponse(String response){
									if(UserApi.checkAddToCart(response)){
										TabCartFragment fragment = new TabCartFragment();
										Bundle bundle = new Bundle();
										bundle.putBoolean("extra_back_button", true);
										fragment.setArguments(bundle);
										PLog.d("bundle", fragment.getArguments().toString());
										((TabActivity) context).getLastFragment().startFragmentWithBackEnabled(fragment, context.getString(R.string.tab_title_cart));
									}
								}
							}, null);
						}else{
							XFDatabase.getInstance(context).insertCart(detailEntity, 1);
							TabCartFragment fragment = new TabCartFragment();
							Bundle bundle = new Bundle();
							bundle.putBoolean("extra_back_button", true);
							fragment.setArguments(bundle);
							PLog.d("bundle", fragment.getArguments().toString());
							((TabActivity) context).getLastFragment().startFragmentWithBackEnabled(fragment, context.getString(R.string.tab_title_cart));
						}
					}
				});
				
				View cart = convertView.findViewById(R.id.add_to_cart);
				cart.setClickable(true);
				cart.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						
						if(addToCart != null){
							addToCart.performAdd();
						}
						
						
						if(LoginUtil.checkLogin()){
							NetworkHandler.getInstance(context).stringRequest(Method.POST, UserApi.getAddCartUrl(detailEntity.getId(), XFSharedPreference.getInstance(context).getUserId()), new Listener<String>(){
								@Override
								public void onResponse(String response){
									if(UserApi.checkAddToCart(response)){
										XFToast.showTextShort("添加成功");
									}
								}
							}, null);
						}else{
							XFDatabase.getInstance(context).insertCart(detailEntity, 1);
							XFToast.showTextShort("添加成功");
						}
					}
				});
			}
			
			break;
		case SOLD:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_item_detail_sold, null, false);
			}
			break;
		case DETAIL:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_item_detail_common_arrow, null, false);
			}
			convertView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					FragmentUtil.startImageMappingFragment(context, detailEntity.getDescMap());
				}
			});
			break;
		case COMMENT:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_item_detail_common_arrow, null, false);
				
				((ImageView) convertView.findViewById(R.id.icon)).setImageResource(R.drawable.detail_comment); 
				((TextView) convertView.findViewById(R.id.title)).setText(context.getString(R.string.fragment_item_detail_title_comment));
			}
			
			convertView.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					if(!commentadd){
						NetworkHandler.getInstance(context).stringRequest(Method.POST, ContentApi.getItemDetailCommentUrl(detailEntity.getId()), new Listener<String>(){

							@Override
							public void onResponse(String response) {
								PLog.d("response", response);
								comments.clear();
								comments.addAll(ContentApi.parseItemDetailComment(response));
								PLog.d("int", comments.size() + "");
								ItemDetailAdapter.this.notifyDataSetChanged();
							}
							
						}, null);
						commentadd = true;
					}else{
						comments.clear();
						ItemDetailAdapter.this.notifyDataSetChanged();
						commentadd = false;
					}
				}
			});
			
			break;
		case COMMENT_CONTENT:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_post_detail_comment, null, false);
				convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selector_settings));
				convertView.findViewById(R.id.gender).setVisibility(View.GONE);
				convertView.findViewById(R.id.avatar).setVisibility(View.INVISIBLE);
			}
			
			int rPosition = position - 4;
			
            TextView floorText = (TextView) convertView.findViewById(R.id.floor);
			
			if(rPosition < floorNames.length - 1){
				floorText.setText(floorNames[rPosition]);
			}else{
				floorText.setText(position - 3 + floorNames[floorNames.length - 1]);
			}
			
			int id;
			if(rPosition < floorBackgrounds.length - 1){
				id = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + floorBackgrounds[rPosition], null, null);
			}else{
				id = context.getResources().getIdentifier(context.getPackageName() + ":drawable/" + floorBackgrounds[floorBackgrounds.length - 1], null, null);
			}
			
			floorText.setBackgroundDrawable(context.getResources().getDrawable(id));
			
			((TextView) convertView.findViewById(R.id.user_name)).setText(comments.get(rPosition).getName());
			((TextView) convertView.findViewById(R.id.content)).setText(comments.get(rPosition).getComment());
			((TextView) convertView.findViewById(R.id.timestamp)).setText(comments.get(rPosition).getCommentId());
			
			break;
		case EXTRA:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_post_detail_comment, null, false);
			}
			break;
		}
		
		return convertView;
	}

}
