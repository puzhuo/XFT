package com.pz.xingfutao.net;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageCache {

	public BitmapLruCache(int maxSize) {
		super(maxSize);
		initLocalFileManager();
	}
	
	private void initLocalFileManager(){}

	@Override
	public Bitmap getBitmap(String url) {
		Bitmap bitmap = get(url);
		if(bitmap != null) return bitmap;
		
		return null;
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		put(url, bitmap);
	}

}
