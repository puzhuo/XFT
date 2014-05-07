package com.pz.xingfutao.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.utils.PLog;
import com.pz.xingfutao.widget.PullWidget.OnStateChangedListener;

public class PullRefreshWidget extends LinearLayout implements OnStateChangedListener{
	
	private static final String TAG = "PullRefreshWidget";
	
	private static final int MODE_REFRESHING = 0;
	private static final int MODE_IDLE = 1;
	private static final int MODE_DRAGGING = 2;
	private static final int MODE_REFRESH_START = 3;
	
	private int mode = MODE_IDLE;
	
	private boolean circling;
	private boolean pullable;
	
	private View mHeadView;
	private AdapterView<?> mAdapterView;
	private ScrollView mScrollView;
	private View mNormalView;
	private PullWidget pullWidget;
	
	private float dx,dy;
	private int moveDistance;
	private int headOriginalHeight;
	
	private Handler handler;
	private HideRunnable hideRunnable;
	private MoveRunnable moveRunnable;
	private HeadMoveRunnable headMoveRunnable;
	
	private OnRefreshListener onRefreshListener;
	
	private class HideRunnable implements Runnable{
		int startY;
		
		public HideRunnable(int startY){
			stopMovement();
			this.startY = startY;
		}
		
		@Override
		public void run(){
			startY += (0 - startY) * 0.23F;
			
			drag(startY);
			
			if(startY > 0){
				handler.postDelayed(this, 12);
			}
		}
	}
	
	private class MoveRunnable implements Runnable{
		int startY;
		
		public MoveRunnable(int startY){
			stopMovement();
			this.startY = startY;
		}
		
		@Override
		public void run(){
			startY += (headOriginalHeight - startY) * 0.5F;
			
			pullWidget.setHeight(startY - headOriginalHeight);
			setChildHeight(mHeadView, startY);
			
			if(startY != headOriginalHeight){
				handler.postDelayed(this, 12);
			}else{
				switch(mode){
				case MODE_REFRESHING:
				case MODE_REFRESH_START:
					pullWidget.circling();
					break;
				case MODE_DRAGGING:
					smoothHideHeadView();
					break;
				}
			}
		}
	}
	
	public class HeadMoveRunnable implements Runnable{
		LayoutParams params;
		
		public HeadMoveRunnable(){
			params = (LayoutParams) mHeadView.getLayoutParams();
		}
		
		@Override
		public void run(){
			params.topMargin += (-headOriginalHeight - params.topMargin) * 0.5F;
			
			mHeadView.setLayoutParams(params);
			
			if(params.topMargin != -headOriginalHeight){
				handler.postDelayed(this, 12);
			}else{
				mode = MODE_IDLE;
			}
		}
	}
	
	public interface OnRefreshListener{
		public void onRefresh();
	}

	public PullRefreshWidget(Context context){
		super(context);
		init(context);
	}
	
	public PullRefreshWidget(Context context, AttributeSet attrs){
		super(context, attrs);
		init(context);
	}
	
	private void init(Context context){
		setOrientation(LinearLayout.VERTICAL);
		LayoutInflater inflater = LayoutInflater.from(context);
		mHeadView = inflater.inflate(R.layout.head_pullrefresh, this, false);
		measureChild(mHeadView);
		pullWidget = (PullWidget) mHeadView.findViewById(R.id.pull_widget);
		pullWidget.setOnStateChangeListener(this);
		
		headOriginalHeight = mHeadView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, headOriginalHeight);
		params.topMargin = -headOriginalHeight;
		addView(mHeadView, params);
		
		handler = new Handler();
		
