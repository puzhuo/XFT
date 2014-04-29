package com.pz.xingfutao.entities;

import java.io.Serializable;

public class ImageMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965044049542277761L;

	public static final int LINK_LIST = 0x1;
	public static final int LINK_ADS = 0x2;
	public static final int LINK_GOOD_DETAIL = 0x4;
	public static final int LINK_ARTICLE = 0x8;
	
	private int linkType;
	
	private String title;
	private String link;
	private String imageLink;
	
	
	public String getTitle(){
		return title;
	}
	public ImageMap setTitle(String title){
		this.title = title;
		
		return this;
	}
	public String getLink() {
		return link;
	}
	public ImageMap setLink(String link) {
		this.link = link;
		
		return this;
	}
	public String getImageLink() {
		return imageLink;
	}
	public ImageMap setImageLink(String imageLink) {
		this.imageLink = imageLink;
		
		return this;
	}
	
	public ImageMap setLinkType(int type){
		this.linkType = type;
		
		return this;
	}
	
	public int getLinkType(){
		return linkType;
	}
	@Override
	public String toString() {
		return "ImageMap [linkType=" + linkType + ", title=" + title
				+ ", link=" + link + ", imageLink=" + imageLink + "]";
	}
	
	
}
