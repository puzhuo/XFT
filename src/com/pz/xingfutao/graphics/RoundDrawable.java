package com.pz.xingfutao.graphics;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class RoundDrawable extends Drawable {
	
	protected int left;
	protected int top;
	protected int right;
	protected int bottom;
	
	protected int width;
	protected int height;
	
	protected int centerX;
	protected int centerY;
	
	protected RectF bound;
	
	private int corner;
	private int backgroundColor;
	
	protected Paint paint;
	
	public RoundDrawable(int color){
		backgroundColor = color;
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		
	}
	
	@Override
	public void setBounds(int left, int top, int right, int bottom){
		super.setBounds(left, top, right, bottom);
		
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
		
		width = right - left;
		height = bottom - top;
		
		centerX = width / 2;
		centerY = height / 2;
		
		corner = centerY;
		
		bound  = new RectF(left, top, right, bottom);
	}

	@Override
	public void draw(Canvas canvas) {
		paint.setColor(backgroundColor);
		canvas.drawRoundRect(bound, corner, corner, paint);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSPARENT;
	}

	@Override
	public void setAlpha(int alpha) {}

	@Override
	public void setColorFilter(ColorFilter cf) {}

}
