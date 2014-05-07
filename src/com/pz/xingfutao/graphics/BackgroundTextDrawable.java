package com.pz.xingfutao.graphics;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils.TruncateAt;

public class BackgroundTextDrawable extends Drawable{

	private String source;
	private int textColor;
	private int corner;
	private int backgroundColor;
	
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	private int left;
	private int top;
	private int right;
	private int bottom;
	
	private RectF bound;
	
	private TextPaint paint;
	
	private StaticLayout layout;
	
	private Rect intrinsicBound;
	
	public BackgroundTextDrawable(String source, int textColor, int backgroundColor, int corner) {
		this.source = source;
		this.textColor = textColor;
		this.backgroundColor = backgroundColor;
		this.corner = corner;
		
		paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
		bound = new RectF();
		intrinsicBound = new Rect();
		
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
		
		bound.left = left;
		bound.right = right;
		bound.top = top;
		bound.bottom = bottom;
		
		paint.setTextSize(height * 0.8F);
		paint.getTextBounds(source, 0, source.length(), intrinsicBound);
		layout = new StaticLayout(source, 0, source.length(), paint, width, Alignment.ALIGN_CENTER, 1.0F, 0.0F, true, TruncateAt.END, width);
		
	}
	
	public void draw(Canvas canvas){
		paint.setColor(backgroundColor);
		canvas.drawRoundRect(bound, corner, corner, paint);
		
		
		paint.setColor(textColor);
		
		float wRate = (bound.width() / intrinsicBound.width()) * 0.90F;
		float hRate = (bound.height() / intrinsicBound.height()) * 0.90F;
		
		float rate = wRate > hRate ? hRate : wRate;
		
		canvas.save();
		//canvas.scale(rate, rate, centerX, centerY);
		layout.draw(canvas);
		canvas.restore();
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return PixelFormat.TRANSPARENT;
	}

}
