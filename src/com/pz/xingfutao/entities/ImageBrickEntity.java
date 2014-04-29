package com.pz.xingfutao.entities;

import com.pz.xingfutao.entities.base.BaseTabStoreEntity;


public class ImageBrickEntity extends BaseTabStoreEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 520388572328565225L;
	
	private String title;
	private ImageMap primaryImageMap;
	
	private ImageMap[] subImageMap;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public ImageMap getPrimaryImageMap(){
		return primaryImageMap;
	}
	
	public void setPrimaryImageMap(ImageMap primaryImageMap){
		this.primaryImageMap = primaryImageMap;
	}
	
	public ImageMap[] getSubImageMap(){
		return subImageMap;
	}
	
	public void setSubImageMap(ImageMap[] subImageMap){
		this.subImageMap = subImageMap;
	}
}
