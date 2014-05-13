package com.pz.xingfutao.utils;

import com.pz.xingfutao.dao.XFDatabase;

import android.content.Context;

@Deprecated
public class Variables {
	
	private Context context;
	
	private static Variables instance;
	
	private static final int NIL = -1;
	
	private int cartCount = NIL;
	private int favCount = NIL;
	private int orderCount = NIL;
	
	public static Variables getInstance(Context context){
		if(instance == null){
			instance = new Variables(context);
		}
		
		return instance;
	}
	
	private Variables(Context context){
		this.context = context;
	}
	
	public int getCartCount(){
		if(cartCount == NIL){
			cartCount = XFDatabase.getInstance(context).getCartCount();
		}
		
		return cartCount;
	}
	
	public void updateCartCount(int count){
		cartCount = count;
	}
	
	public int getFavCount(){
		if(favCount == NIL){
			favCount = XFDatabase.getInstance(context).getFavCount();
		}
		
		return favCount;
	}
	
	public void updateFavCount(int count){
		favCount = count;
	}
	
	public int getOrderCount(){
		if(orderCount == NIL){
			orderCount = XFDatabase.getInstance(context).getOrderCount();
		}
		
		return orderCount;
	}
	
	public void updateOrderCount(int count){
		orderCount = count;
	}
}
