package com.pz.xingfutao.ui.tab.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.tab.TabActivity;
import com.pz.xingfutao.widget.AddToCart;

public class TabCartFragment extends RefreshableListViewFragment{
	
	private ImageView cart;
	private AddToCart addToCart;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		
		cart = new ImageView(getActivity());
		cart.setImageResource(R.drawable.cart_normal);
		
		RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		imageParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		imageParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		imageParams.setMargins(0, 0, 20, 20);
		
		((RelativeLayout) getView().findViewById(R.id.root)).addView(cart, imageParams);
		
		setMode(MODE_TITLE | MODE_LEFT_BUTTON);
	}

	@Override
	protected void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onClick(int id){
		switch(id){
		case MODE_LEFT_BUTTON:
			if(addToCart != null) addToCart.onDestroy();
			addToCart = new AddToCart(getLeftButton(), cart, R.drawable.forum_normal);
			addToCart.performAdd();
			break;
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if(addToCart != null){
			addToCart.onDestroy();
		}
	}
	
	@Override
	protected boolean isContentEmpty(){
		return true;
	}
	
	@Override
	protected View getEmptyStateView(){
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout_tab_cart, null, false);
		
		((Button) v.findViewById(R.id.go_shopping)).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Activity activity = getActivity();
				if(activity instanceof TabActivity){
					((TabActivity) activity).switchToTab(0);
				}
			}
		});
		
		return v;
	}
}
