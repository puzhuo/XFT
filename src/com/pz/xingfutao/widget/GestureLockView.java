package com.pz.xingfutao.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class GestureLockView extends View{
	
	public static final int MODE_NORMAL = 0x1;
	public static final int MODE_SELECTED = 0x2;
	public static final int MODE_ERROR = 0x4;
	
	private int mode = MODE_NORMAL;
	
	private int errorArrow = -1;
	
	private int width;
	private int height;
	
	private int centerX;
	private int centerY;
	
	private Paint paint;
	
	private static final int COLOR_NORMAL = 0xFFFFFFFF;
	private static final int COLOR_ERROR = 0xFFFF0000;
	
	private int radius;
	private float innerRate = 0.2F;
	private float outerWidthRate = 0.15F;
	private float outerRate = 0.91F;
	private float arrowRate = 0.330F;
	private float arrowDistanceRate = 0.85F;
	private int arrowDistance;
	
	private Path arrow;

	public GestureLockView(Context context){
		this(context, null);
	}
	
	public GestureLockView(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}
	
	public GestureLockView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		arrow = new Path();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		
		centerX = width / 2;
		centerY = height / 2;
		
		radius = width > height ? height : width;
		radius /= 2;
		
		arrow.reset();
		
		arrowDistance = (int) (radius * arrowDistanceRate);
		
		int length = (int) (radius * arrowRate);
		
		arrow.moveTo(-length + centerX, length + centerY - arrowDistance);
		arrow.lineTo(centerX, centerY - arrowDistance);
		arrow.lineTo(length + centerX, length + centerY - arrowDistance);
		arrow.close();
		arrow.setFillType(Path.FillType.WINDING);
	}
	
	public void setMode(int mode){
		this.mode = mode;
		invalidate();
	}
	
	public int getMode(){
		return mode;
	}
	
	public void setArrow(int arrow){
		errorArrow = arrow;
		
		invalidate();
	}
	
	public int getArrow(){
		return errorArrow;
	}
	
	@Override
	public void onDraw(Canvas canvas){
		switch(mode){
		case MODE_NORMAL:
			paint.setStyle(Paint.Style.FILL);
			paint.setColor(COLOR_NORMAL);
			canvas.drawCircle(centerX, centerY, radius * innerRate, paint);
			break;
		case MODE_SELECTED:
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(COLOR_NORMAL);
			paint.setStrokeWidth(radius * outerWidthRate);
			canvas.drawCircle(centerX, centerY, radius * outerRate, paint);
			paint.setStrokeWidth(2);
			canvas.drawCircle(centerX, centerY, radius * innerRate, paint);
			break;
		case MODE_ERROR:
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(COLOR_ERROR);
			paint.setStrokeWidth(radius * outerWidthRate);
			canvas.drawCircle(centerX, centerY, radius * outerRate, paint);
			paint.setStrokeWidth(2);
			canvas.drawCircle(centerX, centerY, radius * innerRate, paint);
			break;
		}
		
		if(errorArrow != -1 && arrow != null){
			paint.setStyle(Paint.Style.FILL);
			
			canvas.save();
			canvas.rotate(errorArrow, centerX, centerY);
			canvas.drawPath(arrow, paint);
			
			canvas.restore();
		}
		
	}
	
}
