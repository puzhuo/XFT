package com.pz.xingfutao.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Cache.Entry;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseTitleFragment;

public class NetworkHandler {
	
	private Context context;
	private static NetworkHandler instance;
	
	private RequestQueue jsonQueue;
	
	private RequestQueue imageQueue;
	private ImageLoader imageLoader;
	private LruCache<String, Bitmap> lruCache;
	private ImageCache imageCache;
	
	private class NetworkListener<T> implements Listener<T>{
		private Listener<T> listener;
		private BaseTitleFragment errorHost;
		
		public NetworkListener(BaseTitleFragment errorHost, Listener<T> listener){
			this.errorHost = errorHost;
			this.listener = listener;
			if(errorHost != null) errorHost.progressBarToggle(true);
		}

		@Override
		public void onResponse(T response) {
			if(listener != null) listener.onResponse(response);
			if(errorHost != null) errorHost.progressBarToggle(false);
		}
		
	}
	
	private static final float INTRINSIC_RATE = 2.5F;
	
	private NetworkHandler(Context context){
		this.context = context;
		jsonQueue = Volley.newRequestQueue(context.getApplicationContext());
		imageQueue = Volley.newRequestQueue(context.getApplicationContext());
		
		lruCache = new LruCache<String, Bitmap>(50);
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
	
	public void jsonRequest(Map<String, String> params, String url, final Listener<JSONObject> listener, final BaseTitleFragment errorHost, boolean cachePrimaryPolicy){
		if(errorHost != null) errorHost.progressBarToggle(true);
		
		Entry entry = jsonQueue.getCache().get(url);
		
		if(entry != null){
			if(listener != null){
				try {
					listener.onResponse(new JSONObject(new String(jsonQueue.getCache().get(url).data)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(errorHost != null) errorHost.progressBarToggle(false);
			if(!cachePrimaryPolicy)
				addToJsonWithPost(url, params, errorHost, listener);
		}else{
			addToJsonWithPost(url, params, errorHost, listener);
		}
	}
	
	public void jsonRequest(Map<String, String> params, String url, final Listener<JSONObject> listener, final BaseTitleFragment errorHost){
		jsonRequest(params, url, listener, errorHost, false);
	}
	
	public void jsonRequest(int method, String url, final Listener<JSONObject> listener, final BaseTitleFragment errorHost, boolean cachePrimaryPolicy){
		if(errorHost != null) errorHost.progressBarToggle(true);
		
		Entry entry = jsonQueue.getCache().get(url);
		
		if(entry != null){
			if(listener != null){
				try {
					listener.onResponse(new JSONObject(new String(jsonQueue.getCache().get(url).data)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			if(errorHost != null) errorHost.progressBarToggle(false);
			if(!cachePrimaryPolicy)
				addToJsonQueue(method, url, errorHost, listener);
		}else{
			addToJsonQueue(method, url, errorHost, listener);
		}
	}
	
	public void jsonRequest(int method, String url, final Listener<JSONObject> listener, final BaseTitleFragment errorHost){
		jsonRequest(method, url, listener, errorHost, false);
	}
	
	public void stringRequest(Map<String, String> params, String url, final Listener<String> listener, final BaseTitleFragment errorHost, boolean cachePrimaryPolicy){
		if(errorHost != null) errorHost.progressBarToggle(true);
		
		Entry entry = jsonQueue.getCache().get(url);
		
		if(entry != null){
			if(listener != null) listener.onResponse(new String(jsonQueue.getCache().get(url).data));
			if(errorHost != null) errorHost.progressBarToggle(false);
			if(!cachePrimaryPolicy)
				addToStringWithPost(url, params, errorHost, listener);
		}else{
			addToStringWithPost(url, params, errorHost, listener);
		}
	}
	
	public void stringRequest(Map<String, String> params, String url, final Listener<String> listener, final BaseTitleFragment errorHost){
		stringRequest(params, url, listener, errorHost, false);
	}
	
	public void stringRequest(int method, String url, final Listener<String> listener, final BaseTitleFragment errorHost, boolean cachePrimaryPolicy){
		if(errorHost != null) errorHost.progressBarToggle(true);
		
		Entry entry = jsonQueue.getCache().get(url);
		
		if(entry != null){
			if(listener != null) listener.onResponse(new String(jsonQueue.getCache().get(url).data));
			if(errorHost != null) errorHost.progressBarToggle(false);
			if(!cachePrimaryPolicy)
				addToStringQueue(method, url, errorHost, listener);
		}else{
			addToStringQueue(method, url, errorHost, listener);
		}
	}
	
	public void stringRequest(int method, String url, final Listener<String> listener, final BaseTitleFragment errorHost){
		
		stringRequest(method, url, listener, errorHost, false);
		
	}
	
	public void imageRequest(String url, ImageView imageView){
		
		url = encodeCH(url);
		
		ImageListener listener = ImageLoader.getImageListener(imageView, R.drawable.pre_load_image, R.drawable.pre_load_image);
		imageLoader.get(url, listener);
	}
	
	public void networkImageSpannable(String url, final SpannableString ss, final TextView tv, final int start, final int end){
		
		url = encodeCH(url);
		
		if(url.startsWith("//")){
			url = "http:" + url;
		}
		
		ImageRequest imageRequest = new ImageRequest(url, new Listener<Bitmap>(){
			@Override
			public void onResponse(Bitmap bitmap){
				
				int tWidth = tv.getMeasuredWidth() - tv.getPaddingLeft() - tv.getPaddingRight();
				Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
				if(drawable.getIntrinsicWidth() * INTRINSIC_RATE > tWidth){
					float rate = (float) tWidth / (float) drawable.getIntrinsicWidth();
					
					drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * rate), (int) (drawable.getIntrinsicHeight() * rate));
				}else{
					drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * INTRINSIC_RATE), (int) (drawable.getIntrinsicHeight() * INTRINSIC_RATE));
				}
				ss.setSpan(new ImageSpan(drawable, DynamicDrawableSpan.ALIGN_BOTTOM), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				
				tv.setText(ss);
			}
		}, 0, 0, Config.ARGB_8888, null);
		imageRequest.shouldCache();
		
		imageQueue.add(imageRequest);
	}
	
	public void wipeJsonCache(){
		jsonQueue.getCache().clear();
	}
	
	public void wipeImageCache(){
		imageQueue.getCache().clear();
	}
	
	
	public void addToStringQueue(int method, String url, BaseTitleFragment errorHost, Listener<String> listener){
		
		jsonQueue.add(new XFStringRequest(context, method, url, new NetworkListener<String>(errorHost, listener), errorHost == null ? null : errorHost.errorListener));
	}
	
	public void addToJsonQueue(int method, String url, BaseTitleFragment errorHost, Listener<JSONObject> listener){
		jsonQueue.add(new XFJsonObjectRequest(context, method, url, new NetworkListener<JSONObject>(errorHost, listener), errorHost == null ? null : errorHost.errorListener));
	}
	
	public void addToStringWithPost(String url, Map<String, String> params, BaseTitleFragment errorHost, Listener<String> listener){
		jsonQueue.add(new XFStringRequest(context, Method.POST, url, params, new NetworkListener<String>(errorHost, listener), errorHost == null ? null : errorHost.errorListener));
	}
	
	public void addToJsonWithPost(String url, Map<String, String> params, BaseTitleFragment errorHost, Listener<JSONObject> listener){
		jsonQueue.add(new XFJsonObjectRequest(context, Method.POST, url, params, new NetworkListener<JSONObject>(errorHost, listener), errorHost == null ? null : errorHost.errorListener));
	}
	
	private static String encodeCH(String url){

		if(url != null){
			Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher matcher = pattern.matcher(url);
			if(matcher.find()){
				int nameIndex = url.lastIndexOf("/") + 1;
				String name = url.substring(nameIndex);
				String urlPre = url.substring(0, nameIndex);
				try {
					return urlPre + URLEncoder.encode(name, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		
		return url;
	}
	
}
