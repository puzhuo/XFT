package com.pz.xingfutao.entities;

import java.io.Serializable;

public class SettingsMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1938712961122714795L;
	
	public static final int MODE_RADIO_BUTTON = 0;
	public static final int MODE_ARROW_WITH_EXTRA = 1;
	public static final int MODE_DESC = 2;
	public static final int MODE_DIV = 3;
	public static final int MODE_EXIT = 4;
	
	private String key;
	private String title;
	private String extra;
	private int type;
	
	public int getType(){
		return type;
	}
	
	public SettingsMap setType(int type){
		this.type = type;
		
		return this;
	}
	
	public String getKey() {
		return key;
	}
	public SettingsMap setKey(String key) {
		this.key = key;
		
		return this;
	}
	public String getTitle() {
		return title;
	}
	public SettingsMap setTitle(String title) {
		this.title = title;
		
		return this;
	}
	public String getExtra() {
		return extra;
	}
	public SettingsMap setExtra(String extra) {
		this.extra = extra;
		
		return this;
	}
	
	
	@Override
	public String toString() {
		return "SettingsMap [key=" + key + ", title=" + title + ", extra="
				+ extra + "]";
	}
	
	
}
