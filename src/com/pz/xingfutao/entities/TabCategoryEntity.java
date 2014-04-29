package com.pz.xingfutao.entities;

import com.pz.xingfutao.entities.base.BaseEntity;

public class TabCategoryEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2290198622760623212L;
	
	private ImageMap thumb;
	private String title;
	private String[] tags;
	
	public ImageMap getThumb() {
		return thumb;
	}
	public void setThumb(ImageMap thumb) {
		this.thumb = thumb;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	
}
