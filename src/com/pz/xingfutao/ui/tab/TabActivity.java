package com.pz.xingfutao.ui.tab;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.tab.fragments.TabCartFragment;
import com.pz.xingfutao.ui.tab.fragments.TabCategoryFragment;
import com.pz.xingfutao.ui.tab.fragments.TabForumFragment;
import com.pz.xingfutao.ui.tab.fragments.TabProfileFragment;
import com.pz.xingfutao.ui.tab.fragments.TabStoreFragment;

public class TabActivity extends FragmentActivity{
	
	private FragmentTabHost tabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_tab);
		
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		
		setupTab();
	}
	
	private void setupTab(){
		tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		tabHost.addTab(newTab(getString(R.string.tab_title_store), R.drawable.selector_tab_store), TabStoreFragment.class, null);
		tabHost.addTab(newTab(getString(R.string.tab_title_category), R.drawable.selector_tab_category), TabCategoryFragment.class, null);
		tabHost.addTab(newTab(getString(R.string.tab_title_cart), R.drawable.selector_tab_cart), TabCartFragment.class, null);
		tabHost.addTab(newTab(getString(R.string.tab_title_forum), R.drawable.selector_tab_forum), TabForumFragment.class, null);
		tabHost.addTab(newTab(getString(R.string.tab_title_profile), R.drawable.selector_tab_profile), TabProfileFragment.class, null);
	}
	
	private TabSpec newTab(String tag, int drawableResId){
		View indicator = getLayoutInflater().inflate(R.layout.tab_item, (ViewGroup) findViewById(android.R.id.tabs), false);
		((TextView) indicator.findViewById(R.id.title)).setText(tag);
		((ImageView) indicator.findViewById(R.id.icon)).setImageResource(drawableResId);
		
		TabSpec tabSpec = tabHost.newTabSpec(tag);
		tabSpec.setIndicator(indicator);
		
		return tabSpec;
	}
	
	public void switchToTab(int index){
		tabHost.setCurrentTab(index);
	}
}
