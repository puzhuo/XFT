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
import com.pz.xingfutao.entities.ImageHotEntity;
import com.pz.xingfutao.entities.base.BaseEntity;
import com.pz.xingfutao.entities.base.BaseTabStoreEntity;
import com.pz.xingfutao.graphics.RoundDrawable;
import com.pz.xingfutao.widget.FitWidthImageView;
import com.pz.xingfutao.widget.IndicatorViewPager;
import com.pz.xingfutao.widget.ViewPagerIndicator;

public class TabStoreAdapter extends BaseAdapter{
	
	private static final int TYPE_IMAGE_FLOW = 0;
	private static final int TYPE_IMAGE_BRICK = 1;
	private static final int TYPE_IMAGE_HOT = 2;
	
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
		return 3;
	}
	
	@Override
	public int getItemViewType(int position){
		if(datas.get(position) instanceof ImageFlowEntity){
			return TYPE_IMAGE_FLOW;
		}
		if(datas.get(position) instanceof ImageBrickEntity){
			return TYPE_IMAGE_BRICK;
		}
		if(datas.get(position) instanceof ImageHotEntity){
			return TYPE_IMAGE_HOT;
		}
		
		return TYPE_IMAGE_FLOW;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		switch(getItemViewType(position)){
		case TYPE_IMAGE_FLOW:
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_store_flow, parent, false);
				
				View background = convertView.findViewById(R.id.indicator_background);
				background.setBackgroundDrawable(new RoundDrawable(0x40000000));
				
				FlowHolder flowHolder = new FlowHolder();
				
				flowHolder.viewPager = (IndicatorViewPager) convertView.findViewById(R.id.view_pager);
				flowHolder.viewPager.setAutoScroll(true, 4000L);
				flowHolder.adapter = new TabStoreImageFlowAdapter(context, (ImageFlowEntity) datas.get(position));
				flowHolder.viewPager.setAdapter(flowHolder.adapter);
				flowHolder.indicator = (ViewPagerIndicator) convertView.findViewById(R.id.indicator);
				flowHolder.indicator.setViewPager(flowHolder.viewPager);
				
				convertView.setTag(flowHolder);
			}
			
			FlowHolder rFlowHolder = (FlowHolder) convertView.getTag();
			
			rFlowHolder.adapter.setData((ImageFlowEntity) datas.get(position));
			rFlowHolder.adapter.notifyDataSetChanged();
			
			
			break;
		case TYPE_IMAGE_BRICK:
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_store_brick, null, false);
				BrickHolder brickHolder = new BrickHolder();
				brickHolder.primaryImage = (FitWidthImageView) convertView.findViewById(R.id.primary_image);
				brickHolder.primaryTitle = (TextView) convertView.findViewById(R.id.title);
				brickHolder.subImage0 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_0);
				brickHolder.subImage1 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_1);
				brickHolder.subImage2 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_2);
				brickHolder.subImage3 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_3);
				brickHolder.subImage4 = (FitWidthImageView) convertView.findViewById(R.id.sub_image_4);
				
				brickHolder.subTitle0 = (TextView) convertView.findViewById(R.id.sub_title_0);
				brickHolder.subTitle1 = (TextView) convertView.findViewById(R.id.sub_title_1);
				brickHolder.subTitle2 = (TextView) convertView.findViewById(R.id.sub_title_2);
				brickHolder.subTitle3 = (TextView) convertView.findViewById(R.id.sub_title_3);
				brickHolder.subTitle4 = (TextView) convertView.findViewById(R.id.sub_title_4);
				
				convertView.setTag(brickHolder);
			}
			
			BrickHolder rBrickHolder = (BrickHolder) convertView.getTag();
			
			ImageBrickEntity imageBrick = (ImageBrickEntity) datas.get(position);
			
			rBrickHolder.primaryImage.setNetworkImage(imageBrick.getImageMaps()[0]);
			
			rBrickHolder.primaryTitle.setText(imageBrick.getImageMaps()[0].getTitle());
			
			rBrickHolder.subImage0.setNetworkImage(imageBrick.getImageMaps()[1]);
			rBrickHolder.subTitle0.setText(imageBrick.getImageMaps()[1].getTitle());
			
			rBrickHolder.subImage1.setNetworkImage(imageBrick.getImageMaps()[2]);
			rBrickHolder.subTitle1.setText(imageBrick.getImageMaps()[2].getTitle());
			
			rBrickHolder.subImage2.setNetworkImage(imageBrick.getImageMaps()[3]);
			rBrickHolder.subTitle2.setText(imageBrick.getImageMaps()[3].getTitle());
			
			rBrickHolder.subImage3.setNetworkImage(imageBrick.getImageMaps()[4]);
			rBrickHolder.subTitle3.setText(imageBrick.getImageMaps()[4].getTitle());
			
			rBrickHolder.subImage4.setNetworkImage(imageBrick.getImageMaps()[5]);
			rBrickHolder.subTitle4.setText(imageBrick.getImageMaps()[5].getTitle());
			
			break;
		case TYPE_IMAGE_HOT:
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_tab_store_hot, null, false);
				
				HotHolder hotHolder = new HotHolder();
				
				hotHolder.primaryTitle = (TextView) convertView.findViewById(R.id.title);
				
				hotHolder.image0 = (FitWidthImageView) convertView.findViewById(R.id.hot_0);
				hotHolder.image1 = (FitWidthImageView) convertView.findViewById(R.id.hot_1);
				hotHolder.image2 = (FitWidthImageView) convertView.findViewById(R.id.hot_2);
				
				hotHolder.title0 = (TextView) convertView.findViewById(R.id.title_0);
				hotHolder.title1 = (TextView) convertView.findViewById(R.id.title_1);
				hotHolder.title2 = (TextView) convertView.findViewById(R.id.title_2);
				
				convertView.setTag(hotHolder);
			}
			
			ImageHotEntity imageHot = (ImageHotEntity) datas.get(position);
			
			HotHolder rHotHolder = (HotHolder) convertView.getTag();
			
			rHotHolder.primaryTitle.setText(imageHot.getImageMaps()[0].getTitle());
			rHotHolder.title0.setText(imageHot.getImageMaps()[1].getTitle());
			rHotHolder.title1.setText(imageHot.getImageMaps()[2].getTitle());
			rHotHolder.title2.setText(imageHot.getImageMaps()[3].getTitle());
			rHotHolder.image0.setNetworkImage(imageHot.getImageMaps()[1]);
			rHotHolder.image1.setNetworkImage(imageHot.getImageMaps()[2]);
			rHotHolder.image2.setNetworkImage(imageHot.getImageMaps()[3]);
			
			break;
		}
		
		return convertView;
	}
	
	private class HotHolder{
		TextView primaryTitle;
		
		FitWidthImageView image0;
		TextView title0;
		
		FitWidthImageView image1;
		TextView title1;
		
		FitWidthImageView image2;
		TextView title2;
	}
	
	private class BrickHolder{
		FitWidthImageView primaryImage;
		TextView primaryTitle;
		
		FitWidthImageView subImage0;
		TextView subTitle0;
		
		FitWidthImageView subImage1;
		TextView subTitle1;
		
		FitWidthImageView subImage2;
		TextView subTitle2;
		
		FitWidthImageView subImage3;
		TextView subTitle3;
		
		FitWidthImageView subImage4;
		TextView subTitle4;
	}
	
	private class FlowHolder{
		IndicatorViewPager viewPager;
		TabStoreImageFlowAdapter adapter;
		ViewPagerIndicator indicator;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
