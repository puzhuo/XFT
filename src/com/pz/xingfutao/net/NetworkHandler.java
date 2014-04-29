package com.pz.xingfutao.net;

import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pz.xingfutao.R;

public class NetworkHandler {
	
	private Context context;
	private static NetworkHandler instance;
	
	private RequestQueue jsonQueue;
	
	private RequestQueue imageQueue;
	private ImageLoader imageLoader;
	private LruCache<String, Bitmap> lruCache;
	private ImageCache imageCache;
	
	private NetworkHandler(Context context){
		this.context = context;
		jsonQueue = Volley.newRequestQueue(context.getApplicationContext());
		imageQueue = Volley.newRequestQueue(context.getApplicationContext());
		
		lruCache = new LruCache<String, Bitmap>(20);
		imageCache = new ImageCache(){
			@Override
			public void putBitmap(String key, Bitmap value){
				lruCache.put(key, value);
			}
			
			@Override
			public Bitmap getBitmap(String key){
				return lruCache.get(key);
			}
		};
		
		imageLoader = new ImageLoader(imageQueue, imageCache);
	}
	
	public static NetworkHandler getInstance(Context context){
		if(instance == null){
			instance = new NetworkHandler(context);
		}
		
		return instance;
	}
	
	public void jsonRequest(int method, String url, Listener<JSONObject> listener, ErrorListener errorListener){
		jsonQueue.add(new JsonObjectRequest(method, url, null, listener, errorListener));
	}
	
	public void imageRequest(String url, ImageView imageView){
		
		ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.ic_launcher, R.drawable.ic_launcher);
		imageLoader.get(url, listener);
	}
}
