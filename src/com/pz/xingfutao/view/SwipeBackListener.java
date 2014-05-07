package com.pz.xingfutao.view;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

import com.pz.xingfutao.utils.PLog;

public class SwipeBackListener implements OnTouchListener{
	
	private static final int MIN_WIDTH = 10;
	
	private static final int MODE_IDLE = 0;
	private static final int MODE_DRAGGING_START = 1;
	private static final int MODE_DRAGGING = 2;
	
	private int mode = MODE_IDLE;
	
	private int initX;
	private int initY;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		PLog.d("asdf", "f3f3");
		if(v instanceof FrameLayout){
			switch(event.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
				if(event.getX() <= MIN_WIDTH){
					initX = (int) event.getX();
					initY = (int) event.getY();
					return true;
				}else{
					return false;
				}
			case MotionEvent.ACTION_MOVE:
				switch(mode){
				case MODE_IDLE:
					if(Math.abs(event.getX() - initX) > Math.abs(event.getY() - initY)){
						mode = MODE_DRAGGING_START;
					}else{
						return false;
					}
					break;
				case MODE_DRAGGING_START:
					//TODO do prepare stuff
					break;
				case MODE_DRAGGING:
					FrameLayout.LayoutParams params = (LayoutParams) v.getLayoutParams();
					params.leftMargin = (int) (event.getX() - initX);
					params.rightMargin = -params.leftMargin;
					
					v.setLayoutParams(params);
					
					PLog.d("x:" + params.leftMargin, "y" + params.rightMargin);
					
					break;
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				break;
			}
		}
		
		
		return false;
	}

}
