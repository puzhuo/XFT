package com.pz.xingfutao.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pz.xingfutao.entities.base.BaseEntity;

public class ItemDetailEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3536562742238872684L;
	
	@Expose
	@SerializedName("goods_id")
	private String id;
	@Expose
	@SerializedName("cat_id")
	private String catId;
	@Expose
	@SerializedName("brand_id")
	private String brandId;
	
	@Expose
	@SerializedName("goods_sn")
	private String sn;
	@Expose
	@SerializedName("goods_name")
	private String name;
	@Expose
	@SerializedName("description")
	private String description;
	@Expose
	@SerializedName("goods_thumb")
	private String thumb;
	@Expose
	@SerializedName("goods_img")
	private String image;
	@Expose
	@SerializedName("original_img")
	private String originalImage;
	
	@Expose
	@SerializedName("is_hot")
	private boolean isHot;
	@Expose
	@SerializedName("is_new")
	private boolean isNew;
	@Expose
	@SerializedName("is_best")
	private boolean isBest;
	@Expose
	@SerializedName("is_delete")
	private boolean isDelete;
	
	@Expose
	@SerializedName("click_count")
	private int clickedCount;
	@Expose
	@SerializedName("goods_weight")
	private float weight;
	@Expose
	@SerializedName("market_price")
	private float marketPrice;
	@Expose
	@SerializedName("shop_price")
	private float shopPrice;
	@Expose
	@SerializedName("promote_price")
	private float promotePrice;
	
	@Expose
	@SerializedName("add_time")
	private String addedTime;
	
	//temp
	@Expose
	@SerializedName("goods_thumb_api")
	private String thumbTemp;
	
	public ImageMap getItemMap(){
		return new ImageMap().setLinkType(ImageMap.LINK_GOOD_DETAIL).setLink(id).setTitle(name);
	}
	
	public ImageMap getDescMap(){
		return new ImageMap().setLinkType(ImageMap.LINK_GOOD_DESC).setLink(description).setTitle(name);
	}
	
	public String getThumbTemp(){
		return thumbTemp;
	}
	
	public void setThumbTemp(String thumbTemp){
		this.thumbTemp = thumbTemp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getOriginalImage() {
		return originalImage;
	}

	public void setOriginalImage(String originalImage) {
		this.originalImage = originalImage;
	}

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public boolean isBest() {
		return isBest;
	}

	public void setBest(boolean isBest) {
		this.isBest = isBest;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public int getClickedCount() {
		return clickedCount;
	}

	public void setClickedCount(int clickedCount) {
		this.clickedCount = clickedCount;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(float marketPrice) {
		this.marketPrice = marketPrice;
	}

	public float getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(float shopPrice) {
		this.shopPrice = shopPrice;
	}

	public float getPromotePrice() {
		return promotePrice;
	}

	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}

	public String getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(String addedTime) {
		this.addedTime = addedTime;
	}

	@Override
	public String toString() {
		return "ItemDetailEntity [id=" + id + ", catId=" + catId + ", brandId="
				+ brandId + ", sn=" + sn + ", name=" + name + ", description="
				+ description + ", thumb=" + thumb + ", image=" + image
				+ ", originalImage=" + originalImage + ", isHot=" + isHot
				+ ", isNew=" + isNew + ", isBest=" + isBest + ", isDelete="
				+ isDelete + ", clickedCount=" + clickedCount + ", weight="
				+ weight + ", marketPrice=" + marketPrice + ", shopPrice="
				+ shopPrice + ", promotePrice=" + promotePrice + ", addedTime="
				+ addedTime + "]";
	}
	
	
}
