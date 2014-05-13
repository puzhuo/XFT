package com.pz.xingfutao.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pz.xingfutao.entities.base.BaseEntity;

public class OrderEntity extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8061421561451826833L;
	@Expose
	@SerializedName("")
	private String goodId;
	@Expose
	@SerializedName("")
	private String goodThumb;
	@Expose 
	@SerializedName("")
	private String goodName;
	@Expose
	@SerializedName("")
	private int purchaseCount;
	@Expose
	@SerializedName("")
	private long timeStamp;
	@Expose
	@SerializedName("")
	private String address;
	@Expose
	@SerializedName("")
	private String phoneNumber;
	@Expose
	@SerializedName("")
	private String recipient;
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getGoodThumb() {
		return goodThumb;
	}
	public void setGoodThumb(String goodThumb) {
		this.goodThumb = goodThumb;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public int getPurchaseCount() {
		return purchaseCount;
	}
	public void setPurchaseCount(int purchaseCount) {
		this.purchaseCount = purchaseCount;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	@Override
	public String toString() {
		return "OrderEntity [goodId=" + goodId + ", goodThumb=" + goodThumb
				+ ", goodName=" + goodName + ", purchaseCount=" + purchaseCount
				+ ", timeStamp=" + timeStamp + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", recipient=" + recipient
				+ "]";
	}
	
	
}
