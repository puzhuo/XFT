package com.pz.xingfutao.widget;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.utils.SystemMeasurementUtil;

public class AddToCart {
	
	private ImageView cart;
	
	private int imageResourceId;
	
	private View fromView;
	private View toView;
	
	private int toX;
	private int toY;
	private int toWidth;
	private int toHeight;
	
	private Handler handler;
	private Runnable runnable = new Runnable(){
		@Override
		public void run(){
			WindowManager.LayoutParams p = (WindowManager.LayoutParams) cart.getLayoutParams();
			int x = p.x;
			int y = p.y;
			int width = p.width;
			int height = p.height;
			
			if(Math.abs(x - toX) > 1 || Math.abs(y - toY) > 1){
				x += (toX - x) * 0.2F;
				y += (toY - y) * 0.1F;
				width += (toWidth - width) * 0.4F;
				height += (toHeight - height) * 0.4F;
				
				p.x = x;
				p.y = y;
				p.width = width;
				p.height = height;
				
				windowManager.updateViewLayout(cart, p);
				
				handler.postDelayed(this, 12);
			}else{
				PLog.d("finish", "done");
				onDestroy();
			}
		}
	};
	
	private WindowManager windowManager;
	
	public AddToCart(View from, View to){
		if(from == null || to == null) throw new NullPointerException("arguments must not be null");
		fromView = from;
		toView = to;
	}
	
	public AddToCart(View from, View to, int imageResourceId){
		this(from, to);
		
        this.imageResourceId = imageResourceId;
        
        toX = to.getLeft();
        toY = to.getTop() + SystemMeasurementUtil.getStatusBarHeight(from.getContext());
        toWidth = to.getWidth();
        toHeight = to.getHeight();
		
		windowManager = (WindowManager) from.getContext().getSystemService(Context.WINDOW_SERVICE);
	}
	
	public void performAdd(){
		
		if(cart != null){
			windowManager.removeView(cart);
			cart = null;
		}
		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
		params.format = PixelFormat.RGBA_8888;
		params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
		params.gravity = Gravity.LEFT | Gravity.TOP;
		params.x = fromView.getLeft();
		params.y = fromView.getTop();
		params.width = fromView.getWidth();
		params.height = fromView.getHeight();
		
		cart = new ImageView(fromView.getContext());
		cart.setImageResource(imageResourceId);
		
		windowManager.addView(cart, params);
		
		handler = new Handler();
		
		handler.post(runnable);
	}
	
	public void onDestroy(){
		if(cart != null){
			if(handler != null) handler.removeCallbacks(runnable);
			//windowManager.removeView(cart);
			windowManager.removeViewImmediate(cart);
			cart = null;
			PLog.d("destroy", "done");
		}
	}
}