		pullable = true;
	}
	
	@Override
	public void onFinishInflate(){
		super.onFinishInflate();
		
		initContent();
	}
	
	public void setPullable(boolean pullable){
		this.pullable = pullable;
	}
	
	private void initContent(){
		int count = getChildCount();
		if(count != 2) throw new ArrayIndexOutOfBoundsException("this widget contain one child view at most and must contain one.");
		
		View view = getChildAt(1);
		if(view instanceof AdapterView<?>){
			mAdapterView = (AdapterView<?>) view;
		}else if(view instanceof ScrollView){
			mScrollView = (ScrollView) view;
		}else{
			mNormalView = view;
		}
	}
	
	private void measureChild(View child){
		ViewGroup.LayoutParams params = child.getLayoutParams();
		if(null == params){
			params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		}
		
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, params.width);
		int childHeightSpec;
		if(params.height > 0){
			childHeightSpec = MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY);
		}else{
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		
		child.measure(childWidthSpec, childHeightSpec);
	}
	
	private void setChildHeight(View child, int height){
		ViewGroup.LayoutParams params = child.getLayoutParams();
		params.height = height;
		child.setLayoutParams(params);
	}
	
	private void setHeadMargin(int height){
		LayoutParams params = (LayoutParams) mHeadView.getLayoutParams();
		params.topMargin = height - headOriginalHeight;
		
		mHeadView.setLayoutParams(params);
	}
	
	private boolean shouldRefreshStart(){
		if(null != mNormalView) return true;
		if(null != mScrollView){
			if(mScrollView.getChildAt(0).getScrollY() == 0) return true;
		}
		if(null != mAdapterView && mAdapterView.getChildAt(0) != null){
			int top = mAdapterView.getChildAt(0).getTop();
			int padding = mAdapterView.getPaddingTop();
			if(mAdapterView.getFirstVisiblePosition() == 0){
				if(top == 0 || Math.abs(top - padding) <= 3){
					PLog.d(TAG, "top-padding:" + Math.abs(top - padding));
					return true;
				}
			}
		}
		
		return false;
	}
	
	private void smoothToOriginalSpot(int y){
		moveRunnable = new MoveRunnable(y);
		
		handler.post(moveRunnable);
	}
	
	private void stopMovement(){
		handler.removeCallbacks(moveRunnable);
		handler.removeCallbacks(hideRunnable);
		handler.removeCallbacks(headMoveRunnable);
	}
	
	private void smoothHideHeadView(){
		headMoveRunnable = new HeadMoveRunnable();
		
		handler.post(headMoveRunnable);
	}
	
	public void setOnRefreshListener(OnRefreshListener onRefreshListener){
		this.onRefreshListener = onRefreshListener;
	}
	
	public OnRefreshListener getOnRefreshListener(){
		return onRefreshListener;
	}
	
	public void onRefreshComplete(){
		pullWidget.stopCirclingAndReturn();
		mode = MODE_IDLE;
		moveDistance = 0;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event){
		
		switch(event.getAction() & MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:
			dx = event.getRawX();
			dy = event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			int mx = (int) (event.getRawX() - dx);
			int my = (int) (event.getRawY() - dy);
			
			if(pullable){
				if(Math.abs(mx) < Math.abs(my)){
					if(shouldRefreshStart() && my > 0){
						PLog.d(TAG, "shouldRefreshStart");
						return true;
					}
				}
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			break;
		}
		
		return super.onInterceptTouchEvent(event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		PLog.d(TAG, "MODE:" + mode);
		switch(event.getAction() & MotionEvent.ACTION_MASK){
		case MotionEvent.ACTION_DOWN:
			stopMovement();
			moveDistance = 0;
			dx = event.getX();
			dy = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			
			switch(mode){
			case MODE_IDLE:
				moveDistance = (int) (event.getY() - dy);
				PLog.d(TAG, "IDLE");
				if(shouldRefreshStart() && mode != MODE_REFRESHING && moveDistance > 0 && !circling){
					mode = MODE_DRAGGING;
					handler.removeCallbacks(hideRunnable);
					handler.removeCallbacks(headMoveRunnable);
					handler.removeCallbacks(moveRunnable);
				}
			    break;
			case MODE_DRAGGING:
				moveDistance = (int) (event.getY() - dy);
				if(moveDistance < 0){
					moveDistance = 0;
				}
				PLog.d(TAG, "DRAGGING");
				if(pullWidget.isExceedMaximumHeight()){
					mode = MODE_REFRESH_START;
				}else{
					drag(moveDistance);
				}
				break;
			case MODE_REFRESH_START:
				smoothToOriginalSpot(moveDistance);
				mode = MODE_REFRESHING;
				if(null != onRefreshListener) onRefreshListener.onRefresh();
				break;
			case MODE_REFRESHING:
				circling = true;
				break;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			circling = false;
			if(mode == MODE_DRAGGING) {
				smoothHideWidget(moveDistance);
			}
			break;
		}
		return true;
	}
	
	private void smoothHideWidget(int moveDistance){
		hideRunnable = new HideRunnable(moveDistance);
		handler.post(hideRunnable);
	}
	
	private void drag(int moveDistance){
		if(moveDistance <= headOriginalHeight){
			setHeadMargin((int) moveDistance);
			pullWidget.setHeight(0);
			setChildHeight(mHeadView, headOriginalHeight);
		}else{
			setHeadMargin(headOriginalHeight);
			pullWidget.setHeight((int) moveDistance - headOriginalHeight);
			setChildHeight(mHeadView, (int) moveDistance);
			
		}
	}

	@Override
	public void onCirclingFullyStop() {
		smoothHideHeadView();
	}

	@Override
	public void onPullFullyStop() {
		
	}
}
