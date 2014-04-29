package com.pz.xingfutao.widget;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.pz.xingfutao.utils.PLog;

public class IndicatorViewPager extends ViewPager{
	
	private boolean autoScroll;
	private long delay;
	
	private boolean dragging;
	
	private ViewPagerIndicator indicator;
	
	private Handler handler;
	private Runnable runnable = new Runnable(){
		@Override
		public void run(){
			if(!dragging){
				if(IndicatorViewPager.this.getCurrentItem() == IndicatorViewPager.this.getAdapter().getCount() - 1){
					IndicatorViewPager.this.setCurrentItem(0, true);
				}else{
					IndicatorViewPager.this.setCurrentItem(IndicatorViewPager.this.getCurrentItem() + 1, true);
				}
			}
			
			handler.postDelayed(this, delay);
		}
	};
	
	public IndicatorViewPager(Context context){
		this(context, null);
	}
	
	public IndicatorViewPager(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public void setAutoScroll(boolean autoScroll, long delay){
		this.autoScroll = autoScroll;
		this.delay = delay;
		
		if(autoScroll){
			if(handler == null){
				handler = new Handler();
			}else{
				handler.removeCallbacks(runnable);
			}
			
			handler.post(runnable);
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event){
		switch(event.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
			dragging = true;
			if(handler != null) handler.removeCallbacks(runnable);
			break;
		case MotionEvent.ACTION_UP:
			dragging = false;
			if(autoScroll){
				if(handler == null) handler = new Handler();
				handler.postDelayed(runnable, delay);
			}
			break;
		}
		
		return super.onInterceptTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		switch(event.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
			dragging = true;
			if(handler != null) handler.removeCallbacks(runnable);
			break;
		case MotionEvent.ACTION_UP:
			dragging = false;
			if(autoScroll){
				if(handler == null) handler = new Handler();
				handler.postDelayed(runnable, delay);
			}
			break;
		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		if(getChildCount() > 0){
			final int length = getChildCount();
			int miniHeight = 0;
			for(int i = 0; i < length; i++){
				View child = getChildAt(i);
				int height = 0;
				if(child instanceof FitWidthImageView){
					float rate = ((FitWidthImageView) child).getScaleRate();
					height = (int) ((float) ((FitWidthImageView) child).getIntrinsicHeight() * rate);
					
				}
				if(height > miniHeight){
					miniHeight = height;
				}
			}
			
			PLog.d("fdewfe", miniHeight + "");
			
			if(MeasureSpec.getSize(heightMeasureSpec) < miniHeight){
				setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), miniHeight);
				PLog.d("asdfsadfa", "fefef");
			}
		}
		
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b){
		super.onLayout(changed, l, t, r, b);
		
		if(indicator != null){
			indicator.layout(0, 0, 100, 10);
		}
	}
}
