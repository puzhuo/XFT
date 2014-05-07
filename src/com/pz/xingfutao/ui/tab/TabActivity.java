package com.pz.xingfutao.ui.tab;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.pz.xingfutao.R;
import com.pz.xingfutao.ui.base.BaseTitleFragment;
import com.pz.xingfutao.ui.tab.fragments.TabCartFragment;
import com.pz.xingfutao.ui.tab.fragments.TabCategoryFragment;
import com.pz.xingfutao.ui.tab.fragments.TabForumFragment;
import com.pz.xingfutao.ui.tab.fragments.TabProfileFragment;
import com.pz.xingfutao.ui.tab.fragments.TabStoreFragment;

public class TabActivity extends FragmentActivity{
	
	private FragmentTabHost tabHost;
	
	private List<BaseTitleFragment> fragments;
	
	private boolean exit = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
		fragments = new ArrayList<BaseTitleFragment>();
		
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
	
	public void addFragmentToBackStack(BaseTitleFragment fragment){
		fragments.add(fragment);
	}
	
	public void removeFragment(final Fragment fragment){
		View v = fragment.getView();
		
		if(v != null && v.getAnimation() != null && v.getAnimation().hasStarted()){
			return;
		}
		
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.fragment_slide_right_exit);
		animation.setDuration(300);
		animation.setAnimationListener(new AnimationListener(){

			@Override
			public void onAnimationStart(Animation animation) {}

			@Override
			public void onAnimationEnd(Animation animation) {
				getSupportFragmentManager().beginTransaction().remove(fragment).commit();
				if(fragments.contains(fragment)) fragments.remove(fragment);
				if(fragments.size() > 0) getSupportFragmentManager(). beginTransaction().show(fragments.get(fragments.size() - 1));
			}

			@Override
			public void onAnimationRepeat(Animation animation) {}
			
		});
		
		v.startAnimation(animation);
	}
	
	public BaseTitleFragment getLastFragment(){
		if(fragments.size() > 0) return fragments.get(fragments.size() - 1);
		return (BaseTitleFragment) getSupportFragmentManager().findFragmentByTag(tabHost.getCurrentTabTag());
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event){
		
		if(keyCode == KeyEvent.KEYCODE_BACK && fragments.size() > 0){
			removeFragment(fragments.get(fragments.size() - 1));
			return true;
		}
		
		if(exit){
			return super.onKeyDown(keyCode, event);
		}else{
			exit = true;
			Toast.makeText(this, "再点击一次退出", Toast.LENGTH_LONG).show();
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run(){
					exit = false;
				}
			}, 1500L);
			return true;
		}
	}
}
