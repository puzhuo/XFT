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
 *        \//                             caifangmao8@gmail.com                                 \//
 *
 * 
 *
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * this layout will create an IOS-style overscroll effect
 * @author 7heaven
 *
 */
public class OverScrollLayout extends LinearLayout {

	public OverScrollLayout(Context context){
		this(context, null);
	}
	
	public OverScrollLayout(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	@Override
	protected void onFinishInflate(){
		super.onFinishInflate();
		
		if(getChildCount() != 1){
			throw new ArrayIndexOutOfBoundsException("OverScrollLayout can only contain one child View and must contain one.");
		}
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event){
		return onInterceptTouchEvent(event);
	}
}
