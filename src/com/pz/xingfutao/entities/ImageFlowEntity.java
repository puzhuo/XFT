package com.pz.xingfutao.entities;

public class ImageFlowEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4033186098981163794L;
	
	private String[] imageUrls;
	
	public void setImageUrls(String[] imageUrls){
		this.imageUrls = imageUrls;
	}
	
	public String[] getImageUrls(){
		return imageUrls;
	}
}
