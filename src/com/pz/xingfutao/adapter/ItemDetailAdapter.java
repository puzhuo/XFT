package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.CommentEntity;
import com.pz.xingfutao.entities.ItemDetailEntity;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.utils.FragmentUtil;
import com.pz.xingfutao.utils.Renderer;

public class ItemDetailAdapter extends BaseAdapter{
	
	private static final int ITEM_IMAGE = 0;
	private static final int SOLD = 1;
	private static final int DETAIL = 2;
	private static final int COMMENT = 3;
	private static final int PARAMS = 4;
	private static final int EXTRA = 5;
	private static final int COMMENT_CONTENT = 6;
	
	private Context context;
	private ItemDetailEntity detailEntity;
	private List<CommentEntity> comments;

	public ItemDetailAdapter(Context context, ItemDetailEntity detailEntity, List<CommentEntity> comments){
		this.context = context;
		this.detailEntity = detailEntity;
		this.comments = comments;
	}
	
	public void setItemDetail(ItemDetailEntity detailEntity){
		this.detailEntity = detailEntity;
	}
	
	@Override
	public int getCount() {
		return detailEntity == null || detailEntity.getId() == null ? 0 : 6 + comments.size();
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
		return 7;
	}
	
	@Override
	public int getItemViewType(int position){
		if(position < 4) return position;
		if(position == getCount() - 1) return EXTRA;
		if(position == getCount() - 2) return PARAMS;
		
		return COMMENT_CONTENT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		
		switch(getItemViewType(position)){
		case ITEM_IMAGE:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_item_detail_head, null, false);
				
				NetworkHandler.getInstance(context).imageRequest(Renderer.getBaseUrl() + detailEntity.getImage(), (ImageView) convertView.findViewById(R.id.thumb));
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
						
					}
				});
				
				View cart = convertView.findViewById(R.id.add_to_cart);
				cart.setClickable(true);
				cart.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v){
						
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
			break;
		case COMMENT_CONTENT:
			break;
		case PARAMS:
			if(convertView == null){
				convertView = inflater.inflate(R.layout.item_listview_item_detail_common_arrow, null, false);
				
				((ImageView) convertView.findViewById(R.id.icon)).setImageResource(R.drawable.detail_comment);
			}
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
