package com.pz.xingfutao.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pz.xingfutao.R;

@Deprecated
public class TabView extends LinearLayout{
	
	private String[] titles;
	private int[] drawableRes;
	private Class<?>[] classes;
	
	public TabView(Context context){
		this(context, null);
	}
	
	public TabView(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	public void setTab(final String[] titles, int[] drawableRes, final Class<?>[] classes){
		this.titles = titles;
		this.drawableRes = drawableRes;
		this.classes = classes;
		
		for(int i = 0; i < titles.length; i++){
			final int fi = i;
			View item = LayoutInflater.from(getContext()).inflate(R.layout.tab_item, null, false);
			item.setClickable(true);
			item.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					Intent intent = new Intent(getContext(), classes[fi]);
					intent.putExtra("title", titles[fi]);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					getContext().startActivity(intent);
				}
			});
			
			((ImageView) item.findViewById(R.id.icon)).setImageResource(drawableRes[i]);
			((TextView) item.findViewById(R.id.title)).setText(titles[i]);
			
			LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			itemParams.weight = 1.0F;
			
			addView(item, itemParams);
		}
	}
}
