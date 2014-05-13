package com.pz.xingfutao.entities.base;

import java.util.Arrays;

import com.pz.xingfutao.entities.ImageMap;

public class BaseTabStoreEntity extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7870328006536652599L;
	
	private ImageMap[] imageMaps;

	public ImageMap[] getImageMaps() {
		return imageMaps;
	}

	public void setImageMaps(ImageMap[] imageMaps) {
		this.imageMaps = imageMaps;
	}

	@Override
	public String toString() {
		return "BaseTabStoreEntity [imageMaps=" + Arrays.toString(imageMaps)
				+ "]";
	}
	
	

}
