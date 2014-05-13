package com.pz.xingfutao.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pz.xingfutao.entities.base.BaseEntity;

public class UserEntity extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4006887759784227544L;
	@Expose
	@SerializedName("")
	private String name;
	@Expose
	@SerializedName("")
	private String avatar;
	@Expose
	@SerializedName("")
	private String password;
	@Expose
	@SerializedName("")
	private String gender;
	@Expose
	@SerializedName("")
	private String email;
	@Expose
	@SerializedName("")
	private String address;
	@Expose
	@SerializedName("")
	private String phoneNumber;
	@Expose
	@SerializedName("")
	private String recipient;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatar(){
		return avatar;
	}
	public void setAvatar(String avatar){
		this.avatar = avatar;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
		return "UserEntity [name=" + name + ", password=" + password
				+ ", gender=" + gender + ", email=" + email + ", address="
				+ address + ", phoneNumber=" + phoneNumber + ", recipient="
				+ recipient + "]";
	}
	
}
