package com.pz.xingfutao.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.net.NetworkHandler;

public class FitWidthImageView extends ImageView {
	
	private int width;
	private int height;
	
	private int intrinsicWidth;
	private int intrinsicHeight;
	
	private float scaleRate;

	public FitWidthImageView(Context context){
		this(context, null);
	}
	
	public FitWidthImageView(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}
	
	public FitWidthImageView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		//setScaleType(ImageView.ScaleType.MATRIX);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		
		float rate = (float) width / (float) intrinsicWidth;
		height = (int) ((float) intrinsicHeight * rate);
		setMeasuredDimension(width, height);
	}
	
	@Override
	public void setImageDrawable(Drawable drawable){
		if(drawable != null){
			intrinsicWidth = drawable.getIntrinsicWidth();
			intrinsicHeight = drawable.getIntrinsicHeight();
			super.setImageDrawable(drawable);
			scaleToFit();
		}
	}
	
	@Override
	public void setImageResource(int resourceId){
		super.setImageResource(resourceId);
		
		Drawable d = this.getDrawable();
		intrinsicWidth = d.getIntrinsicWidth();
		intrinsicHeight = d.getIntrinsicHeight();
		if(d != null){
			scaleToFit();
		}
	}
	
	@Override
	public void setImageBitmap(Bitmap bitmap){
		if(bitmap != null){
			intrinsicWidth = bitmap.getWidth();
			intrinsicHeight = bitmap.getHeight();
			super.setImageBitmap(bitmap);
			scaleToFit();
		}
	}
	
	public void setNetworkImage(ImageMap image){
		NetworkHandler.getInstance(getContext()).imageRequest(image.getImageLink(), this);
		
	}
	
	public float getScaleRate(){
		return scaleRate;
	}
	
	public int getIntrinsicWidth(){
		return intrinsicWidth;
	}
	
	public int getIntrinsicHeight(){
		return intrinsicHeight;
	}
	
	private void scaleToFit(){
		Matrix matrix = new Matrix();
		float rate = (float) width / (float) intrinsicWidth;
		scaleRate = rate;
		matrix.postScale(rate, rate);
		setImageMatrix(matrix);
		requestLayout();
	}
}
