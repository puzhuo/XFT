package com.pz.xingfutao.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.ImageBrickEntity;
import com.pz.xingfutao.entities.ImageFlowEntity;
import com.pz.xingfutao.entities.base.BaseEntity;
import com.pz.xingfutao.entities.base.BaseTabStoreEntity;
import com.pz.xingfutao.graphics.RoundDrawable;
import com.pz.xingfutao.widget.FitWidthImageView;
import com.pz.xingfutao.widget.IndicatorViewPager;
import com.pz.xingfutao.widget.ViewPagerIndicator;

public class TabStoreAdapter extends BaseAdapter{
	
	private static final int TYPE_IMAGE_FLOW = 0;
	private static final int TYPE_IMAGE_BRICK = 1;
	
	private Context context;
	private List<BaseTabStoreEntity> datas;
	
	public TabStoreAdapter(Context context, List<BaseTabStoreEntity> datas){
		this.context = context;
		this.datas = datas;
	}
	
	@Override
	public int getCount(){
		return datas.size();
	}
	
	@Override
	public BaseEntity getItem(int position){
		return datas.get(position);
	}
	
	@Override
	public int getViewTypeCount(){
		return 2;
	}
	
	@Override
	public int getItemViewType(int position){
		if(datas.get(position) instanceof ImageFlowEntity){
			return TYPE_IMAGE_FLOW;
		}
		if(datas.get(position) instanceof ImageBrickEntity){
			return TYPE_IMAGE_BRICK;
		}
		
		return TYPE_IMAGE_FLOW;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		if(convertView == null){
			switch(getItemViewType(position)){
			case TYPE_IMAGE_FLOW:
				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_store_flow, parent, false);
				
				IndicatorViewPager viewPager = (IndicatorViewPager) convertView.findViewById(R.id.view_pager);
				viewPager.setAutoScroll(true, 4000L);
				
				TabStoreImageFlowAdapter adapter = new TabStoreImageFlowAdapter(context, (ImageFlowEntity) datas.get(position));
				viewPager.setAdapter(adapter);
				
				ViewPagerIndicator indicator = (ViewPagerIndicator) convertView.findViewById(R.id.indicator);
				indicator.setViewPager(viewPager);
				
				View background = convertView.findViewById(R.id.indicator_background);
				background.setBackgroundDrawable(new RoundDrawable(0x40000000));
				
				break;
			case TYPE_IMAGE_BRICK:
				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_store_brick, null, false);
				
				ImageBrickEntity imageBrick = (ImageBrickEntity) datas.get(position);
				
				FitWidthImageView primaryImage = (FitWidthImageView) convertView.findViewById(R.id.primary_image);
				primaryImage.setNetworkImage(imageBrick.getPrimaryImageMap());
				
				TextView title = (TextView) convertView.findViewById(R.id.title);
				title.setText(imageBrick.getTitle());
				
				FitWidthImageView subImage0 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_0);
				subImage0.setNetworkImage(imageBrick.getSubImageMap()[0]);
				
				FitWidthImageView subImage1 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_1);
				subImage1.setNetworkImage(imageBrick.getSubImageMap()[1]);
				
				FitWidthImageView subImage2 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_2);
				subImage2.setNetworkImage(imageBrick.getSubImageMap()[2]);
				
				FitWidthImageView subImage3 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_3);
				subImage3.setNetworkImage(imageBrick.getSubImageMap()[3]);
				
				FitWidthImageView subImage4 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_4);
				subImage4.setNetworkImage(imageBrick.getSubImageMap()[4]);
				
				break;
			}
		}
		
		return convertView;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
