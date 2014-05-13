package com.pz.xingfutao.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965044049542277761L;

	public static final int LINK_GOOD_LIST = 0x1;
	public static final int LINK_ADS = 0x2;
	public static final int LINK_GOOD_DETAIL = 0x4;
	public static final int LINK_ARTICLE = 0x8;
	public static final int LINK_CATEGORY_LIST = 0x10;
	public static final int LINK_GOOD_DESC = 0x20;
	public static final int LINK_URL_GOOD_LIST = 0x40;
	public static final int LINK_SEARCH_LIST = 0x80;
	
	public static Map<String, Integer> switchy;
	
	static{
		switchy = new HashMap<String, Integer>();
		switchy.put("SINGLE", LINK_GOOD_DETAIL);
		switchy.put("KEYWORD", LINK_SEARCH_LIST);
		switchy.put("URL", LINK_URL_GOOD_LIST);
		switchy.put("CATEGORY", LINK_GOOD_LIST);
		switchy.put("AD", LINK_ARTICLE);
	}
	
	private int linkType = -1;
	
	@Expose
	@SerializedName("name")
	private String title;
	@Expose
	@SerializedName("type")
	private String type;
	@Expose
	@SerializedName("link")
	private String link;
	@Expose
	@SerializedName("image")
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
		if(linkType == -1){
			return switchy.get(type);
		}else{
			return linkType;
		}
	}
	
	
	
	@Override
	public String toString() {
		return "ImageMap [linkType=" + linkType + ", title=" + title
				+ ", link=" + link + ", imageLink=" + imageLink + "]";
	}
	
	
}
