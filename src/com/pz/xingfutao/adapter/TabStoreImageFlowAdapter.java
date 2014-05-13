package com.pz.xingfutao.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.pz.xingfutao.R;
import com.pz.xingfutao.entities.ImageFlowEntity;
import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.utils.FragmentUtil;
import com.pz.xingfutao.widget.FitWidthImageView;

public class TabStoreImageFlowAdapter extends PagerAdapter {
	
	private Context context;
	private ImageFlowEntity datas;
	
	private List<View> viewContainer;
	
	public TabStoreImageFlowAdapter(Context context, ImageFlowEntity datas){
		this.context = context;
		this.datas = datas;
		viewContainer = new ArrayList<View>();
	}
	
	public void setData(ImageFlowEntity datas){
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.getImageMaps().length;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return (View) object == view;
	}
	
	@Override
	public View instantiateItem(ViewGroup container, final int position){
		if(viewContainer.size() > position){
			View view = viewContainer.get(position);
			if(view != null){
				
				return view;
			}
		}
		
		View newInstance = LayoutInflater.from(context).inflate(R.layout.item_viewpager_tab_store_flow, null, false);
		
		FitWidthImageView imageView = (FitWidthImageView) newInstance.findViewById(R.id.image_view);
		NetworkHandler.getInstance(context).imageRequest(datas.getImageMaps()[position].getImageLink(), imageView);
		imageView.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				
				FragmentUtil.startImageMappingFragment(context, datas.getImageMaps()[position]);
			}
		});
		
		while(viewContainer.size() <= position){
			viewContainer.add(null);
		}
		
		viewContainer.set(position, newInstance);
		
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		
		container.addView(newInstance, params);
		return newInstance;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object){
		View v = (View) object;
		
		container.removeView(v);
		viewContainer.set(position, null);
	}

}
