package com.pz.xingfutao.utils;

public class Renderer {

	static{
		System.loadLibrary("render");
	}
	
	public static native String getBaseUrl();
	public static native String getBaseForumUrl();
	public static native String render(String rawString);
	public static native String wipe(String renderedString);
	public static native String md5(String rawString);
}
