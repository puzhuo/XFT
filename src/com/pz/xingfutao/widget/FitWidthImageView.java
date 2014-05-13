package com.pz.xingfutao.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.pz.xingfutao.entities.ImageMap;
import com.pz.xingfutao.net.NetworkHandler;
import com.pz.xingfutao.utils.FragmentUtil;

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
		
		if(d != null){
			intrinsicWidth = d.getIntrinsicWidth();
			intrinsicHeight = d.getIntrinsicHeight();
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
	
	public void setNetworkImage(final ImageMap image){
		NetworkHandler.getInstance(getContext()).imageRequest(image.getImageLink(), this);
		this.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				FragmentUtil.startImageMappingFragment(getContext(), image);
			}
		});
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
