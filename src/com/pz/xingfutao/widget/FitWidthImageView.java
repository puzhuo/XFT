package com.pz.xingfutao.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.pz.xingfutao.utils.PLog;

public class FitWidthImageView extends ImageView {
	
	private int width;
	private int height;
	
	private int intrinsicWidth;
	private int intrinsicHeight;

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
			PLog.d("intrinsicWidth", intrinsicWidth + "");
			PLog.d("intrinsicHeight", intrinsicHeight + "");
			super.setImageDrawable(drawable);
			scaleToFit();
		}
	}
	
	/*
	@Override
	public void setImageBitmap(Bitmap bitmap){
		if(bitmap != null){
			intrinsicWidth = bitmap.getWidth();
			intrinsicHeight = bitmap.getHeight();
			super.setImageBitmap(bitmap);
			scaleToFit();
		}
	}
	 */
	
	private void scaleToFit(){
		Matrix matrix = new Matrix();
		float rate = (float) width / (float) intrinsicWidth;
		matrix.postScale(rate, rate);
		setImageMatrix(matrix);
		requestLayout();
	}
}
