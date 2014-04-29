package com.pz.xingfutao.widget;

/*
 *   _    ________    __    __    ________    ________    __    __    ________    ___   __ _
 *  /\\--/\______ \--/\ \--/\ \--/\  _____\--/\  ____ \--/\ \--/\ \--/\  _____\--/\  \-/\ \\\
 *  \ \\ \/_____/\ \ \ \ \_\_\ \ \ \ \____/_ \ \ \__/\ \ \ \ \_\ \ \ \ \ \____/_ \ \   \_\ \\\
 *   \ \\       \ \ \ \ \  ____ \ \ \  _____\ \ \  ____ \ \_ \ \_\ \  \ \  _____\ \ \  __   \\\
 *    \ \\       \ \ \ \ \ \__/\ \ \ \ \____/_ \ \ \__/\ \  \_ \ \ \   \ \ \____/_ \ \ \ \_  \\\
 *     \ \\       \ \_\ \ \_\ \ \_\ \ \_______\ \ \_\ \ \_\   \_ \_\    \ \_______\ \ \_\_ \__\\\
 *      \ \\       \/_/  \/_/  \/_/  \/_______/  \/_/  \/_/     \/_/     \/_______/  \/_/ \/__/ \\
 *       \ \\----------------------------------------------------------------------------------- \\
 *        \//                                                                                   \//
 *
 * 
 *
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;

import com.pz.xingfutao.R;
import com.pz.xingfutao.graphics.RoundDrawable;

public class ViewPagerIndicator extends View {

	private Paint paint;
	private PorterDuffXfermode porterDuffMode;

	private int width;
	private int height;

	private int dividerWidth;
	private int foregroundColor;
	private int backgroundColor;

	private int indicatorWidth;
	private int indicatorCount;
	private int heightMeasurement;

	private int foregroundOffset;
	
	private boolean backgrouneEnabled;
	private RoundDrawable backgroundDrawable;

	public ViewPagerIndicator(Context context){
		this(context, null);
	}

	public ViewPagerIndicator(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		porterDuffMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

		width = -1;
		height = -1;
		indicatorWidth = -1;
		indicatorCount = -1;

		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);

		dividerWidth = ta.getDimensionPixelOffset(R.styleable.ViewPagerIndicator_dividerWidth, 5);
		foregroundColor = ta.getColor(R.styleable.ViewPagerIndicator_foregroundColor, 0xFFFFFFFF);
		backgroundColor = ta.getColor(R.styleable.ViewPagerIndicator_backgroundColor, 0xFF999999);

		ta.recycle();

		if(foregroundColor >> 24 == 0) foregroundColor |= 0xFF << 24;
		if(backgroundColor >> 24 == 0) backgroundColor |= 0xFF << 24;

		//disable Hardware acceleration to prevent canvas to draw black color instead of alpha
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}
	
	public void setDividerWidth(int width){
		dividerWidth = width;
		doMeasureWork();
	}
	
	public void setForegroundColor(int color){
		foregroundColor = color;
	}
	
	public void setBackgroundColor(int color){
		backgroundColor = color;
	}
	
	public void setBackgroundEnabled(boolean enabled){
		backgrouneEnabled = enabled;
		if(enabled){
			backgroundDrawable = new RoundDrawable(0x40000000);
			setBackgroundDrawable(backgroundDrawable);
		}else{
			setBackgroundDrawable(null);
		}
	}

	public void setViewPager(ViewPager viewPager){
	    if(viewPager != null){

	    	if(viewPager.getAdapter() != null){
	    		indicatorCount = viewPager.getAdapter().getCount();
	    	}

	    	doMeasureWork();

	    	viewPager.setOnPageChangeListener(new OnPageChangeListener(){

				@Override
				public void onPageScrollStateChanged(int state) {}

				@Override
				public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
					foregroundOffset = (int) ((position * (indicatorWidth + dividerWidth)) + ((indicatorWidth + dividerWidth) * positionOffset));
					invalidate();
				}

				@Override
				public void onPageSelected(int position) {}

    		});
	    }
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);

		heightMeasurement = heightMeasureSpec;

        doMeasureWork();
        
	}

	private void doMeasureWork(){
		if(width != -1 && height != -1 && indicatorCount != -1 && indicatorWidth == -1){
			int singleWidth = (width - (dividerWidth * (indicatorCount - 1))) / indicatorCount;
			if(MeasureSpec.getMode(heightMeasurement) != MeasureSpec.UNSPECIFIED){
				if(height < singleWidth){
					indicatorWidth = height;
				}else{
					indicatorWidth = singleWidth;
				}
			}else{
				indicatorWidth = singleWidth;
			}
		}

		invalidate();

	}

	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);

		paint.setXfermode(null);
		
		paint.setColor(backgroundColor);
		for(int i = 0; i < indicatorCount; i++){
			canvas.drawCircle(indicatorWidth / 2 + (dividerWidth + indicatorWidth) * i, height / 2, indicatorWidth / 2, paint);
		}

		paint.setColor(foregroundColor);
		paint.setXfermode(porterDuffMode);
		canvas.drawRect(foregroundOffset, 0, foregroundOffset + indicatorWidth, height, paint);
	}
}