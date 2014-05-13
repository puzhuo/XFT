package com.pz.xingfutao.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class GestureLock extends RelativeLayout{
	
	private GestureLockView[] lockers;
	
	public static final int MODE_NORMAL = 0;
	public static final int MODE_EDIT = 1;
	
	private int mode = MODE_NORMAL;
	
	private static final int depth = 3;
	
	private int[] defaultGestures = new int[]{0, 1, 2, 5, 8};
	private int[] negativeGestures;
	
	private int[] gesturesContainer;
	private int gestureCursor = 0;
	
	private Path gesturePath;
	
	private int lastX;
	private int lastY;
	private int lastPathX;
	private int lastPathY;
	
	private int blockWidth = 190;
	private int blockGap = 70;
	
	private int gestureWidth;
	
	private Paint paint;
	
	private int unmatchedCount;
	private static final int unmatchedBoundary = 5;
	
	private boolean touchable;
	
	private OnGestureEventListener onGestureEventListener;
	
	public interface OnGestureEventListener{
		public void onBlockSelected(int position);
		public void onGestureEvent(boolean matched);
		public void onUnmatchedExceedBoundary();
	}

	public GestureLock(Context context){
		this(context, null);
	}
	
	public GestureLock(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}
	
	public GestureLock(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		
		negativeGestures = new int[depth * depth];
		for(int i = 0; i < negativeGestures.length; i++) negativeGestures[i] = -1;
		gesturesContainer = negativeGestures.clone();
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(20);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		
		unmatchedCount = 0;
		
		touchable = true;
	}
	
	public void setTouchable(boolean touchable){
		this.touchable = touchable;
	}
	
	public void rewindUnmatchedCount(){
		unmatchedCount = 0;
	}
	
	public void setOnGestureEventListener(OnGestureEventListener onGestureEventListener){
		this.onGestureEventListener = onGestureEventListener;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		
		int length = width > height ? height : width;
		
		//add LockView to Layout and sort
		if(lockers == null){
			blockWidth = (length - (blockGap * (depth - 1))) / depth;
			gestureWidth = blockWidth * depth + blockGap * (depth - 1);
			lockers = new GestureLockView[depth * depth];
			for(int i = 0; i < lockers.length; i++){
				lockers[i] = new GestureLockView(getContext());
				lockers[i].setId(i + 1);
				
				RelativeLayout.LayoutParams lockerParams = new RelativeLayout.LayoutParams(blockWidth, blockWidth);
				
				//skip the first vertical line
				if(i % depth != 0) lockerParams.addRule(RelativeLayout.RIGHT_OF, lockers[i - 1].getId());
				//skip the first horizontal line
				if(i > (depth - 1)) lockerParams.addRule(RelativeLayout.BELOW, lockers[i - depth].getId());
				
				int rightMargin = 0;
				int bottomMargin = 0;
				
				//skip the last vertical line
				if((i + 1) % depth != 0) rightMargin = blockGap;
				//skip the last horizontal line
				if(i < depth * (depth - 1)) bottomMargin = blockGap;
				
				lockerParams.setMargins(0, 0, rightMargin, bottomMargin);
				
				addView(lockers[i], lockerParams);
				
				lockers[i].setMode(GestureLockView.MODE_NORMAL);
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		if(touchable){
			switch(event.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
				for(int i = 0; i < getChildCount(); i++){
					View c = getChildAt(i);
					if(c instanceof GestureLockView){
						((GestureLockView) c).setMode(GestureLockView.MODE_NORMAL);
						((GestureLockView) c).setArrow(-1);
					}
				}
				
				gesturePath = null;
				
				lastX = (int) event.getX();
				lastY = (int) event.getY();
				lastPathX = lastX;
				lastPathY = lastY;
				
				paint.setColor(0x66FFFFFF);
				
				break;
			case MotionEvent.ACTION_MOVE:
				
				if(gesturesContainer[gesturesContainer.length - 1] != -1){
					lastX = lastPathX;
					lastY = lastPathY;
				}else{
					lastX = (int) event.getX();
					lastY = (int) event.getY();
				}
				
				int cId = calculateChildIdByCoords(lastX, lastY);
				
				View child = findViewById(cId + 1);
				boolean checked = false;
				for(int id : gesturesContainer){
					if(id == cId){
						checked = true;
						break;
					}
				}
				
				if(child != null && child instanceof GestureLockView && checkChildInCoords(lastX, lastY, child)){
					((GestureLockView) child).setMode(GestureLockView.MODE_SELECTED);
					
					if(!checked){
						int checkedX = child.getLeft() + child.getWidth() / 2;
						int checkedY = child.getTop() + child.getHeight() / 2;
						if(gesturePath == null){
							gesturePath = new Path();
							gesturePath.moveTo(checkedX, checkedY);
						}else{
							gesturePath.lineTo(checkedX, checkedY);
						}
						gesturesContainer[gestureCursor] = cId;
						gestureCursor++;
						
						lastPathX = checkedX;
						lastPathY = checkedY;
						
						if(onGestureEventListener != null) onGestureEventListener.onBlockSelected(cId);
					}
				}
				
				invalidate();
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				
				if(gesturesContainer[0] != -1){
					boolean matched = false;
					for(int j = 0; j < gesturesContainer.length; j++){
						if(j < defaultGestures.length){
							if(gesturesContainer[j] == defaultGestures[j]){
								matched = true;
							}else{
								matched = false;
								break;
							}
						}else{
							if(gesturesContainer[j] != -1){
								matched = false;
							}
						}
					}
					
					if(!matched && mode != MODE_EDIT){
						unmatchedCount++;
						//paint.setColor(0x66FF0000);
						for(int k = 0; k < gesturesContainer.length; k++){
							View selectedChild = findViewById(gesturesContainer[k] + 1);
							if(selectedChild != null && selectedChild instanceof GestureLockView){
								((GestureLockView) selectedChild).setMode(GestureLockView.MODE_ERROR);
								
								if(k < gesturesContainer.length - 1 && gesturesContainer[k + 1] != -1){
									View nextChild = findViewById(gesturesContainer[k + 1] + 1);
									if(nextChild != null){
										int dx = nextChild.getLeft() - selectedChild.getLeft();
										int dy = nextChild.getTop() - selectedChild.getTop();
										
										int angle = (int) Math.toDegrees(Math.atan2(dy, dx)) + 90;
										((GestureLockView) selectedChild).setArrow(angle);
									}
								}
							}
						}
					}else{
						unmatchedCount = 0;
					}
					
					
					
					if(onGestureEventListener != null){
						onGestureEventListener.onGestureEvent(matched);
						if(unmatchedCount >= unmatchedBoundary){
							onGestureEventListener.onUnmatchedExceedBoundary();
							unmatchedCount = 0;
						}
					}
				}
				
				gestureCursor = 0;
				gesturesContainer = negativeGestures.clone();
				
				lastX = lastPathX;
				lastY = lastPathY;
				
				invalidate();
				
				break;
			}
		}
		
		return true;
	}
	
	public void setCorrectGesture(int[] correctGestures){
		defaultGestures = correctGestures;
	}
	
	public void setMode(int mode){
		this.mode = mode;
	}
	
	public void rewind(){
		for(int i = 0; i < getChildCount(); i++){
			View c = getChildAt(i);
			if(c instanceof GestureLockView){
				((GestureLockView) c).setMode(GestureLockView.MODE_NORMAL);
				((GestureLockView) c).setArrow(-1);
			}
		}
		
		gesturePath = null;
		
		lastX = 0;
		lastY = 0;
		lastPathX = lastX;
		lastPathY = lastY;
		
		paint.setColor(0x66FFFFFF);
	}
	
	private int calculateChildIdByCoords(int x, int y){
		if(x >= 0 && x <= gestureWidth && y >= 0 && y <= gestureWidth){
			int rowX = (int) (((float) x / (float) gestureWidth) * depth);
			int rowY = (int) (((float) y / (float) gestureWidth) * depth);
			
			return rowX + (rowY * depth);
		}
		
		return -1;
	}
	
	private boolean checkChildInCoords(int x, int y, View child){
		if(child != null){
			int centerX = child.getLeft() + child.getWidth() / 2;
			int centerY = child.getTop() + child.getHeight() / 2;
			
			int dx = centerX - x;
			int dy = centerY - y;
			
			int radius = child.getWidth() > child.getHeight() ? child.getHeight() : child.getWidth();
			radius /= 2;
			if(dx * dx + dy * dy < radius * radius) return true;
		}
		
		return false;
	}
	
	@Override
	public void dispatchDraw(Canvas canvas){
		super.dispatchDraw(canvas);
		
		if(gesturePath != null){
			 canvas.drawPath(gesturePath, paint);
		}
		
		if(gesturesContainer[0] != -1) canvas.drawLine(lastPathX, lastPathY, lastX, lastY, paint);
		
		
	}
}
