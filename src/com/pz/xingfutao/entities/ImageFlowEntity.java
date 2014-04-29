package com.pz.xingfutao.entities;

import java.util.Arrays;

import com.pz.xingfutao.entities.base.BaseTabStoreEntity;

public class ImageFlowEntity extends BaseTabStoreEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4033186098981163794L;
	
	private ImageMap[] imageUrls;
	
	public void setImageUrls(ImageMap[] imageUrls){
		this.imageUrls = imageUrls;
	}
	
	public ImageMap[] getImageUrls(){
		return imageUrls;
	}

	@Override
	public String toString() {
		return "ImageFlowEntity [imageUrls=" + Arrays.toString(imageUrls) + "]";
	}
	
	
}
