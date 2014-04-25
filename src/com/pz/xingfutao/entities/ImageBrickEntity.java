package com.pz.xingfutao.entities;

public class ImageBrickEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 520388572328565225L;
	
	private String title;
	private String primaryImageUrl;
	private String primaryImageLink;
	
	private String[] subImageUrls;
	private String[] subImageTitles;
	private String[] subItemId;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPrimaryImageUrl() {
		return primaryImageUrl;
	}
	public void setPrimaryImageUrl(String primaryImageUrl) {
		this.primaryImageUrl = primaryImageUrl;
	}
	public String getPrimaryImageLink() {
		return primaryImageLink;
	}
	public void setPrimaryImageLink(String primaryImageLink) {
		this.primaryImageLink = primaryImageLink;
	}
	public String[] getSubImageUrls() {
		return subImageUrls;
	}
	public void setSubImageUrls(String[] subImageUrls) {
		this.subImageUrls = subImageUrls;
	}
	public String[] getSubImageTitles() {
		return subImageTitles;
	}
	public void setSubImageTitles(String[] subImageTitles) {
		this.subImageTitles = subImageTitles;
	}
	public String[] getSubItemId() {
		return subItemId;
	}
	public void setSubItemId(String[] subItemId) {
		this.subItemId = subItemId;
	}
	
	
}
